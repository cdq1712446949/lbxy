package com.lbxy.service;

/**
 * @author lmy
 * @description PayBackService
 * @date 2018/9/13
 */
public interface PayBackService {
    void payBack(long orderId, long userId, double totalFee);
}
