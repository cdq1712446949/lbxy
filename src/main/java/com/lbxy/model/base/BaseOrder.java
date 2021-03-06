package com.lbxy.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseOrder<M extends BaseOrder<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Long id) {
		set("id", id);
		return (M)this;
	}
	
	public java.lang.Long getId() {
		return getLong("id");
	}

	public M setStatus(java.lang.Integer status) {
		set("status", status);
		return (M)this;
	}
	
	public java.lang.Integer getStatus() {
		return getInt("status");
	}

	public M setCreatedDate(java.util.Date createdDate) {
		set("createdDate", createdDate);
		return (M)this;
	}
	
	public java.util.Date getCreatedDate() {
		return get("createdDate");
	}

	public M setCompletedDate(java.util.Date completedDate) {
		set("completedDate", completedDate);
		return (M)this;
	}
	
	public java.util.Date getCompletedDate() {
		return get("completedDate");
	}

	public M setAcceptUserId(java.lang.Long acceptUserId) {
		set("acceptUserId", acceptUserId);
		return (M)this;
	}
	
	public java.lang.Long getAcceptUserId() {
		return getLong("acceptUserId");
	}

	public M setAcceptUserPhoneNumber(java.lang.String acceptUserPhoneNumber) {
		set("acceptUserPhoneNumber", acceptUserPhoneNumber);
		return (M)this;
	}
	
	public java.lang.String getAcceptUserPhoneNumber() {
		return getStr("acceptUserPhoneNumber");
	}

	public M setReward(java.math.BigDecimal reward) {
		set("reward", reward);
		return (M)this;
	}
	
	public java.math.BigDecimal getReward() {
		return get("reward");
	}

	public M setUserId(java.lang.Long userId) {
		set("userId", userId);
		return (M)this;
	}
	
	public java.lang.Long getUserId() {
		return getLong("userId");
	}

	public M setUserName(java.lang.String userName) {
		set("userName", userName);
		return (M)this;
	}
	
	public java.lang.String getUserName() {
		return getStr("userName");
	}

	public M setUserPhoneNumber(java.lang.String userPhoneNumber) {
		set("userPhoneNumber", userPhoneNumber);
		return (M)this;
	}
	
	public java.lang.String getUserPhoneNumber() {
		return getStr("userPhoneNumber");
	}

	public M setFromAddress(java.lang.String fromAddress) {
		set("fromAddress", fromAddress);
		return (M)this;
	}
	
	public java.lang.String getFromAddress() {
		return getStr("fromAddress");
	}

	public M setToAddress(java.lang.String toAddress) {
		set("toAddress", toAddress);
		return (M)this;
	}
	
	public java.lang.String getToAddress() {
		return getStr("toAddress");
	}

	public M setRemark(java.lang.String remark) {
		set("remark", remark);
		return (M)this;
	}
	
	public java.lang.String getRemark() {
		return getStr("remark");
	}

	public M setDetail(java.lang.String detail) {
		set("detail", detail);
		return (M)this;
	}
	
	public java.lang.String getDetail() {
		return getStr("detail");
	}

	public M setAvailableDateDesc(java.lang.String availableDateDesc) {
		set("availableDateDesc", availableDateDesc);
		return (M)this;
	}
	
	public java.lang.String getAvailableDateDesc() {
		return getStr("availableDateDesc");
	}

	public M setAvailableDate(java.util.Date availableDate) {
		set("availableDate", availableDate);
		return (M)this;
	}
	
	public java.util.Date getAvailableDate() {
		return get("availableDate");
	}

	public M setAcceptDate(java.util.Date acceptDate) {
		set("acceptDate", acceptDate);
		return (M)this;
	}
	
	public java.util.Date getAcceptDate() {
		return get("acceptDate");
	}

	public M setSettledDate(java.util.Date settledDate) {
		set("settledDate", settledDate);
		return (M)this;
	}
	
	public java.util.Date getSettledDate() {
		return get("settledDate");
	}

	public M setCanPayBack(java.lang.Integer canPayBack) {
		set("canPayBack", canPayBack);
		return (M)this;
	}
	
	public java.lang.Integer getCanPayBack() {
		return getInt("canPayBack");
	}

}
