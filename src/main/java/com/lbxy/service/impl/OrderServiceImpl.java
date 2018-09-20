package com.lbxy.service.impl;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.lbxy.common.request.CreateOrderBean;
import com.lbxy.common.status.BillStatus;
import com.lbxy.common.status.CommonStatus;
import com.lbxy.common.status.OrderStatus;
import com.lbxy.dao.OrderDao;
import com.lbxy.event.CreateBillEvent;
import com.lbxy.event.UpdateBillEvent;
import com.lbxy.event.UpdateUserBalanceEvent;
import com.lbxy.model.Order;
import com.lbxy.model.User;
import com.lbxy.service.OrderService;
import net.dreamlu.event.EventKit;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Objects;

public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao;

    @Inject
    public OrderServiceImpl(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public Order findById(long id) {
        return orderDao.findById(id);
    }

    public Page<Record> getOrdersByPage(int pn) {
        return orderDao.getUnCompletedOrdersByPage(pn);
    }

    @Override
    public Page<Order> getUnCompletedAndWaitCompletedAndCompletedOrdersByPage(int pn) {
        return orderDao.getUnCompletedAndWaitCompletedAndCompletedOrdersByPage(pn);
    }

    @Before(Tx.class)
    public boolean complete(long orderId) {
        Order order = orderDao.findById(orderId);
        order.setCompletedDate(new Date());
        order.setStatus(OrderStatus.COMPLETED);
        return orderDao.update(order);
    }

    @Override
    public boolean updateAcceptOrder(long orderId, User user) throws Exception {
        Order order = orderDao.findById(orderId);
        if (Objects.equals(order.getUserId(), user.getId())) {
            throw new Exception("can't accept own orders(不能接受自己的订单)");
        }
        order.setAcceptUserId(user.getId());
        order.setAcceptUserPhoneNumber(user.getPhoneNumber());
        order.setAcceptDate(new Date());
        order.setStatus(OrderStatus.WAIT_COMPLETE);
        return orderDao.update(order);
    }


    public Page<Order> getOwnerPostOrders(int pn, long userId) {
        return orderDao.findByUserId(userId, pn);
    }

    public Page<Order> getOwnerAcceptOrders(int pn, long userId) {
        return orderDao.findByAcceptUserId(userId, pn);
    }

    @Override
    @Before(Tx.class)
    public boolean cancelOrder(long orderId) {
        Order order = orderDao.findById(orderId);
        order.setStatus(OrderStatus.CANCELED);
        boolean result = orderDao.update(order);

        EventKit.post(new UpdateUserBalanceEvent(getClass()).setUserId(order.getUserId()).setReward(order.getReward()));
        return result;
    }

    @Override
    @Before(Tx.class)
    public boolean settleOrder(long orderId) {
        Order order = orderDao.findById(orderId);
        long acceptUserId = order.getAcceptUserId();
        long userId = order.getUserId();
        BigDecimal reward = order.getReward();

        EventKit.post(new UpdateUserBalanceEvent(getClass()).setUserId(acceptUserId).setReward(order.getReward()));
        EventKit.post(new CreateBillEvent(getClass(),orderId,acceptUserId,reward, BillStatus.INCOME));
        EventKit.post(new UpdateBillEvent(getClass(),orderId,userId, BillStatus.PAY));

        order.setStatus(OrderStatus.SETTLED);
        order.setSettledDate(new Date());
        return orderDao.update(order);
    }

    @Override
    @Before(Tx.class)
    public boolean payOrder(long orderId) {
        int result = orderDao.updateOrderStatus(orderId, OrderStatus.UN_COMPLETED);
        return result != 0;
    }

    @Override
    @Before(Tx.class)
    public boolean updateModel(Order order) {
        return orderDao.update(order);
    }

    @Before(Tx.class)
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
    @Before(Tx.class)
    public long createOrder(long userId, CreateOrderBean orderInfo) {
        Order order = new Order();
        order.setStatus(OrderStatus.UN_PAYED);
        order.setCreatedDate(new Date());
        order.setReward(BigDecimal.valueOf(orderInfo.getReward()));
        order.setUserId(userId);
        order.setUserName(orderInfo.getUserName());
        order.setUserPhoneNumber(orderInfo.getUserPhoneNumber());
        order.setFromAddress(orderInfo.getFromAddress());
        order.setToAddress(orderInfo.getToAddress());
        order.setRemark(orderInfo.getRemark());
        order.setDetail(orderInfo.getDetail());
        order.setAvailableDateDesc(orderInfo.getAvailableDate());

        String[] availableDateTimeArray = orderInfo.getAvailableDate().split(" \\| ");
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

    /**
     * 设置是否可以在完成状态下撤销订单
     *
     * @return
     */
    @Before(Tx.class)
    public boolean setPayBack(Order order) {
        order.setCanPayBack(OrderStatus.CAN_PAY_BACK);
        return orderDao.update(order);
    }

}
