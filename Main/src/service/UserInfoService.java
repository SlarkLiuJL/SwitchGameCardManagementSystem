package service;


public interface UserInfoService {
    //登录
    Boolean Login(String username, String password);

    //获取密码
    String getPsw(String username);

    //注册
    Integer register(String username, String password);

    Boolean isUserExists(String username);

}
