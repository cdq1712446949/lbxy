package com.lbxy.dao;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.lbxy.common.status.BillStatus;
import com.lbxy.model.Bill;

import java.math.BigDecimal;

public class BillDao {

    public Page<Bill> findBillPn(int pn){
        return Bill.dao.paginate(pn,10,"select *","from Bill");
    }

    public BigDecimal get7DaysTotalIncome(int userId) {
        return Db.queryBigDecimal("select sum(money) from Bill where userId=? and status=?", userId, BillStatus.INCOME);
    }

}
