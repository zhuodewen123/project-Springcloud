package com.zhuodewen.www.service;

import javax.mail.MessagingException;

public interface WebSiteService {

    /**
     * 发送短信接口
     * @param phoneNumber
     */
    public void sendVerifyCode(String phoneNumber);

    /**
     * 发送简单邮件(163/QQ)
     * @param to
     */
    public void sendMail(String to) throws MessagingException;
}
