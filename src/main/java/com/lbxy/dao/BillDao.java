package com.lbxy.dao;

import com.jfinal.plugin.activerecord.Page;
import com.lbxy.model.Bill;

public class BillDao {

    public Page<Bill> findBillPn(int pn){
        return Bill.dao.paginate(pn,10,"select *","from Bill");
    }

}
