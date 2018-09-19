package com.lbxy.service.impl;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.lbxy.dao.BillDao;
import com.lbxy.model.Bill;
import com.lbxy.service.BillService;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.List;

public class BillServiceImpl implements BillService {

    private final BillDao billDao;

    @Inject
    public BillServiceImpl(BillDao billDao) {
        this.billDao = billDao;
    }

    @Override
    public List<Bill> getBill(long userId) {
        return billDao.findByUserId(userId);
    }

    @Before(Tx.class)
    @Override
    public boolean add(Bill bill) {
        return bill.save();
    }

    @Override
    public Page<Bill> getAllBill(int pn) {
        return billDao.findBillPn(pn);
    }

    @Override
    public BigDecimal get7DaysTotalIncome(long userId) {
        return billDao.get7DaysTotalIncome(userId);
    }

    @Override
    public Page<Record> getAllByUserId(int pn, long userId) {
        return billDao.getAllByUserId(pn, userId);
    }

    @Override
    public Page<Bill> getBillByPhoneNumber(int pn, String phoneNumber) {
        return billDao.findBillByPhoneNumber(pn, phoneNumber);
    }

    @Override
    public int updateStatusByUserIdAndOrderId(long userId, long orderId, int status) {
        return billDao.updateStatusByUserIdAndOrderId(userId, orderId, status);
    }
}
