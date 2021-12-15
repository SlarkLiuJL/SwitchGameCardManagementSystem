import constcode.Consts;
import model.*;
import service.JdbcService;
import service.UserInfoService;
import service.UserInfoServiceImpl;

import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;


public class Main {


    public static UserInfoService userInfoService = new UserInfoServiceImpl();

    //删除状态映射
    public static Map<Integer,String> StatusMap = new HashMap<>();
    //用户角色映射
    public static Map<Integer,String> RoleMap = new HashMap<>();

    private static ConsumerUser consumerUser;

    private static SellerUser sellerUser;

    private  static MaintainerUser maintainerUser;



    public static void main(String[] args) throws IOException {
        //初始化
        JdbcService jdbcService = new JdbcService();
        StatusMap.put(0,"正常");
        StatusMap.put(1,"已下架");
        StatusMap.put(2,"已冻结");

        RoleMap.put(1,"买家");
        RoleMap.put(2,"卖家");
        RoleMap.put(3,"管理员");

        NumberFormat numberFormat = NumberFormat.getIntegerInstance();
        DecimalFormat numberDecimalFormat;
        try {
            numberDecimalFormat = (DecimalFormat) numberFormat;
            numberDecimalFormat.applyPattern("#");
        } catch (Exception e) {

        }

        //商品清单
        Scanner n=new Scanner(System.in);
        System.out.println("——————欢迎进入火锅商品管理系统——————");
        System.out.println("请在下方输入菜单序号：");
        System.out.println("【1】.登录系统");
        System.out.println("【2】.注册");
        System.out.println("【3】.退出");
        System.out.println("请输入菜单选项：");
        String usrname = null;
        String psw = null;
        int status = 0;
        User currentUser = null;
        int mode = n.nextInt();
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
                        System.out.println("登陆失败，用户不存在，请联系管理员");
                    } else if (currentUser.getIsdelete() != 0){
                        System.out.println("登陆失败，用户已被冻结，请联系管理员");
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



        System.out.println("——————您当前处于系统界面——————");
        System.out.println("——————欢迎：" + currentUser.getUsername() + "——————");

        while(status == 1){
            //用户操作
            if (Consts.CONSUMER_TYPE.equals(currentUser.getUserType())) {
                consumerUser = new ConsumerUser(currentUser);
                System.out.println("请在下方输入菜单序号：");
                System.out.println("【1】.查看商品列表");
                System.out.println("【2】.查看已购商品");
                System.out.println("【3】.成为卖家");
                System.out.println("【0】.退出");
                List<GoodInfo> goodsList;

                switch(n.nextInt()){
                    case 1:
                        n.nextLine();
                        goodsList = jdbcService.getGoodsList();
                        System.out.println("编号                         名称        价格        上架日期");
                        for (GoodInfo g : goodsList) {
                            System.out.println(g.getCardId() + "        " + g.getName() + "        " +
                                    g.getPrice() + "        " + g.getPushdate());
                        }
                        System.out.println("----------请选择要购买的产品id,输入回车退出----------");
                        String goodid = n.nextLine();
                        if (goodid == null || "".equals(goodid)){
                            break;
                        } else {
                            //将物品id添加到用户名下，更新boughtid和boughttime
                            if (jdbcService.buyGoods(consumerUser,goodid)){
                                System.out.println("----------购买成功----------");
                            } else {
                                System.out.println("----------购买失败，请重新操作----------");
                            }
                            break;
                        }
                    case 2:
                        System.out.println("——————已购商品清单————————");
                        goodsList = jdbcService.getBoughtGoodsList(consumerUser);
                        System.out.println("编号                         名称        价格        上架日期");
                        for (GoodInfo g : goodsList) {
                            System.out.println(g.getCardId() + "        " + g.getName() + "        " +
                                    g.getPrice() + "        " + g.getPushdate());
                        }
                        break;
                    case 3:
                            jdbcService.becomeSeller(consumerUser);
                            System.out.println("成功");
                            currentUser = consumerUser;
                        break;
                    case 0:
                        System.out.println("——————感谢使用————————");
                        return;
                    default:
                        System.out.println("没有此项菜单选项！");
                }

            } else if (Consts.SELLER_TYPE.equals(currentUser.getUserType())){
                sellerUser = new SellerUser(currentUser);
                System.out.println("请在下方输入菜单序号：");
                System.out.println("【1】.查看商品列表");
                System.out.println("【2】.查看已购商品");
                System.out.println("【3】.查看我的商品");
                System.out.println("【4】.商品上架");
                System.out.println("【0】.退出");
                List<GoodInfo> goodsList;
                switch(n.nextInt()){
                    case 1:
                        n.nextLine();
                        goodsList = jdbcService.getGoodsList();
                        System.out.println("编号                         名称        价格        上架日期");
                        for (GoodInfo g : goodsList) {
                            System.out.println(g.getCardId() + "        " + g.getName() + "        " +
                                    g.getPrice() + "        " + g.getPushdate());
                        }
                        System.out.println("----------请选择要购买的产品id,输入回车退出----------");
                        String goodid = n.nextLine();
                        if (goodid == null || "".equals(goodid)){
                            break;
                        } else {
                            //将物品id添加到用户名下，更新boughtid和boughttime
                            if (jdbcService.buyGoods(sellerUser,goodid)){
                                System.out.println("----------购买成功----------");
                            } else {
                                System.out.println("----------购买失败，请重新操作----------");
                            }
                            break;
                        }
                    case 2:
                        System.out.println("——————已购商品清单————————");
                        goodsList = jdbcService.getBoughtGoodsList(sellerUser);
                        System.out.println("编号                         名称        价格        上架日期");
                        for (GoodInfo g : goodsList) {
                            System.out.println(g.getCardId() + "        " + g.getName() + "        " +
                                    g.getPrice() + "        " + g.getPushdate());
                        }
                        break;
                    case 3:
                        System.out.println("——————我的商品清单————————");
                        goodsList = jdbcService.getMyGoodsList(sellerUser);
                        System.out.println("编号                         名称        价格        上架日期");
                        for (GoodInfo g : goodsList) {
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
                                if (jdbcService.changePrice(sellerUser, goodid,price)) {
                                    System.out.println("修改成功");
                                } else {
                                    System.out.println("修改失败");
                                }
                            } else if (num == 2) {
                                n.nextLine();
                                System.out.println("请在下方输入要下架的商品编号：");
                                goodid = n.nextLine();
                                if (jdbcService.deleteGood(sellerUser, goodid)) {
                                    System.out.println("下架成功");
                                } else {
                                    System.out.println("下架失败");
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
                            jdbcService.addGoods(sellerUser,storename,storeprice);
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
                maintainerUser = new MaintainerUser(currentUser,jdbcService);
                System.out.println("请在下方输入操作序号：");
                System.out.println("【1】.查看商品列表");
                System.out.println("【2】.查看用户列表");
                System.out.println("【0】.退出");
                status = n.nextInt();
                n.nextLine();
                int flag = 1;
                switch(status){
                    case 1:
                        List<GoodInfo> goodsList;
                        status = 1;

                        while (flag != 0) {
                            System.out.println("——————商品清单————————");
                            goodsList = jdbcService.showAllGoodsForMaintainer(maintainerUser);
                            System.out.println("编号                         名称        价格        上架日期          状态          是否售出");
                            for (GoodInfo g : goodsList) {
                                System.out.println(g.getCardId() + "        " + g.getName() + "        " +
                                        g.getPrice() + "        " + g.getPushdate() + "          " + StatusMap.get(g.getIsdelete())
                                        + "          " + (g.getBoughtUserId() == null ? "未售出" : "已售出"));
                            }
                            System.out.println("请在下方输入操作序号：");
                            System.out.println("【1】.强制下架商品");
                            System.out.println("【2】.恢复下架商品");
                            System.out.println("【0】.返回");
                            flag = n.nextInt();
                            n.nextLine();
                            if (flag == 1) {
                                System.out.println("请在下方输入要下架的商品编号：");
                                String goodid = n.nextLine();
                                System.out.println(maintainerUser.deleteGoods(goodid, 1));
                            } else if (flag == 2) {
                                System.out.println("请在下方输入要恢复的商品编号：");
                                String goodid = n.nextLine();
                                System.out.println(maintainerUser.deleteGoods(goodid, 0));
                            }
                        }
                        break;
                    case 2:
                        status = 1;
                        while (flag != 0) {
                            List<User> userList = jdbcService.getAllUsersForMaintainer(maintainerUser);
                            System.out.println("——————用户列表————————");
                            System.out.println("编号        用户名        用户角色          状态");
                            for (User g : userList) {
                                System.out.println(g.getUserid() + "        " + g.getUsername() + "        " +
                                        RoleMap.get(g.getUserType()) + "        "  + StatusMap.get(g.getIsdelete()));
                            }
                            System.out.println("请在下方输入操作序号：");
                            System.out.println("【1】.强制冻结账号");
                            System.out.println("【2】.恢复冻结账号");
                            System.out.println("【0】.返回");
                            flag = n.nextInt();
                            n.nextLine();
                            if (flag == 1) {
                                System.out.println("请在下方输入要冻结的用户编号：");
                                String userid = n.nextLine();
                                System.out.println(maintainerUser.deleteUser(userid, 2));
                            } else if (flag == 2) {
                                System.out.println("请在下方输入要恢复的用户编号：");
                                String userid = n.nextLine();
                                System.out.println(maintainerUser.deleteUser(userid, 0));
                            }
                        }
                        break;
                    case 0:
                        System.out.println("感谢您的使用！");
                        n.close();
                        break;
                    default:
                        System.out.println("没有此项菜单选项！");
                }


            }
        }



    }

}
