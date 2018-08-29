package com.lbxy.weixin.dto.templateMessage;

/**
 * @Description:
 * @author: laumgjyu
 * @Date: 2018-07-11-18:27
 */
public class TemplateData {

    private TemplateItem header;
    private TemplateItem body;
    private TemplateItem footer;

    public TemplateData() {
    }

    public TemplateData(TemplateItem header, TemplateItem body, TemplateItem footer) {
        this.header = header;
        this.body = body;
        this.footer = footer;
    }

    public TemplateData(String header, String body, String footer) {
        this.header = new TemplateItem(header);
        this.body = new TemplateItem(body);
        this.footer = new TemplateItem(footer);
    }

    public TemplateItem getHeader() {
        return header;
    }

    public void setHeader(TemplateItem header) {
        this.header = header;
    }

    public TemplateItem getBody() {
        return body;
    }

    public void setBody(TemplateItem body) {
        this.body = body;
    }

    public TemplateItem getFooter() {
        return footer;
    }

    public void setFooter(TemplateItem footer) {
        this.footer = footer;
    }

    class TemplateItem {
        private String value;
        private String color;

        public TemplateItem(String value, String color) {
            this.value = value;
            this.color = color;
        }

        public TemplateItem(String value) {
            this.value = value;
            this.color = "#173177";
        }
    }
}
