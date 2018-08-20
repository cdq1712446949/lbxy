package com.lbxy.core.interceptors;


import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.core.paragetter.Para;
import com.lbxy.common.response.MessageVoUtil;
import com.lbxy.core.annotation.ValidParam;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author lmy
 * @description ParamValidatorInterceptor 参数校验拦截器
 * @date 2018/8/16
 */

public class ParamValidateInterceptor implements Interceptor {
    private static Validator validator = null;

    public ParamValidateInterceptor() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void intercept(Invocation inv) {
        Controller invController = inv.getController();
        Method method = inv.getMethod();
        Annotation[][] annotations = method.getParameterAnnotations(); //获取方法参数的注解，结果为二维数组，第一个维度对应参数列表里参数的数目，第二个维度对应参数列表里对应的注解。
        Set<?> constraintViolations = null;
        for (int i = 0; i < annotations.length; i++) {
            for (Annotation annotation : annotations[i]) {
                if (annotation instanceof ValidParam) {
                    Para para = this.containsAnnotations(annotations[i], Para.class);
                    Class<?> validBean = inv.getArg(i).getClass();
                    Class<?>[] groups = ((ValidParam) annotation).groups();
                    if (groups.length > 0) {
                        //支持分组校验
                        if (para != null) {
                            constraintViolations = validator.validate(invController.getBean(validBean, para.value()), groups);
                        } else {
                            constraintViolations = validator.validate(invController.getBean(validBean, method.getParameters()[i].getName()), groups);
                        }
                    } else {
                        if (para != null) {
                            constraintViolations = validator.validate(invController.getBean(validBean, para.value()));
                        } else
                            constraintViolations = validator.validate(invController.getBean(validBean, method.getParameters()[i].getName()));
                    }
                } else {
                    String AnnotationPackage = annotation.annotationType().getPackage().getName();
                    if ("javax.validation.constraints".equals(AnnotationPackage) || "org.hibernate.annotation.constraints".equals(AnnotationPackage)) {
                        Parameter[] parameters = method.getParameters();
                        Map<String, Object> paramValueMap = new LinkedHashMap<>();
                        for (int j = 0; j < parameters.length; j++) {
                            Parameter parameter = parameters[j];
                            paramValueMap.put(parameter.getName(), inv.getArg(j));
                        }
                        constraintViolations = validator.forExecutables().validateParameters(invController, method, paramValueMap.values().toArray());
                    }
                }

                if (constraintViolations != null && constraintViolations.size() > 0) {
                    // 如果验证出错
                    StringBuilder msg = new StringBuilder();
                    for (Object constraintViolation : constraintViolations) {
                        msg.append(((ConstraintViolation) constraintViolation).getMessage()).append(";");
                    }

                    invController.renderJson(MessageVoUtil.error(msg.toString()));
                    return;
                }

            }
        }

        //没有错误就放行
        inv.invoke();
    }

    private <T extends Annotation> T containsAnnotations(Annotation[] annotations, Class<T> targetAnnotation) {
        for (Annotation annotation : annotations) {
            if (targetAnnotation.isInstance(annotation)) {
                return (T) annotation;
            }
        }
        return null;
    }
}



