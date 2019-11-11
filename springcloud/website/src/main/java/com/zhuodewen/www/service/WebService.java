package com.zhuodewen.www.service;

public interface WebService {

    /**
     * 发送短信接口
     * @param phoneNumber
     */
    public void sendVerifyCode(String phoneNumber);
}
