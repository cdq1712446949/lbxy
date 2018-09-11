package com.lbxy.weixin.dto.templateMessage;

/**
 * @Description:
 * @author: laumgjyu
 * @Date: 2018-07-11-18:27
 */
public class TemplateData {

    private TemplateItem keyword1;
    private TemplateItem keyword2;
    private TemplateItem keyword3;
    private TemplateItem keyword4;
    private TemplateItem keyword5;

    public TemplateData() {
    }

    public TemplateData(TemplateItem keyword1, TemplateItem keyword2, TemplateItem keyword3, TemplateItem keyword4, TemplateItem keyword5) {
        this.keyword1 = keyword1;
        this.keyword2 = keyword2;
        this.keyword3 = keyword3;
        this.keyword4 = keyword4;
        this.keyword5 = keyword5;
    }

    public TemplateData(String keyword1, String keyword2, String keyword3, String keyword4, String keyword5) {
        this.keyword1 = new TemplateItem(keyword1);
        this.keyword2 = new TemplateItem(keyword2);
        this.keyword3 = new TemplateItem(keyword3);
        this.keyword4 = new TemplateItem(keyword4);
        this.keyword5 = new TemplateItem(keyword5);
    }

    public TemplateItem getKeyword1() {
        return keyword1;
    }

    public void setKeyword1(String keyword1) {
        this.keyword1 = new TemplateItem(keyword1);
    }

    public void setKeyword1(TemplateItem keyword1) {
        this.keyword1 = keyword1;
    }

    public TemplateItem getKeyword2() {
        return keyword2;
    }

    public void setKeyword2(String keyword2) {
        this.keyword2 = new TemplateItem(keyword2);
    }

    public void setKeyword2(TemplateItem keyword2) {
        this.keyword2 = keyword2;
    }

    public TemplateItem getKeyword3() {
        return keyword3;
    }

    public void setKeyword3(String keyword3) {
        this.keyword3 = new TemplateItem(keyword3);
    }

    public void setKeyword3(TemplateItem keyword3) {
        this.keyword3 = keyword3;
    }

    public TemplateItem getKeyword4() {
        return keyword4;
    }

    public void setKeyword4(String keyword4) {
        this.keyword4 = new TemplateItem(keyword4);
    }

    public void setKeyword4(TemplateItem keyword4) {
        this.keyword4 = keyword4;
    }

    public TemplateItem getKeyword5() {
        return keyword5;
    }

    public void setKeyword5(String keyword5) {
        this.keyword5 = new TemplateItem(keyword5);
    }

    public void setKeyword5(TemplateItem keyword5) {
        this.keyword5 = keyword5;
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
