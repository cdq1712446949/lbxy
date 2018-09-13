package com.lbxy.controller;

import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.ehcache.CacheInterceptor;
import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.plugin.ehcache.CacheName;
import com.jfinal.plugin.ehcache.EvictInterceptor;
import com.lbxy.common.CacheNameConst;
import com.lbxy.common.NotificationType;
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

    public void userList(int pn) {
        Page<User> users = userService.getAllUsers(pn);
        setAttr("data", users);
        render("user_list.html");
    }

    public void search(String phoneNumber) {
        if (StringUtils.isBlank(phoneNumber)) {
            Page<User> users = userService.getAllUsers(1);
            setAttr("data", users);
        } else {
            Page<User> user = userService.findByPhone(phoneNumber);
            if (user.getList().size() == 0) {
                System.out.println("改手机号不存在");
                setAttr("error_search", "该手机号不存在");
            } else {
                setAttr("data", user);
            }
        }
        render("user_list.html");
    }

    //查询用户交易记录
    public void searchBill(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.equals("")) {
            redirect("./billList?pn=1");
        } else {
            setAttr("billPage", billService.getBillByPhoneNumber(1, phoneNumber));
            render("bill_list.html");
        }
    }

    public void searchTreehole(int pn, String content) {
        if (StringUtils.isBlank(content)) {
            redirect("./notificationList?pn=1");
        } else {
            setAttr("treeHolePage", treeHoleService.getTreeHoleByContent(pn, content));
            render("treehole_list.html");
        }
    }

    public void searchFlea(int pn, String content) {
        if (StringUtils.isBlank(content)) {
            redirect("./fleaList?pn=1");
        } else {
            setAttr("fleaPage", fleaService.getFleaByContent(pn, content));
            render("flea_list.html");
        }
    }

    public void searchLostFound(int pn, String content) {
        if (StringUtils.isBlank(content)) {
            redirect("./lostFoundList?pn=1");
        } else {
            setAttr("lostFoundPage", lostFoundService.getLostFoundByContent(pn, content));
            render("lostFound_list.html");
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

    @Before({CacheInterceptor.class})
    @CacheName("order")
    public void orderList(int pn) {
        Page<Order> orderPage = orderService.getAllOrder(pn);
        setAttr("orderPage", orderPage);
        render("order_list.html");
    }

    @Before({CacheInterceptor.class})
    @CacheName("treehole")
    public void treeHoleList(int pn) {
        Page<Treehole> treeHolePage = treeHoleService.getAllTreeHole(pn);
        setAttr("treeHolePage", treeHolePage);
        render("treehole_list.html");
    }

    @Before({CacheInterceptor.class})
    @CacheName("flea")
    public void fleaList(int pn) {
        Page<Flea> fleaPage = fleaService.getAllFlea(pn);
        setAttr("fleaPage", fleaPage);
        render("flea_list.html");
    }

    @Before({CacheInterceptor.class})
    @CacheName("lostfound")
    public void lostFoundList(int pn) {
        Page<Lostfound> lostFoundPage = lostFoundService.getAllLostFound(pn);
        setAttr("lostFoundPage", lostFoundPage);
        render("lostfound_list.html");
    }

    @Before({CacheInterceptor.class})
    @CacheName("notification")
    public void notificationList(int pn) {
        Page<Notification> noticePage = notificationService.getAllNotification(pn);
        setAttr("noticePage", noticePage);
        render("notice_list.html");
    }

    public void billList(int pn) {
        Page<Bill> billPage = billService.getAllBill(pn);
        setAttr("billPage", billPage);
        render("bill_list.html");
    }

    @Before({EvictInterceptor.class})
    @CacheName("treehole")
    public void deleteTreeHole(int id) {
        boolean isDelete = treeHoleService.deleteTreeHole(id);
        if (isDelete) {
            setAttr("isDelete", "true");
        } else {
            setAttr("isDelete", "false");
        }
        redirect("./treeHoleList?pn=1");
    }

    @Before({EvictInterceptor.class})
    @CacheName("flea")
    public void deleteFlea(int id) {
        boolean isDelete = fleaService.deleteFlea(id);
        if (isDelete) {
            setAttr("isDelete", "true");
        } else {
            setAttr("isDelete", "false");
        }
        redirect("./fleaList?pn=1");
    }

    @Before({EvictInterceptor.class})
    @CacheName("lostfound")
    public void deleteLostFound(int id) {
        boolean isDelete = lostFoundService.deleteLostFound(id);
        if (isDelete) {
            setAttr("isDelete", "true");
        } else {
            setAttr("isDelete", "false");
        }
        redirect("./lostFoundList?pn=1");
    }

    @Before({EvictInterceptor.class})
    @CacheName("notification")
    public void notificationEdit(int id, String content) {
        boolean isEdit = notificationService.notificationEdit(id, content);
        if (isEdit) {
            setAttr("isEdit", "true");
        } else {
            setAttr("isEdit", "false");
        }
        redirect("./notificationList?pn=1");
    }

    @Before({EvictInterceptor.class})
    @CacheName("notification")
    public void notificationSave(String content, int active) {
        boolean isSave = notificationService.notificationSave(content, active);
        if (isSave) {
            setAttr("isSave", "true");
        } else {
            setAttr("isSave", "false");
        }
        redirect("./notificationList?pn=1");
    }

    public void throughAuthencation(int id, int status) {
        userService.throughAuthentication(id, status);
        redirect("./userList?pn=1");
    }

    public void changeMoney(int id, int money) {
        User user = new User();
        user.set("id", id);
        user.set("balance", money);
        boolean i = userService.changeMoney(user);
        if (i) {
            setAttr("isChange", "true");
        } else {
            setAttr("isChange", "false");
        }
        redirect("./userList?pn=1");
    }

    @Before({EvictInterceptor.class})
    @CacheName("notification")
    public void setActive(int id) {
        Notification notification = new Notification();
        notification.set("id", id);
        notification.set("active", NotificationType.ACTIVE);
        boolean b = notificationService.notificationUpdate(notification);
        redirect("./notificationList?pn=1");
    }

    @Before({EvictInterceptor.class})
    @CacheName("notification")
    public void cancelNowActive(int id) {
        boolean b = notificationService.cancelActive(id);
        redirect("./notificationList?pn=1");
    }

}
