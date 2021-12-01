package service;

import model.User;

import java.util.List;

public class UserInfoServiceImpl implements UserInfoService{

    //登录
    @Override
    public User Login(String username, String password, JdbcService jdbcService){
        String corectPsw = getPsw(username,jdbcService);
        if ((!"".equals(password)) && corectPsw.equals(password)) {
            return jdbcService.getUser(username,password);
        } else{
            return null;
        }
    }

    //获取密码
    @Override
    public String getPsw(String username, JdbcService jdbcService){

        if (username != null && (!"".equals(username))){
            String psw = jdbcService.getPsw(username);
            return psw;
        } else{
            return "";
        }
    }

    @Override
    public Integer register(String username, String password, JdbcService jdbcService) {
        if (isUserExists(username,jdbcService)) {
            return 0;
        } else {
            //存入数据库
            jdbcService.register(username,password);
            return 1;
        }
    }

    @Override
    public Boolean isUserExists(String username, JdbcService jdbcService) {
        return jdbcService.isExists(username);
    }

}
