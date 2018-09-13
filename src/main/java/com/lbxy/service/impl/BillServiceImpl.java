package com.lbxy.service.impl;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.lbxy.core.annotation.Service;
import com.lbxy.dao.BillDao;
import com.lbxy.model.Bill;
import com.lbxy.service.BillService;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@Service("billService")
public class BillServiceImpl implements BillService {

    @Resource
    private BillDao billDao;

    @Override
    public List<Bill> getBill(int userId) {
        return billDao.findByUserId(userId);
    }

    @Before(Tx.class)
    @Override
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
    public Page<Bill> getAllByUserId(int pn, int userId) {
        return billDao.getAllByUserId(pn, userId);
    }

    @Override
    public Page<Bill> getBillByPhoneNumber(int pn, String phoneNumber) {
        return billDao.findBillByPhoneNumber(pn, phoneNumber);
    }
}
