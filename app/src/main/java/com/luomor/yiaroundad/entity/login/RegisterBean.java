package com.luomor.yiaroundad.entity.login;

/**
 * Created by peterzhang on 10/09/2018.
 */

public class RegisterBean {
    private int code;
    private String msg;
    private LoginBean.ResultBean result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public LoginBean.ResultBean getResult() {
        return result;
    }

    public void setResult(LoginBean.ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        private Long user_id;
        private String user_name;

        public Long getUser_id() {
            return user_id;
        }

        public void setUser_id(Long user_id) {
            this.user_id = user_id;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }
    }
}
