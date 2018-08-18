package com.lbxy.controller;

import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.ehcache.CacheKit;
import com.lbxy.model.*;
import com.lbxy.service.*;
import com.lbxy.service.impl.*;
import org.apache.commons.lang3.StringUtils;

public class ManagerController extends BaseController {

    private ManagerService managerService;

    private UserService userService;

    private OrderService orderService;

    private TreeHoleService treeHoleService;

    private FleaService fleaService;

    private NoticeService noticeService;

    private LostFoundService lostFoundService;

    private BillService billService;

    private String userNmae = "null";

    public ManagerController() {
        userService = new UserServiceImpl();
        managerService = new ManagerServiceImpl();
        orderService = new OrderServiceImpl();
        treeHoleService = new TreeHoleServiceImpl();
        fleaService = new FleaServiceImpl();
        lostFoundService = new LostFoundServiceImpl();
        noticeService = new NoticeServiceImpl();
        billService = new BillServiceImpl();
    }

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

    //查询公告
//    public void searchNotice(){
//        String userName = getPara("userName");
//        if (StringUtils.isBlank(userName)) {
//            Page<Notice> noticePage = noticeService.getAllNotice(1);
//            setAttr("noticePage", noticePage);
//        } else {
//            Page<Notice> noticePage=noticeService.findByUserName();
//            if (noticePage.getList().size() == 0) {
//                System.out.println("改手机号不存在");
//                setAttr("error_search", "该手机号不存在");
//            } else {
//                setAttr("noticePage", noticePage);
//            }
//        }
//        render("notice_list.html");
//    }

    public void login() {
        String username = getPara("username");
        String password = getPara("password");
        int i = managerService.login(username, password);
        if (i == 0) {
            setAttr("error", "账号不存在");
            return;
        }
        if (i == 1) {
            setAttr("error", "密码错误");
            render("login.html");
            return;
        }
        if (i == 2) {
            String sid=getSession().getId();
//            CacheKit.put("LoginUserCache", sid,managerService.getManager(username) ); // 将用户信息保存到缓存，用作超时判断
            setAttr("userName", ManagerServiceImpl.MANAGER.getStr("userName"));
            render("index.html");
            return;
        }
        System.out.println(username + password);
    }

    public void orderList() {
        int pn = 1;
        try {
            pn = getParaToInt("pn");
        } catch (Exception e) {
            System.out.println(" pageNumber is invalid");
        }
        Page<Order> orderPage = orderService.getAllOrder(pn);
        setAttr("orderpage", orderPage);
        render("order_list.html");
    }

    public void treeHoleList() {
        int pn = 1;
        try {
            pn = getParaToInt(pn);
        } catch (Exception e) {
            System.out.println(" pageNumber is invalid");
        }
        Page<TreeHole> treeHolePage = treeHoleService.getAllTreeHole(pn);
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
        Page<LostFound> lostFoundPage = lostFoundService.getAllLostFound(pn);
        setAttr("lostFoundPage", lostFoundPage);
        render("lostfound_list.html");
    }

    public void noticeList() {
        if (getPara("userName") == null) {
            if (getPara("user") == null) {
                System.out.println("判断username值为null");
            } else {
                userNmae = getPara("user");
            }
        } else {
            userNmae = getPara("userName");
        }
        if (userNmae.equals("null")) {
            int pn = 1;
            try {
                pn = getParaToInt(pn);
            } catch (Exception e) {
                System.out.println(" pageNumber is invalid");
            }
            Page<Notice> noticePage = noticeService.getAllNotice(pn);
            setAttr("noticePage", noticePage);
        } else {
            int pn = 1;
            try {
                pn = getParaToInt(pn);
            } catch (Exception e) {
                System.out.println(" pageNumber is invalid");
            }
            Page<Notice> noticePage = noticeService.findByUserName(pn, userNmae);
            setAttr("noticePage", noticePage);
            setAttr("username", userNmae);
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

    public void deleteNotice() {
        int id = getParaToInt("id");
        boolean isDelete = noticeService.deleteNotice(id);
        if (isDelete) {
            setAttr("isDelete", "true");
            noticeList();
        } else {
            setAttr("isDelete", "false");
            noticeList();
        }
    }

    public void noticeEdit(int id, String content, String title) {
        boolean isEdit = noticeService.noticeEdit(id, content, title);
        if (isEdit) {
            setAttr("isEdit", "true");
            noticeList();
        } else {
            setAttr("isEdit", "false");
            noticeList();
        }
    }

    public void throughAuthencation(int id,int status){
        userService.throughAuthencation(id,status);
        userList();
    }

    public void creatNotice() {

    }

    public void test() {
        render("TestEdit.html");
    }

}
