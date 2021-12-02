import constcode.Consts;
import model.*;
import service.JdbcService;
import service.UserInfoService;
import service.UserInfoServiceImpl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.Math;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


public class Main {

    private static User USER = null;

    public static UserInfoService userInfoService = new UserInfoServiceImpl();

    public static void JudgeUserRole(User user, JdbcService jdbcService){
        if (Consts.CONSUMER_TYPE.equals(user.getUserType())){
            USER = new ConsumerUser(user);
        } else if (Consts.SELLER_TYPE.equals(user.getUserType())) {
            USER = new SellerUser(user,jdbcService);
        } else if (Consts.MAINTAINER_TYPE.equals(user.getUserType())) {
            USER = new MaintainerUser(user,jdbcService);
        }
    }

    public static void main(String[] args) throws IOException {
        JdbcService jdbcService = new JdbcService();

        //商品清单


        Scanner n=new Scanner(System.in);
        System.out.println("——————欢迎进入购物管理系统——————");
        System.out.println("请在下方输入菜单序号：");
        System.out.println("【1】.登录系统");
        System.out.println("【2】.注册");
        System.out.println("【3】.退出");
        System.out.println("请输入菜单选项：");
        String usrname = null;
        String psw = null;
        Integer status = 0;
        User currentUser = null;
        Integer mode = n.nextInt();
        n.nextLine();
        switch(mode){
            case 2:
                while(status == 0) {
                    System.out.println("——————您当前处于注册系统界面——————");
                    System.out.println("请在下方输入账号：");
                    usrname = n.nextLine();
                    System.out.println("请在下方输入密码：");
                    psw = n.nextLine();
                    status = userInfoService.register(usrname,psw,jdbcService);
                    if (status == 1){
                        System.out.println("注册成功，请继续登录！");
                        break;
                    } else {
                        System.out.println("注册失败，已存在该账户，输入0退出，输入其他继续注册");
                        if (0 == n.nextInt()){
                            System.out.println("感谢您的使用！");
                            n.close();
                            return;
                        } else{
                            n.nextLine();
                            continue;
                        }
                    }
                }

            case 1:
                status = 0;
                while (status == 0){
                    System.out.println("——————您当前处于登录系统界面——————");
                    System.out.println("请在下方输入账号：");
                    usrname = n.nextLine();
                    System.out.println("请在下方输入密码：");
                    psw = n.nextLine();

                    currentUser = userInfoService.Login(usrname,psw,jdbcService);
                    if (currentUser == null) {
                        System.out.println("登陆失败，用户名或密码错误");
                    } else if (currentUser.getUserid() == null){
                        System.out.println("登陆失败，用户已被删除，请联系管理员");
                    } else {
                        System.out.println("——————登陆成功——————");
                        status = 1;
                    }
                }
                break;
            case 3:
                System.out.println("感谢您的使用！");
                n.close();
                break;
            default:
                System.out.println("没有此项菜单选项！");
                n.close();
        }

        JudgeUserRole(currentUser,jdbcService);
        Integer type = USER.getUserType();

        System.out.println("——————您当前处于系统界面——————");
        System.out.println("——————欢迎：" + USER.getUsername() + "——————");

        while(status == 1){
            //用户操作
            if (Consts.CONSUMER_TYPE.equals(type) || Consts.SELLER_TYPE.equals(type)) {
                System.out.println("请在下方输入菜单序号：");
                System.out.println("【1】.查看商品列表");
                System.out.println("【2】.查看已购商品");
                if (Consts.CONSUMER_TYPE.equals(type)) {
                    System.out.println("【3】.成为卖家");
                } else if (Consts.SELLER_TYPE.equals(type)) {
                    System.out.println("【3】.查看我的商品");
                    System.out.println("【4】.商品上架");
                }
                System.out.println("【0】.退出");
                List<GameCard> goodsList;
                switch(n.nextInt()){
                    case 1:
                        n.nextLine();
                        goodsList = jdbcService.getGoodsList();
                        System.out.println("编号        名称        价格        上架日期");
                        for (GameCard g : goodsList) {
                            System.out.println(g.getCardId() + "        " + g.getName() + "        " +
                                    g.getPrice() + "        " + g.getPushdate());
                        }
                        System.out.println("----------请选择要购买的产品id,输入回车退出----------");
                        String goodid = n.nextLine();
                        if (goodid == null || "".equals(goodid)){
                            break;
                        } else {
                            //将物品id添加到用户名下，更新boughtid和boughttime
                            if (jdbcService.buyGoods(USER,goodid)){
                                System.out.println("----------购买成功----------");
                            } else {
                                System.out.println("----------购买失败，请重新操作----------");
                            }
                            break;
                        }
                    case 2:
                        System.out.println("——————已购商品清单————————");
                        goodsList = jdbcService.getBoughtGoodsList(USER);
                        System.out.println("编号        名称        价格        上架日期");
                        for (GameCard g : goodsList) {
                            System.out.println(g.getCardId() + "        " + g.getName() + "        " +
                                    g.getPrice() + "        " + g.getPushdate());
                        }
                        break;
                    case 3:
                        if (Consts.CONSUMER_TYPE.equals(type)) {
                            jdbcService.becomeSeller(USER);
                            System.out.println("成功");

                        } else if (Consts.SELLER_TYPE.equals(type)) {
                            System.out.println("——————我的商品清单————————");
                            goodsList = jdbcService.getMyGoodsList(USER);
                            System.out.println("编号        名称        价格        上架日期");
                            for (GameCard g : goodsList) {
                                System.out.println(g.getCardId() + "        " + g.getName() + "        " +
                                        g.getPrice() + "        " + g.getPushdate());
                            }
                            Integer num = 1;
                            while (num != 0) {
                                System.out.println("请在下方输入操作序号：");
                                System.out.println("【1】.修改商品价格");
                                System.out.println("【2】.下架商品");
                                System.out.println("【0】.退出");
                                num = n.nextInt();
                                if (num == 1) {
                                    n.nextLine();
                                    System.out.println("请在下方输入商品编号：");
                                    goodid = n.nextLine();
                                    System.out.println("请在下方输入修改后的价格：");
                                    Integer price = n.nextInt();
                                    if (jdbcService.changePrice(USER, goodid,price)) {
                                        System.out.println("修改成功");
                                    } else {
                                        System.out.println("修改失败");
                                    }
                                } else if (num == 2) {
                                    n.nextLine();
                                    System.out.println("请在下方输入要下架的商品编号：");
                                    goodid = n.nextLine();
                                    if (jdbcService.deleteGood(USER, goodid)) {
                                        System.out.println("下架成功");
                                    } else {
                                        System.out.println("下架失败");
                                    }
                                }
                            }


                        }
                        break;
                    case 4:
                        String flag = "1";
                        n.nextLine();
                        while (!"0".equals(flag)) {
                            System.out.println("请输入要上架的商品信息");
                            System.out.println("请输入商品名");
                            String storename = n.nextLine();
                            System.out.println("请输入价格");
                            Integer storeprice = n.nextInt();
                            n.nextLine();
                            jdbcService.addGoods(USER,storename,storeprice);
                            System.out.println("输入其他继续上架，输入0退出");
                            flag = n.nextLine();
                        }
                        break;
                    case 0:
                        System.out.println("——————感谢使用————————");
                        return;
                    default:
                        System.out.println("没有此项菜单选项！");
                }
            } else {
                System.out.println("请在下方输入操作序号：");
                System.out.println("【1】.查看用户列表");
                System.out.println("【2】.查看商品列表");
                System.out.println("【0】.退出");
            }
        }



    }

}
