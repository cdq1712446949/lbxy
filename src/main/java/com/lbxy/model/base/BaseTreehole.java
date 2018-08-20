package com.lbxy.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseTreehole<M extends BaseTreehole<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Long id) {
		set("id", id);
		return (M)this;
	}
	
	public java.lang.Long getId() {
		return getLong("id");
	}

	public M setUserId(java.lang.Long userId) {
		set("userId", userId);
		return (M)this;
	}
	
	public java.lang.Long getUserId() {
		return getLong("userId");
	}

	public M setPUserId(java.lang.Long pUserId) {
		set("pUserId", pUserId);
		return (M)this;
	}
	
	public java.lang.Long getPUserId() {
		return getLong("pUserId");
	}

	public M setPId(java.lang.Long pId) {
		set("pId", pId);
		return (M)this;
	}
	
	public java.lang.Long getPId() {
		return getLong("pId");
	}

	public M setContent(java.lang.String content) {
		set("content", content);
		return (M)this;
	}
	
	public java.lang.String getContent() {
		return getStr("content");
	}

	public M setPostDate(java.util.Date postDate) {
		set("postDate", postDate);
		return (M)this;
	}
	
	public java.util.Date getPostDate() {
		return get("postDate");
	}

	public M setStatus(java.lang.Integer status) {
		set("status", status);
		return (M)this;
	}
	
	public java.lang.Integer getStatus() {
		return getInt("status");
	}

	public M setPictureUrl(java.lang.String pictureUrl) {
		set("pictureUrl", pictureUrl);
		return (M)this;
	}
	
	public java.lang.String getPictureUrl() {
		return getStr("pictureUrl");
	}

}
