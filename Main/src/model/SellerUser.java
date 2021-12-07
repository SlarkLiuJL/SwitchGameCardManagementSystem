package model;

import service.JdbcService;

import java.util.List;

public class SellerUser extends User{

    private String role = "卖家";

    private JdbcService jdbcService;

    private List<GoodInfo> myGoodsList = null;

    public SellerUser(User user) {
        super.setUsername(user.getUsername());
        super.setUserid(user.getUserid());
        super.setPassword(user.getPassword());
        super.setUserType(user.getUserType());
    }

}
