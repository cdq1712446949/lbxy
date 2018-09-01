package com.lbxy.controller;

import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.plugin.activerecord.Page;
import com.lbxy.common.NotificationType;
import com.jfinal.plugin.ehcache.CacheKit;
import com.lbxy.common.CacheNameConst;
import com.lbxy.core.interceptors.ManagerLoginInterceptor;
import com.lbxy.model.*;
import com.lbxy.service.*;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;

@Before(ManagerLoginInterceptor.class)
public class ManagerController extends BaseController {

    @Resource
    private ManagerService managerService;

    @Resource
    private UserService userService;

    @Resource
    private OrderService orderService;

    @Resource
    private TreeHoleService treeHoleService;

    @Resource
    private FleaService fleaService;

    @Resource
    private NoticeService noticeService;

    @Resource
    private LostFoundService lostFoundService;

    @Resource
    private BillService billService;

    @Resource
    private NotificationService notificationService;


    @Clear({ManagerLoginInterceptor.class})
    public void index() {
        setAttr("error", "");
        render("login.html");
    }

    public void userList() {
        int pn = 1;
        try {
            pn = getParaToInt("pn");
        } catch (Exception e) {
            System.out.println(" pageNumber is invalid");
        }
        Page<User> users = userService.getAllUsers(pn);
        setAttr("data", users);
        render("user_list.html");
    }

    public void search() {
        String phoneNum = getPara("phoneNumber");
        if (StringUtils.isBlank(phoneNum)) {
            Page<User> users = userService.getAllUsers(1);
            setAttr("data", users);
        } else {
            Page<User> user = userService.findByPhone(phoneNum);
            if (user.getList().size() == 0) {
                System.out.println("改手机号不存在");
                setAttr("error_search", "该手机号不存在");
            } else {
                setAttr("data", user);
            }
        }
        render("user_list.html");
    }

    //查询树洞帖子
    public void searchTreeHole() {

    }

