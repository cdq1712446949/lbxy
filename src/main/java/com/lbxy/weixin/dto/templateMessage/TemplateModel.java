package com.lbxy.weixin.dto.templateMessage;

/**
 * @Description:
 * @author: laumgjyu
 * @Date: 2018-07-11-18:17
 */

public class TemplateModel {
    private String touser;
    private String template_id;
    private String page;

    private String form_id;

    private TemplateData data;

    public TemplateModel(String touser, String template_id, String formId, String page, TemplateData data) {
        this.touser = touser;
        this.template_id = template_id;
        this.page = page;
        this.form_id = formId;
        this.data = data;
    }

    public String getForm_id() {
        return form_id;
    }

    public void setForm_id(String form_id) {
        this.form_id = form_id;
    }

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public TemplateData getData() {
        return data;
    }

    public void setData(TemplateData data) {
        this.data = data;
    }
}
