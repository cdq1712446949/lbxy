package com.lbxy.service;

import com.jfinal.plugin.activerecord.Page;
import com.lbxy.model.Bill;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author lmy
 * @description BillServiceImpl
 * @date 2018/8/14
 */
public interface BillService {
    List<Bill> getBill(long userId);

    boolean add(Bill bill);

    Page<Bill> getAllBill(int pn);

    BigDecimal get7DaysTotalIncome(long userId);

    Page<Bill> getAllByUserId(int pn, long userId);

    Page<Bill> getBillByPhoneNumber(int pn,String phoneNumber);

}