    //查询用户交易记录
    public void searchBill(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.equals("")) {
            billList();
        } else {
            setAttr("billPage", billService.getBillByPhoneNumber(1, phoneNumber));
            render("bill_list.html");
        }
    }


    @Clear({ManagerLoginInterceptor.class})
    public void login(String username, String password) {
        int i = managerService.login(username, password);
        if (i == ManagerService.NOT_EXIST) {
            System.out.println("账号不存在");
            setAttr("error", "账号不存在");
        } else if (i == ManagerService.INVALID_PASSWORD) {
            System.out.println("密码错误");
            setAttr("error", "密码错误");
            render("login.html");
        } else if (i == ManagerService.SUCCESS) {
            System.out.println("登陆成功");
            CacheKit.put(CacheNameConst.MANAGER_LOGIN_CACHE, "username.login", username);
            setAttr("username", username);
            render("index.html");
        }
    }

    public void orderList() {
        int pn = 1;
        try {
            pn = getParaToInt("pn");
        } catch (Exception e) {
            System.out.println(" pageNumber is invalid");
        }
        Page<Order> orderPage = orderService.getAllOrder(pn);
        setAttr("orderPage", orderPage);
        render("order_list.html");
    }

    public void treeHoleList() {
        int pn = 1;
        try {
            pn = getParaToInt(pn);
        } catch (Exception e) {
            System.out.println(" pageNumber is invalid");
        }
        Page<Treehole> treeHolePage = treeHoleService.getAllTreeHole(pn);
        setAttr("treeHolePage", treeHolePage);
        render("treehole_list.html");
    }

    public void fleaList() {
        int pn = 1;
        try {
            pn = getParaToInt(pn);
        } catch (Exception e) {
            System.out.println(" pageNumber is invalid");
        }
        Page<Flea> fleaPage = fleaService.getAllFlea(pn);
        setAttr("fleaPage", fleaPage);
        render("flea_list.html");
    }

    public void lostFoundList() {
        int pn = 1;
        try {
            pn = getParaToInt(pn);
        } catch (Exception e) {
            System.out.println(" pageNumber is invalid");
        }
        Page<Lostfound> lostFoundPage = lostFoundService.getAllLostFound(pn);
        setAttr("lostFoundPage", lostFoundPage);
        render("lostfound_list.html");
    }

    public int checkPn(int pn, int totalPage) {
        if (pn >= totalPage) {
            pn = totalPage;
        } else if (pn <= 1) {
            pn = 1;
        }
        return pn;
    }

    public void notificationList() {
        String userName=null;
        if (getPara("userName") == null || getPara("userName").equals("")) {
            System.out.println("判断username值为null");
        } else {
            userName = getPara("userName");
        }
        if (userName.equals("null")) {
            int pn = 1;
            int totalPage = 1;
            try {
                pn = getParaToInt("pn");
                totalPage = getParaToInt("totalPage");
                pn = checkPn(pn, totalPage);
            } catch (Exception e) {
                System.out.println(" pageNumber is invalid");
            }
            Page<Notification> noticePage = notificationService.getAllNotification(pn);
            setAttr("noticePage", noticePage);
        } else {
            int pn = 1;
            int totalPage = 1;
            try {
                pn = getParaToInt("pn");
                totalPage = getParaToInt("totalPage");
                pn = checkPn(pn, totalPage);
            } catch (Exception e) {
                System.out.println(" pageNumber is invalid");
            }
            Page<Notification> noticePage = notificationService.getAllNotification(pn);
            setAttr("noticePage", noticePage);
            setAttr("username", userName);
        }
        render("notice_list.html");
    }

    public void billList() {
        int pn = 1;
        try {
            pn = getParaToInt(pn);
        } catch (Exception e) {
            System.out.println(" pageNumber is invalid");
        }
        Page<Bill> billPage = billService.getAllBill(pn);
        setAttr("billPage", billPage);
        render("bill_list.html");
    }

    public void deleteTreeHole() {
        int id = getParaToInt("id");
        boolean isDelete = treeHoleService.deleteTreeHole(id);
        if (isDelete) {
            setAttr("isDelete", "true");
            treeHoleList();
        } else {
            setAttr("isDelete", "false");
            treeHoleList();
        }
    }

    public void deleteFlea() {
        int id = getParaToInt("id");
        boolean isDelete = fleaService.deleteFlea(id);
        if (isDelete) {
            setAttr("isDelete", "true");
            fleaList();
        } else {
            setAttr("isDelete", "false");
            fleaList();
        }
    }

    public void deleteLostFound() {
        int id = getParaToInt("id");
        boolean isDelete = lostFoundService.deleteLostFound(id);
        if (isDelete) {
            setAttr("isDelete", "true");
            lostFoundList();
        } else {
            setAttr("isDelete", "false");
            lostFoundList();
        }
    }


    public void notificationEdit(int id, String content) {
        boolean isEdit = notificationService.notificationEdit(id, content);
        if (isEdit) {
            setAttr("isEdit", "true");
            notificationList();
        } else {
            setAttr("isEdit", "false");
            notificationList();
        }
    }

    public void notificationSave(String content, int active) {
        boolean isSave;
        if (active == 1) {
            cancelNowActive();
            isSave = notificationService.notificationSave(content, active);
        } else {
            isSave = notificationService.notificationSave(content, active);
        }
        if (isSave) {
            setAttr("isSave", "true");
            notificationList();
        } else {
            setAttr("isSave", "false");
            notificationList();
        }
    }

    public void throughAuthencation(int id, int status) {
        userService.throughAuthentication(id, status);
        userList();
    }

    public void creatNotice() {

    }

    public void test() {
        render("TestEdit.html");
    }

    public void setActive(int id) {
        cancelNowActive();
        Notification notification = new Notification();
        notification.set("id", id);
        notification.set("active", NotificationType.ACTIVE);
        boolean b = notificationService.notificationUpData(notification);
        notificationList();
    }

    public void cancelNowActive() {
        Notification notification = notificationService.findNotificationByActive();
        if (notification == null) {
            System.out.println("当前并没有显示的公告");
        } else {
            int id = notification.getInt("id");
            boolean b = notificationService.cancelActive(id);
        }
        notificationList();
    }

}
