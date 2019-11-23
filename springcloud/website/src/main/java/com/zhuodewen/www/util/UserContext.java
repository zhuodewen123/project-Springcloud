package com.zhuodewen.www.util;

import com.zhuodewen.www.vo.MailCodeVO;
import com.zhuodewen.www.vo.VerifyCodeVO;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * session的工具类
 */
public class UserContext {

    //定义session的常量
    private static final String  VERIFY_CODE_IN_SESSION= "verify_code_in_session";
    private static final String  MAIL_CODE_IN_SESSION= "mail_code_in_session";

    //1.获取session
    private static HttpSession getSession(){
        RequestAttributes attr = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servletRequestAttributes= (ServletRequestAttributes) attr;
        HttpServletRequest request = servletRequestAttributes.getRequest();
        HttpSession session = request.getSession();
        return session;
    }
    //2.将发送的验证码记录到session 中
    public static void  setVerifyCodeVO(VerifyCodeVO vo){
        getSession().setAttribute(VERIFY_CODE_IN_SESSION,vo);
    }

    //3.从session中取出验证码
    public static VerifyCodeVO getVerifyCodeVO(){
        return (VerifyCodeVO) getSession().getAttribute(VERIFY_CODE_IN_SESSION);
    }

    //4.将发送的邮件验证码记录到session 中
    public static void  setMailCodeVO(MailCodeVO vo){
        getSession().setAttribute(MAIL_CODE_IN_SESSION,vo);
    }

    //5.从session中取出邮件验证码
    public static MailCodeVO getMailCodeVO(){
        return (MailCodeVO) getSession().getAttribute(MAIL_CODE_IN_SESSION);
    }

}
