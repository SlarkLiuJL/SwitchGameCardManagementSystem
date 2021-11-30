package service;


public interface UserInfoService {
    //登录
    public Boolean Login(String username, String password);

    //获取密码
    public String getPsw(String username);

    //注册
    public void register(String username, String password);


}
