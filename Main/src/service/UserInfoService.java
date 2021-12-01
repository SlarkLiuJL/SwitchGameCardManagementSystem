package service;


import model.User;

public interface UserInfoService {
    //登录
    User Login(String username, String password, JdbcService jdbcService);

    //获取密码
    String getPsw(String username, JdbcService jdbcService);

    //注册
    Integer register(String username, String password, JdbcService jdbcService);

    Boolean isUserExists(String username, JdbcService jdbcService);

}
