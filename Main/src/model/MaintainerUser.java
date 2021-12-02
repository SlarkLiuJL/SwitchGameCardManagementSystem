package model;

import service.JdbcService;

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

    //仅有管理员有此权限
    public List<GameCard> showAllGameCard() {
        List<GameCard> list = null;
        String sql = "";

        return list;
    }

    public boolean deleteGameCard(String id){
        //下架卡带

        return true;
    }

    public boolean deleteUser(String id){
        //删除用户

        return true;
    }

}
