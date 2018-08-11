package com.demo.index;

import com.demo.common.model.Blog;
import com.jfinal.core.Controller;
import com.lbxy.common.response.MessageVo;
import com.lbxy.common.response.ResponseStatus;

/**
 * 本 demo 仅表达最为粗浅的 jfinal 用法，更为有价值的实用的企业级用法
 * 详见 JFinal 俱乐部: http://jfinal.com/club
 * 
 * IndexController
 */
public class IndexController extends Controller {
	public void index() {
		Blog blog = new Blog();
		renderJson(new MessageVo().setStatus(ResponseStatus.success).setMessage("请求成功").setData(blog));
		render("login.html");
	}
}



