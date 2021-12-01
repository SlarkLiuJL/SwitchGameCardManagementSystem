package model;

import service.JdbcService;

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

    public boolean deleteGameCard(GameCard gameCard){
        String id = gameCard.getCardId();
        //下架卡带

        return true;
    }

}
