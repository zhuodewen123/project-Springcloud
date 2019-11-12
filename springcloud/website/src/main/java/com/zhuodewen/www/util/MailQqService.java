package com.zhuodewen.www.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * qq邮件工具类(第二种发送方式:使用代码进行配置并发送)
 */
@Service
public class MailQqService {

    //初始化QQ邮件的发送者
    @Value("${qq.mail.username}")
    private String qqFrom;

    //初始化QQ邮件的授权码
    @Value("${qq.mail.code}")
    private String qqCode;

    /**
     * 发送QQ邮件
     * @param to
     * @param subject
     * @param content
     */
    public void sendQqMail(String to,String subject,String content) throws MessagingException {
        //构造SMTP邮件服务器的基本环境
        Properties properties = new Properties();
        properties.setProperty("mail.host", "smtp.qq.com");
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.setProperty("mail.smtp.port", "465");
        Session session = Session.getDefaultInstance(properties);
        session.setDebug(true);

        //构造邮件
        MimeMessage mimeMessage = new MimeMessage(session);
        mimeMessage.addRecipients(Message.RecipientType.TO, to);                   //收件人
        //mimeMessage.addRecipients(Message.RecipientType.CC, "222@qq.com");       //抄送
        mimeMessage.setFrom(qqFrom);                                               //邮件发送人
        mimeMessage.setSubject(subject);                                           //邮件主题
        mimeMessage.setContent(content, "text/html;charset=utf-8");           //邮件正文

        //发送邮件
        Transport transport = null;
        transport = session.getTransport();
        transport.connect(properties.getProperty("mail.host"), qqFrom, qqCode);
        transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());        //发送邮件，第二个参数为收件人
        transport.close();
    }
}
