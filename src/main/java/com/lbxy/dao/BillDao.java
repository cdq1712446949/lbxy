package com.lbxy.dao;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.lbxy.common.PageConst;
import com.lbxy.common.status.BillStatus;
import com.lbxy.core.annotation.Repository;
import com.lbxy.model.Bill;

import java.math.BigDecimal;
import java.util.List;

@Repository
public class BillDao {

    public Page<Bill> findBillPn(int pn) {
        return Bill.dao.paginate(pn, 10, "select *", "from Bill");
    }

    public List<Bill> findByUserId(int UserId) {
        return Bill.dao.find("select * from Bill where userId=?", UserId);
    }

    public BigDecimal get7DaysTotalIncome(int userId) {
        return Db.queryBigDecimal("select sum(money) from Bill where userId=? and status=?", userId, BillStatus.INCOME);
    }

    public Page<Bill> getAllByUserId(int pn, int userId) {
        return Bill.dao.paginate(pn, PageConst.pageSize, "select b.status,b.money,b.createdDate,o.fromAddress,o.toAddress ", "from Bill b inner join `order` o on b.orderId = o.id where b.userId=?", userId);
    }

    public Page<Bill> findBillByPhoneNumber(int pn, String phoneNumber) {
        return Bill.dao.paginate(pn, 10, "select b.*", "from Bill b inner join User u on b.userId=u.id where u.phoneNumber = ?", phoneNumber);
    }
}
