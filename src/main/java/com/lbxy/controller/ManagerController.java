package com.lbxy.controller;

import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.ehcache.CacheInterceptor;
import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.plugin.ehcache.CacheName;
import com.jfinal.plugin.ehcache.EvictInterceptor;
import com.lbxy.common.CacheNameConst;
import com.lbxy.common.ImageType;
import com.lbxy.common.NotificationType;
import com.lbxy.common.status.CommonStatus;
import com.lbxy.common.status.UserStatus;
import com.lbxy.core.interceptors.ManagerLoginInterceptor;
import com.lbxy.model.*;
import com.lbxy.service.*;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Range;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;

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

    @Resource
    private ImageService imageService;

    @Clear({ManagerLoginInterceptor.class})
    public void index() {
        setAttr("error", "");
        render("login.html");
    }

    public void userList(int pn) {
        if (pn <= 0) pn = 1;
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
        if (StringUtils.isBlank(phoneNumber)) {
            redirect("/back/billList?pn=1");
        } else {
            setAttr("billPage", billService.getBillByPhoneNumber(1, phoneNumber));
            render("bill_list.html");
        }
    }

    public void searchTreehole(int pn, String content) {
        if (pn <= 0) pn = 1;
        if (StringUtils.isBlank(content)) {
            redirect("/back/treehole_list?pn=1");
        } else {
            setAttr("treeHolePage", treeHoleService.getTreeHoleByContent(pn, content));
            render("treehole_list.html");
        }
    }

    public void searchFlea(int pn, String content) {
        if (pn <= 0) pn = 1;
        if (StringUtils.isBlank(content)) {
            redirect("/back/fleaList?pn=1");
        } else {
            setAttr("fleaPage", fleaService.getFleaByContent(pn, content));
            render("flea_list.html");
        }
    }

    public void searchLostFound(int pn, String content) {
        if (pn <= 0) pn = 1;
        if (StringUtils.isBlank(content)) {
            redirect("/back/lostFoundList?pn=1");
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
        if (pn <= 0) pn = 1;
        Page<Order> orderPage = orderService.getAllOrder(pn);
        setAttr("orderPage", orderPage);
        render("order_list.html");
    }

    @Before({CacheInterceptor.class})
    @CacheName("treehole")
    public void treeHoleList(int pn) {
        if (pn <= 0) pn = 1;
        Page<Treehole> treeHolePage = treeHoleService.getAllTreeHole(pn);
        setAttr("treeHolePage", treeHolePage);
        render("treehole_list.html");
    }

    @Before({CacheInterceptor.class})
    @CacheName("flea")
    public void fleaList(int pn) {
        if (pn <= 0) pn = 1;
        Page<Flea> fleaPage = fleaService.getAllFlea(pn);
        setAttr("fleaPage", fleaPage);
        render("flea_list.html");
    }

    @Before({CacheInterceptor.class})
    @CacheName("lostfound")
    public void lostFoundList(int pn) {
        if (pn <= 0) pn = 1;
        Page<Lostfound> lostFoundPage = lostFoundService.getAllLostFound(pn);
        setAttr("lostFoundPage", lostFoundPage);
        render("lostfound_list.html");
    }

    @Before({CacheInterceptor.class})
    @CacheName("notification")
    public void notificationList(int pn) {
        if (pn <= 0) pn = 1;
        Page<Notification> noticePage = notificationService.getAllNotification(pn);
        setAttr("noticePage", noticePage);
        render("notice_list.html");
    }

    public void billList(int pn) {
        if (pn <= 0) pn = 1;
        Page<Bill> billPage = billService.getAllBill(pn);
        setAttr("billPage", billPage);
        render("bill_list.html");
    }

    public void imageList(int pn) {
        if (pn <= 0) pn = 1;
        Page<Image> imagePage = imageService.getIndexImagesByPage(pn);
        setAttr("imagePage", imagePage);
        render("image_list.html");
    }

    @Before({EvictInterceptor.class})
    @CacheName("treehole")
    public void deleteTreeHole(@Range(min = 1) int id) {
        boolean isDelete = treeHoleService.deleteTreeHole(id);
        if (isDelete) {
            setAttr("isDelete", "true");
        } else {
            setAttr("isDelete", "false");
        }
        redirect("/back/treeHoleList?pn=1");
    }

    @Before({EvictInterceptor.class})
    @CacheName("flea")
    public void deleteFlea(@Range(min = 1) int id) {
        boolean isDelete = fleaService.deleteFlea(id);
        if (isDelete) {
            setAttr("isDelete", "true");
        } else {
            setAttr("isDelete", "false");
        }
        redirect("/back/fleaList?pn=1");
    }

    @Before({EvictInterceptor.class})
    @CacheName("lostfound")
    public void deleteLostFound(@Range(min = 1) int id) {
        boolean isDelete = lostFoundService.deleteLostFound(id);
        if (isDelete) {
            setAttr("isDelete", "true");
        } else {
            setAttr("isDelete", "false");
        }
        redirect("/back/lostFoundList?pn=1");
    }

    @Before({EvictInterceptor.class})
    @CacheName("notification")
    public void notificationEdit(@Range(min = 1) int id, @NotBlank String content,@NotBlank String title) {
        boolean isEdit = notificationService.notificationEdit(id,title, content);
        if (isEdit) {
            setAttr("isEdit", "true");
        } else {
            setAttr("isEdit", "false");
        }
        redirect("/back/notificationList?pn=1");
    }

    @Before({EvictInterceptor.class})
    @CacheName("notification")
    public void notificationSave(@NotBlank String title,@NotBlank String content, @Range(min = 0, max = 1) int active) {
        boolean isSave = notificationService.notificationSave(title,content, active);
        if (isSave) {
            setAttr("isSave", "true");
        } else {
            setAttr("isSave", "false");
        }
        redirect("/back/notificationList?pn=1");
    }

    public void throughAuthencation(@Range(min = 1) int id) {
        userService.updateUserStatus(id, UserStatus.AUTHENTACATED);
        redirect("/back/userList?pn=1");
    }

    public void cancelAuthencation(@Range(min = 1) int id) {
        userService.updateUserStatus(id, UserStatus.UNAUTHENTICATION);
        redirect("/back/userList?pn=1");
    }

    public void blockUser(@Range(min = 1) int id) {
        userService.updateUserStatus(id, UserStatus.BLOCKED);
        redirect("/back/userList?pn=1");
    }

    public void unBlockUser(@Range(min = 1) int id) {
        userService.updateUserStatus(id, UserStatus.UNAUTHENTICATION);
        redirect("/back/userList?pn=1");
    }

    public void changeMoney(@Range(min = 1) int id, @Range double money) {
        User user = new User();
        user.set("id", id);
        user.set("balance", money);
        boolean i = userService.changeMoney(user);
        if (i) {
            setAttr("isChange", "true");
        } else {
            setAttr("isChange", "false");
        }
        redirect("/back/userList?pn=1");
    }

    @Before({EvictInterceptor.class})
    @CacheName("notification")
    public void setActive(@Range(min = 1) int id) {
        Notification notification = new Notification();
        notification.set("id", id);
        notification.set("active", NotificationType.ACTIVE);
        boolean b = notificationService.notificationUpdate(notification);
        redirect("/back/notificationList?pn=1");
    }

    @Before({EvictInterceptor.class})
    @CacheName("notification")
    public void cancelNowActive(@Range(min = 1) int id) {
        boolean b = notificationService.cancelActive(id);
        redirect("/back/notificationList?pn=1");
    }

    public void createdImage(@NotBlank String location) {
        Image image = new Image();
        image.set("type",ImageType.INDEX_SWIPER);
        image.set("location", location);
        image.setType(ImageType.INDEX_SWIPER);
        boolean b = imageService.saveImage(image);
        redirect("/back/imageList?pn=1");
    }

    public void deleteIndexImage(@Range(min = 1)long id){
        Image image=new Image();
        image.set("id",id);
        image.set("status", CommonStatus.DELETED);
        boolean b=imageService.deleteImage(image);
        redirect("/back/imageList?pn=1");
    }

    public void findImage(int type,int id){
        Page<Image> imagePage=imageService.findImage(type,id);
        setAttr("imagePage",imagePage);
        render("findImage_list.html");
    }

}
