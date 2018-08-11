package com.lbxy.service;

import com.lbxy.model.Bill;

import java.util.ArrayList;
import java.util.List;

public class BillService {

    public List<Bill> getBill(int userId){
        List<Bill> list=new ArrayList<Bill>();
        list=Bill.dao.find("select * from Bill where userId=?",userId);
        return list;
    }

    public boolean add(Bill bill){
        boolean i=bill.save();
        return i;
    }

}
