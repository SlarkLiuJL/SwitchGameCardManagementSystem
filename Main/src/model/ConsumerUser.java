package model;

import service.JdbcService;

import java.util.List;

public class ConsumerUser extends User {

    private String role = "买家";

    public ConsumerUser(String username, String password, String userid, Integer userType) {
        super(username, password, userid, userType);
    }

    public ConsumerUser (User user) {
        super.setUsername(user.getUsername());
        super.setUserid(user.getUserid());
        super.setPassword(user.getPassword());
        super.setUserType(user.getUserType());
    }

    @Override
    public String getRole() {
        return this.role;
    }

}
