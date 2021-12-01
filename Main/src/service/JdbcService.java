package service;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import constcode.Consts;
import model.GameCard;
import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JdbcService {

    private String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private String dbUrl = "jdbc:sqlserver://localhost:1433;DatabaseName=model";
    private String userName = "sa";
    private String userPwd = "123456";

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
            throwables.printStackTrace();
            return "";
        }
    }

    public User getUser(String usn, String psw){
        try {
            String sql = "select * from [user] where username = '" + usn + "' and password = '" + psw + "';";
            rs = execSql(sql);
            rs.next();
            return new User(rs.getString(1),rs.getString(2),rs.getString(3),rs.getInt(4));
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
                    "where owneruserid = '" + userid + "' and isdelete = 0";

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
                    " where isdelete = 0 and boughtuserid is not null";

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

}
