import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.Math;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.Scanner;


public class Main {

    private static void writeToFile() throws IOException {
        String writerContent = "";// 要写入的文本
        File file = new File("D:\\t_sfzx_tj_lapydzjb.txt");// 要写入的文本文件
        if (!file.exists()) {// 如果文件不存在，则创建该文件
            file.createNewFile();
        }
        FileWriter writer = new FileWriter(file);// 获取该文件的输出流
        Random r = new Random(1);
        int f = 0;
        for (int i = 0 ;i < 5054; i ++ ) {
            for (int j = 0; j < 90; j++){
            writerContent = "insert into t_sfzx_tj_lapydzjb VALUES ("+"'"+(1 + f)+"','" +(i+1)+"',"+"'"+r.nextInt(j + 1)+"',"
                    +"'"+calcTime("", -(j+1))+"');"
                    + "\r\n";
            f++;
            writer.write(writerContent);// 写内容
            }
        }
        writer.flush();// 清空缓冲区，立即将输出流里的内容写到文件里
        writer.close();// 关闭输出流，施放资源
    }

    /**
     *
     * @param type 往前计算的类型（week、month、year，“”表示day
     * @param count 往前计算的数量
     * @return
     */
    private static String calcTime(String type ,int count){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        if (type .equals("week")) {
            //周
            calendar.add(Calendar.WEEK_OF_YEAR, count);
        }else if (type .equals("month")) {
            //月
            calendar.add(Calendar.MONTH, count);
        }else if (type .equals("year")) {
            //12个月
            calendar.add(Calendar.MONTH, count);
        }else {
            //
            calendar.add(Calendar.DATE, count);
        }
        java.util.Date date = calendar.getTime();
        return sdf.format(date);

    }

    public static void main(String[] args) throws IOException {
        Scanner n=new Scanner(System.in);
        System.out.println("——————欢迎进入购物管理系统——————");
        System.out.println("请在下方输入菜单序号：");
        System.out.println("【1】.登录系统");
        System.out.println("【2】.退出");
        System.out.println("请输入菜单选项：");
        switch(n.nextInt()){
            case 1:
            System.out.println("——————您当前处于登录系统界面——————");
            System.out.println("请在下方输入账号：");
            System.out.println("请在下方输入密码：");

            switch(n.nextInt()){
                    case 1:
                        System.out.println("——————您当前处于客户信息系统界面——————");
                        System.out.println("请在下方输入菜单序号：");
                        System.out.println("【1】.所有客户信息");
                        System.out.println("【2】.增加客户");
                        System.out.println("【3】.修改客户信息");
                        System.out.println("【4】.查询客户信息");
                        n.close();
                        break;
                    case 2:
                        System.out.println("——————当前系统界面暂未开放————————");
                        break;
                    case 3:
                        System.out.println("——————您当前处于购物管理系统界面——————");
                        System.out.println("请在下方输入菜单序号：");
                        System.out.println("【1】.幸运大放送");
                        System.out.println("【2】.幸运抽奖");
                        System.out.println("【3】.生日问候");
                        break;
                    case 4:
                        System.out.println("——————当前系统界面暂未开放————————");
                        break;
                    default:
                        System.out.println("没有此项菜单选项！");

                }
                break;
            case 2:
                System.out.println("感谢您的使用！");
                n.close();
                break;
            default:
                System.out.println("没有此项菜单选项！");
                n.close();
        }

    }

}
