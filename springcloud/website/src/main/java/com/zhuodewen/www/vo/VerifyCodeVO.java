package com.zhuodewen.www.vo;

import java.util.Date;

/**
 * 封装短信验证码信息的类
 */
public class VerifyCodeVO {
    //验证码
    private String code;
    //用户名(电话号码封装)
    private String phoneNumber;
    //发送时间
    private Date sendTime;

    public VerifyCodeVO(){

    }

    public VerifyCodeVO(String code,String phoneNumber,Date sendTime){
        this.code=code;
        this.phoneNumber=phoneNumber;
        this.sendTime=sendTime;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }


}
