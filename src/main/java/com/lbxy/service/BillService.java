package com.lbxy.service;

import com.jfinal.plugin.activerecord.Page;
import com.lbxy.model.Bill;

import java.util.List;

/**
 * @author lmy
 * @description BillServiceImpl
 * @date 2018/8/14
 */
public interface BillService {
    List<Bill> getBill(int userId);

    boolean add(Bill bill);

    Page<Bill> getAllBill(int pn);

}
