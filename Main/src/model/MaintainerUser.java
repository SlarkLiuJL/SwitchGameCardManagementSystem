package model;

import service.JdbcService;

import java.sql.SQLException;
import java.util.List;

public class MaintainerUser extends User {

    private JdbcService jdbcService;

    private String role = "管理员";


    public MaintainerUser(String username, String password, String userid, Integer userType, JdbcService jdbcService) {
        super(username, password, userid, userType);
        this.jdbcService = jdbcService;
    }

    public MaintainerUser(User user, JdbcService jdbcService) {
        super.setUsername(user.getUsername());
        super.setUserid(user.getUserid());
        super.setPassword(user.getPassword());
        super.setUserType(user.getUserType());
        this.jdbcService = jdbcService;
    }

    public void setJdbcService(JdbcService jdbcService) {
        this.jdbcService = jdbcService;
    }

    public MaintainerUser(String username, String password, String userid, Integer userType) {
        super(username, password, userid, userType);
    }


    public String deleteGoods(String id){
        //下架商品
        String sql = "update [card] set isdelete = 1 where cardid = '" + id + "'";
        try {
            jdbcService.execSql(sql,1);
            return "已强制下架";
        } catch (SQLException throwables) {
//            throwables.printStackTrace();
            return "强制下架失败";
        }
    }

    public String deleteUser(String id){
        //删除用户
        String sql = "update [user] set isdelete = 2 where userid = '" + id + "'";
        try {
            jdbcService.execSql(sql,1);
            return "已冻结账号";
        } catch (SQLException throwables) {
//            throwables.printStackTrace();
            return "账号冻结失败";
        }
    }

}
