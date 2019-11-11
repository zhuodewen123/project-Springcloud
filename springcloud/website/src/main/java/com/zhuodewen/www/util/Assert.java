package com.zhuodewen.www.util;

import org.springframework.util.StringUtils;

public class Assert {

    //限定手机号码的长度为11位
    public static final int PHONE_LENGTH=11;

    //断言不为空(不为null,且不为空字符串)
    public static void isNotNull(Object object,String message){
        if(object==null){
            throw new RuntimeException(message);
        }
        if(object instanceof String){
            String str= (String) object;
            if(!StringUtils.hasLength(str)){
                throw new RuntimeException(message);
            }
        }
    }

    //断言是否是手机号码长度
    public static void isPhoneLength(String username,String message){
        isNotNull(username,message);
        if(username.trim().length()!=PHONE_LENGTH){
            throw new RuntimeException(message);
        }
    }

    //断言是否为true(传入的expression为false就报错)
    public static void  isTrue(boolean expression,String message){
        if(!expression){
            throw new RuntimeException(message);
        }
    }

    //断言是否为false(传入的expression为true就报错)
    public static void  isFalse(boolean expression,String message){
       isTrue(!expression,message);
    }
}
