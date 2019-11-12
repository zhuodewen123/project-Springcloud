package com.zhuodewen.www.vo;

import java.util.Date;

/**
 * 封装邮件验证码信息的类
 */
public class MailCodeVO {
    //验证码
    private String code;
    //接收人邮箱
    private String mail;
    //发送时间
    private Date sendTime;

    public MailCodeVO(){

    }

    public MailCodeVO(String code, String phoneNumber, Date sendTime){
        this.code=code;
        this.mail=phoneNumber;
        this.sendTime=sendTime;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPhoneNumber() {
        return mail;
    }

    public void setPhoneNumber(String mail) {
        this.mail = mail;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }


}
