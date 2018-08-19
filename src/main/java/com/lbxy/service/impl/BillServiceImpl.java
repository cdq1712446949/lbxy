package com.lbxy.service.impl;

import com.jfinal.plugin.activerecord.Page;
import com.lbxy.dao.BillDao;
import com.lbxy.model.Bill;
import com.lbxy.service.BillService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BillServiceImpl implements BillService {

    private BillDao billDao;

    public BillServiceImpl() {
        billDao = new BillDao();
    }

    public List<Bill> getBill(int userId) {
        return billDao.findByUserId(userId);
    }

    public boolean add(Bill bill) {
        boolean i = bill.save();
        return i;
    }

    @Override
    public Page<Bill> getAllBill(int pn) {
        return billDao.findBillPn(pn);
    }

    @Override
    public BigDecimal get7DaysTotalIncome(int userId) {
        return billDao.get7DaysTotalIncome(userId);
    }

    @Override
    public List<Bill> getAllByUserId(int userId) {
        return billDao.getAllByUserId(userId);
    }
}
