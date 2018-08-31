package com.lbxy.service.impl;

import com.jfinal.plugin.activerecord.Page;
import com.lbxy.common.request.CreateOrderBean;
import com.lbxy.common.status.BillStatus;
import com.lbxy.common.status.CommonStatus;
import com.lbxy.common.status.OrderStatus;
import com.lbxy.core.annotation.Service;
import com.lbxy.dao.BillDao;
import com.lbxy.dao.OrderDao;
import com.lbxy.dao.UserDao;
import com.lbxy.model.Bill;
import com.lbxy.model.Order;
import com.lbxy.model.User;
import com.lbxy.service.OrderService;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

@Service("orderService")
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderDao orderDao;

    @Resource
    private UserDao userDao;

    @Resource
    private BillDao billDao;

    @Override
    public Order findById(long id) {
        return orderDao.findById(id);
    }

    public Page<Order> getOrdersByPage(int pn) {
        return orderDao.getUnCompletedOrdersByPage(pn);
    }

    @Override
    public Page<Order> getUnCompletedAndWaitCompletedAndCompletedOrdersByPage(int pn) {
        return orderDao.getUnCompletedAndWaitCompletedAndCompletedOrdersByPage(pn);
    }

    public boolean complete(long orderId) {
        Order order = orderDao.findById(orderId);
        order.setCompletedDate(new Date());
        order.setStatus(OrderStatus.COMPLETED);
        return orderDao.update(order);
    }

    public int accept(long orderId, long userId) {
        User user = userDao.findById(userId);
        if (user == null) {
            return ERROR_USERID;
        }
        if (user.getPhoneNumber() == null || user.getReadName() == null) {
            return NEED_MORE_INFO;
        }

        Order order = orderDao.findById(orderId);
        if (order.getUserId() == userId) {
            return CANT_ACCEPT_OWN_ORDER;
        }
        order.setAcceptUserId(userId);
        order.setAcceptUserPhoneNumber(user.getPhoneNumber());
        order.setAcceptDate(new Date());
        order.setStatus(OrderStatus.WAIT_COMPLETE);
        orderDao.update(order);
        return SUCCESS;
    }

    public Page<Order> getOwnerPostOrders(int pn, long userId) {
        return orderDao.findByUserId(userId, pn);
    }

    public Page<Order> getOwnerAcceptOrders(int pn, long userId) {
        return orderDao.findByAcceptUserId(userId, pn);
    }

    @Override
    public int cancelOrder( long orderId) {
        Order order = orderDao.findById(orderId);
        order.setStatus(OrderStatus.CANCELED);
        orderDao.update(order);

        return userDao.updateUserBalance(order.getUserId(), order.getReward());
    }

    @Override
    public boolean settleOrder(long orderId) throws Exception {
        Order order = orderDao.findById(orderId);
        long acceptUserId = order.getAcceptUserId();
        BigDecimal reward = order.getReward();

        User acceptUser = userDao.findById(acceptUserId);
        BigDecimal balance = acceptUser.getBalance();
        acceptUser.setBalance(balance.add(reward));
        boolean result = userDao.update(acceptUser);
        if (result) {
            Bill bill = new Bill();
            bill.setOrderId(orderId);
            bill.setUserId(acceptUserId);
            bill.setMoney(reward);
            bill.setStatus(BillStatus.INCOME);
            billDao.save(bill);

            order.setStatus(OrderStatus.SETTLED);
            order.setSettledDate(new Date());
            return orderDao.update(order);
        } else {
            throw new Exception("订单结算失败，orderId：" + orderId);
        }
    }

    @Override
    public boolean payOrder(long orderId) {
        int result = orderDao.updateOrderStatus(orderId, OrderStatus.UN_COMPLETED);
        return result != 0;
    }

    @Override
    public boolean updateModel(Order order) {
        return orderDao.update(order);
    }

    public boolean delete(long id) {
//        return orderDao.deleteById(id); // 不采用物理删除的方法，使用逻辑删除
        int result = orderDao.updateOrderStatus(id, CommonStatus.DELETED);
        return result != 0;
    }

    public Page<Order> getAllOrder(int pn) {
        int totalNum = orderDao.getTotalNumber();
        int totalPage = totalNum / 10;
        if (totalNum % 10 >= 1) {
            totalPage += 1;
        }
        if (pn >= totalPage) {
            pn = totalPage;
        }

        if (pn <= 1) {
            pn = 1;
        }
        return orderDao.findByPn(pn);
    }

    public BigDecimal getWaitSettledReward(long acceptUserId) {
        return orderDao.getWaitCompletedOrdersTotalRewardByAcceptUserId(acceptUserId);
    }

    @Override
    public long createOrder(long userId, CreateOrderBean orderInfo) {
        Order order = new Order();
        order.setCreatedDate(new Date());
        order.setReward(BigDecimal.valueOf(orderInfo.getReward()));
        order.setUserId(userId);
        order.setUserName(orderInfo.getUserName());
        order.setUserPhoneNumber(orderInfo.getUserPhoneNumber());
        order.setFromAddress(orderInfo.getFromAddress());
        order.setToAddress(orderInfo.getToAddress());
        order.setRemark(orderInfo.getRemark());
        order.setDetail(orderInfo.getDetail());
        order.setAvailableDateDesc(orderInfo.getAvailableDateDesc());

        String[] availableDateTimeArray = orderInfo.getAvailableDateDesc().split(" \\| ");
        String[] availableDateArray = availableDateTimeArray[0].split("/");
        String startTime = null;
        if (availableDateTimeArray[1].contains("-")) {
            startTime = availableDateTimeArray[1].split("-")[0].trim();
        } else {
            startTime = "00:00"; //如果时间为零点，那么就是任意时间
        }
        String[] startTimeArray = startTime.split(":");

        LocalDateTime dateTime = LocalDateTime.of(LocalDate.now().getYear(),
                Integer.parseInt(availableDateArray[0]),
                Integer.parseInt(availableDateArray[1]),
                Integer.parseInt(startTimeArray[0]),
                Integer.parseInt(startTimeArray[1])
        );

        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = dateTime.atZone(zoneId);
        Date date = Date.from(zdt.toInstant());

        order.setAvailableDate(date);
        orderDao.save(order);
        return order.getId();
    }

}
