package com.lbxy.core.handlers;

import com.jfinal.handler.Handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Pattern;

/**
 * @author lmy
 * @description ServletExcludeHandler
 * @date 2018/9/13
 */
public class ServletExcludeHandler extends Handler {

    private Pattern r = Pattern.compile("^(/wx).*");

    @Override
    public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
        if (!r.matcher(target).matches()) {
            next.handle(target,request,response,isHandled);
        }
    }
}
