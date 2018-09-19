package com.lbxy.core.interceptors;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.lbxy.core.plugins.cache.InjectionCache;
import com.lbxy.core.utils.LoggerUtil;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;

/**
 * @author lmy
 * @description InjectionInterceptor 控制器依赖注入拦截器，在每次请求的时候将service单例注入到controller中
 * @date 2018/8/21
 */

@Deprecated
public class InjectionInterceptor implements Interceptor {
    @Override
    public void intercept(Invocation inv) {
        Map<String, String[]> paramMap = inv.getController().getParaMap();
        StringBuilder builder = new StringBuilder();
        paramMap.keySet().forEach(key -> builder.append("[ ").append(key).append(":").append(Arrays.toString(paramMap.get(key))).append(" ]").append("\n"));
        LoggerUtil.info(getClass(), inv.getActionKey() + ": \n" + builder);


        Controller controller = inv.getController();
        Field[] fields = controller.getClass().getDeclaredFields();
        for (Field field : fields) {
            Object bean = null;
            if (field.isAnnotationPresent(Resource.class)) {
                bean = InjectionCache.get(field.getName());
            } else {
                continue;
            }
            try {
                if (bean != null) {
                    field.setAccessible(true);
                    field.set(controller, bean);
                }
            } catch (Exception e) {
                LoggerUtil.error(getClass(), "依赖注入失败：controller" + controller.getClass().getName() + "; field" + field.getName(), e);
                throw new RuntimeException(e);
            }
        }
        inv.invoke();
    }
}
