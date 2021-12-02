package service;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import constcode.Consts;
import model.GameCard;
import model.User;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Date;

public class JdbcService {

    private final String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private final String dbUrl = "jdbc:sqlserver://localhost:1433;DatabaseName=model";
    private final String userName = "sa";
    private final String userPwd = "123456";

    private final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式

    Connection dbConn;

    PreparedStatement ps = null;

    Statement st = null;

    ResultSet rs = null;

    public String getUuid(){
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        return uuid;
    }

    public JdbcService(){

        try {
            Class.forName(driverName);
            dbConn = DriverManager.getConnection(dbUrl,userName,userPwd);
            System.out.println("OK");
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    protected void finalize() throws Throwable {
        dbConn.close();
        ps.close();
        rs.close();
        super.finalize();
    }

    private ResultSet execSql(String sql) throws SQLException {
        ps = dbConn.prepareStatement(sql);
        rs = ps.executeQuery();
        return rs;
    }

    private void execSql(String sql, Integer isInsert) throws SQLException {
        st = dbConn.createStatement();
        st.execute(sql);
    }

    public String getPsw(String usrName) {
        try{
            String sql = "select password from [user] where username = '" + usrName + "';";
            rs = execSql(sql);
            rs.next();
            return rs.getString(1);
        } catch (SQLException throwables) {
            //throwables.printStackTrace();
            return "";
        }
    }

    public Boolean isGoodExists(String goodid){
        try {
            String sql = "select * from [card] where cardid = '" + goodid + "';";
            rs = execSql(sql);
            rs.next();
            if (rs != null) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }


    public User getUser(String usn, String psw){
        try {
            String sql = "select * from [user] where username = '" + usn + "' and password = '" + psw + "';";
            rs = execSql(sql);
            rs.next();
            if (rs.getInt(5) == 1) {
                return new User();
            }
            return new User(rs.getString(2),rs.getString(3),rs.getString(1),rs.getInt(4));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    public Boolean isExists(String usn){
        try {
            String sql = "select * from [user] where username = '" + usn  + "';";
            rs = execSql(sql);
            rs.next();
            if (rs == null) {
                return false;
            } else {
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    public void register(String username, String password) {
        try {
            String sql = "insert into [user](userid,username,password,usertype) values " +
                    "('" + getUuid() + "','" + username + "','" + password + "'," + Consts.CONSUMER_TYPE + ")";
            execSql(sql,1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<GameCard> getMyGoodsList(User user) {
        String userid = user.getUserid();
        List<GameCard> list = new ArrayList();
        try {
            String sql = "select cardid,name,price,year,owneruserid,boughtuserid,pushdate,boughtdate from [card] " +
                    "where owneruserid = '" + userid + "' and isdelete = 0 and boughtuserid is null";

            rs = execSql(sql);
            while (rs.next()) {
                GameCard g = new GameCard(rs.getString("cardid"),rs.getString("name"),
                        rs.getInt("price"),rs.getInt("year"),
                        rs.getString("owneruserid"),rs.getString("boughtuserid"),
                        rs.getDate("pushdate"),rs.getDate("boughtdate"));
                list.add(g);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }

    public List<GameCard> getGoodsList (){
        List<GameCard> list = new ArrayList();
        try {
            String sql = "select cardid,name,price,year,owneruserid,boughtuserid,pushdate,boughtdate from [card] " +
                    " where isdelete = 0 and boughtuserid is null";

            rs = execSql(sql);
            while (rs.next()) {
                GameCard g = new GameCard(rs.getString("cardid"),rs.getString("name"),
                        rs.getInt("price"),rs.getInt("year"),
                        rs.getString("owneruserid"),rs.getString("boughtuserid"),
                        rs.getDate("pushdate"),rs.getDate("boughtdate"));
                list.add(g);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }

    public List<GameCard> getBoughtGoodsList (User user){
        List<GameCard> list = new ArrayList();
        try {
            String sql = "select cardid,name,price,year,owneruserid,boughtuserid,pushdate,boughtdate from [card] " +
                    " where isdelete = 0 and boughtuserid = '" + user.getUserid() + "'; ";

            rs = execSql(sql);
            while (rs.next()) {
                GameCard g = new GameCard(rs.getString("cardid"),rs.getString("name"),
                        rs.getInt("price"),rs.getInt("year"),
                        rs.getString("owneruserid"),rs.getString("boughtuserid"),
                        rs.getDate("pushdate"),rs.getDate("boughtdate"));
                list.add(g);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }

    public Boolean buyGoods(User user, String goodid) {
        Date d = new Date();
        try {
            if (isGoodExists(goodid)) {
                String sql = "update [card] set boughtuserid = '" + user.getUserid() + "' , boughtdate = '" + df.format(d) +
                        "' where cardid = '" + goodid + "'; ";

                execSql(sql,1);
                return true;
            } else {
                return false;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public Boolean becomeSeller(User user) {

        try {
            String sql = "update [user] set usertype = 2" +
                    " where userid = '" + user.getUserid() + "'; ";
            execSql(sql,1);
            user.setUserType(2);
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public Boolean addGoods(User user, String name, Integer price) {
        try {
            String sql = "insert into card(cardid,name,price,year,owneruserid,boughtuserid,pushdate,boughtdate) " +
                    "values ('" + getUuid() + "','" + name + "'," + price + ",2021,'" + user.getUserid() + "',null," +
                    "CONVERT(varchar(10),GETDATE(),120),null)";
            execSql(sql,1);
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public Boolean changePrice(User user,String goodid, Integer price) {
        try {
            if (isGoodExists(goodid)) {
                String sql = "update [card] set price = " + price +
                        " where cardid = '" + goodid + "' and owneruserid = '" + user.getUserid() + "'; ";
                execSql(sql,1);
                return true;
            } else {
                return false;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public Boolean deleteGood(User user, String goodid) {
        try {
            if (isGoodExists(goodid)) {
                String sql = "update [card] set isdelete = 1 where cardid = '" + goodid + "' and owneruserid = '" + user.getUserid() + "'; ";
                execSql(sql,1);
                return true;
            } else {
                return false;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

}
