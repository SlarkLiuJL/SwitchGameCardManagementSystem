package service;

import java.util.List;

public class UserInfoServiceImpl implements UserInfoService{

    //登录
    @Override
    public Boolean Login(String username, String password){
        String corrcetUsrName = "";
        String corectPsw = getPsw(username);
        if ("".equals(corectPsw)) {
            return true;
        } else{
            return false;
        }
    }

    //获取密码
    @Override
    public String getPsw(String username){

        if (username != null && (!"".equals(username))){
            String psw = "";
            return psw;
        } else{
            return "";
        }
    }

    @Override
    public Integer register(String username, String password) {
        if (isUserExists(username)) {
            return 0;
        } else {
            //存入数据库
            return 1;
        }
    }

    @Override
    public Boolean isUserExists(String username) {
        List<String> users = null;
        if (users.contains(username)){
            return false;
        } else {
            return true;
        }

    }

}
