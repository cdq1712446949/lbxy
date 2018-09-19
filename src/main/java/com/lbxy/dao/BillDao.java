package com.lbxy.dao;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.lbxy.common.PageConst;
import com.lbxy.common.status.BillStatus;
import com.lbxy.model.Bill;

import java.math.BigDecimal;
import java.util.List;

public class BillDao {
    public BillDao() {
    }

    public Page<Bill> findBillPn(int pn) {
        return Bill.DAO.paginate(pn, 10, "select *", "from bill order by createdDate desc");
    }

    public List<Bill> findByUserId(long userId) {
        return Bill.DAO.find("select * from bill where userId=? order by createdDate desc", userId);
    }

    public BigDecimal get7DaysTotalIncome(long userId) {
        return Db.queryBigDecimal("select sum(money) from bill where userId=? and status=?", userId, BillStatus.INCOME);
    }

    public Page<Bill> getAllByUserId(int pn, long userId) {
        return Bill.DAO.paginate(pn, PageConst.PAGE_SIZE, "select b.status,b.money,b.createdDate,o.fromAddress,o.toAddress ", "from bill b inner join `order` o on b.orderId = o.id where b.userId=? order by b.createdDate desc", userId);
    }

    public Page<Bill> findBillByPhoneNumber(int pn, String phoneNumber) {
        return Bill.DAO.paginate(pn, 10, "select b.*", "from bill b inner join user u on b.userId=u.id where u.phoneNumber = ? order by createdDate desc", phoneNumber);
    }

    public boolean save(Bill bill) {
        return bill.save();
    }
}
