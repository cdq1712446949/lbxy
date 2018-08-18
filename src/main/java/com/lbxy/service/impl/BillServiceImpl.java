package com.lbxy.service.impl;

import com.jfinal.plugin.activerecord.Page;
import com.lbxy.dao.BillDao;
import com.lbxy.model.Bill;
import com.lbxy.service.BillService;

import java.util.ArrayList;
import java.util.List;

public class BillServiceImpl implements BillService {

    private BillDao billDao;

    public BillServiceImpl() {
        billDao = new BillDao();
    }

    public List<Bill> getBill(int userId) {
        List<Bill> list = new ArrayList<Bill>();
        list = Bill.dao.find("select * from Bill where userId=?", userId);
        return list;
    }

    public boolean add(Bill bill) {
        boolean i = bill.save();
        return i;
    }

    @Override
    public Page<Bill> getAllBill(int pn) {
        return billDao.findBillPn(pn);
    }

}
