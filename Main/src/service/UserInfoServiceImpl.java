package service;

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
    public void register(String username, String password) {

    }

}
