package com.lbxy.common.request;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * @author lmy
 * @description VerificationBean
 * @date 2018/8/18
 */
public class VerificationBean {
    @NotNull
    @Length(min = 2, message = "真实姓名至少两位")
    private String realName;

    @NotNull
    private String studentNumber;

    private String stuNoPic;

    public String getStuNoPic() {
        if (Objects.isNull(stuNoPic)) {
            return "";
        } else {
            return stuNoPic;
        }
    }

    public void setStuNoPic(String stuNoPic) {
        this.stuNoPic = stuNoPic;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
}
