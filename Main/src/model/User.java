package model;

public class User {

    private String username;

    private String password;

    private String userid;

    private Integer userType;

    private Integer isdelete;

    public String getRole() {
        return "用户";
    }

    public User() {

    }

    public User(String username, String password, String userid, Integer userType) {
        this.username = username;
        this.password = password;
        this.userid = userid;
        this.userType = userType;
    }

    public User(String username, String password, String userid, Integer userType,Integer isdelete) {
        this.username = username;
        this.password = password;
        this.userid = userid;
        this.userType = userType;
        this.isdelete = isdelete;
    }

    public Integer getIsdelete() {
        return isdelete;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }
}
