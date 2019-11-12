package com.zhuodewen.www.service.impl;

import com.zhuodewen.www.util.Mail163Service;
import com.zhuodewen.www.util.MailQqService;
import com.zhuodewen.www.service.WebSiteService;
import com.zhuodewen.www.util.Assert;
import com.zhuodewen.www.util.DateUtil;
import com.zhuodewen.www.util.HttpClientUtil;
import com.zhuodewen.www.util.UserContext;
import com.zhuodewen.www.vo.MailCodeVO;
import com.zhuodewen.www.vo.VerifyCodeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.Date;
import java.util.UUID;

@Service
public class WebSiteServiceImpl implements WebSiteService {

    @Autowired
    private Mail163Service mail163Service;

    @Autowired
    private MailQqService mailQqService;

    public void sendVerifyCode(String phoneNumber) {
        //1.判断手机号码
        Assert.isNotNull(phoneNumber,"无效的手机号码");

        //2.判断验证码是否发送频繁(60秒内重复获取)
        VerifyCodeVO lastVo=UserContext.getVerifyCodeVO();//获取session中的验证码对象
        Date now=new Date();//获取当前时间

        if(lastVo!=null){
            Date lastVoSendTime = lastVo.getSendTime();//获取验证码的发送时间

            //调用断言类,判断发送时间至今是否已经60秒
            Assert.isTrue(!(DateUtil.getSecondsBetween(now, lastVoSendTime)<60),"发送频繁,请稍后再试");
        }

        //3.创建随机4位的验证码
        String code = UUID.randomUUID().toString().substring(0, 4);

        //4.执行发送操作
        System.out.println("短信验证码:"+code);
        send(phoneNumber,code);//调用发送短信验证码的真正方法

        //5.将发送的验证码记录到session中:字段有--code(验证码),phoneNumber(手机号码),sendTime(发送时间)
        VerifyCodeVO vo=new VerifyCodeVO(code,phoneNumber,now);//封装验证码相关信息

        UserContext.setVerifyCodeVO(vo);//将当前的验证码对象存入session中
    }

    /**
     * 发送短信验证码
     * @param phoneNumber
     * @param code
     */
    private void send(String phoneNumber,String code){
        String url="https://way.jd.com/chuangxin/VerCodesms?mobile="+phoneNumber+"&content=【广州德文信息科技股份有限公司】您的验证码是："+code+"，3分钟内有效,请及时使用！&appkey=13318f00a48229b658a857e1ab70ebde";  //京东万象

            /*Map<String,String> map=new HashMap<>();
            map.put("mobile",phoneNumber);
            map.put("content","【卓德文股份有限公司】您的验证码为:"+code);
            map.put("appkey","13318f00a48229b658a857e1ab70ebde");*/

        try {
            //调用短信接口:
            //String result = HttpClientUtil.doGet(url, map);
            String result = HttpClientUtil.doGet(url);
            System.out.println(result);

            Assert.isNotNull(result,"验证码发送失败,请联系客服处理[null]");

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("验证码发送失败,请联系客服处理[exception]");
        }
    }

    /**
     * 发送简单邮件(163/QQ)
     * @param to
     */
    @Override
    public void sendMail(String to) throws MessagingException {

        Date now=new Date();
        //判断验证码是否发送频繁(60秒内重复获取)
        MailCodeVO lastVo=UserContext.getMailCodeVO();                                  //获取session中的邮件验证码对象
        if(lastVo!=null){
            Date lastVoSendTime = lastVo.getSendTime();                                 //获取邮件验证码的发送时间
            //调用断言类,判断发送时间至今是否已经60秒
            Assert.isTrue(!(DateUtil.getSecondsBetween(now, lastVoSendTime)<60),"发送频繁,请稍后再试");
        }

        String code= UUID.randomUUID().toString().substring(0, 4);                       //验证码
        String subject="验证码(广州德文信息科技股份有限公司)";                               //邮件标题
        String content="尊敬的客户,您好!您本次的验证码是:"+code+",有效期为5分钟,请尽快验证!";  //邮件内容
        System.out.println("邮箱验证码:"+code);

        String str=to.split("\\.")[0].split("@")[1];                        //截取邮箱地址,判断是163邮箱还是qq邮箱("."前需要加\\进行转义)

        if(str==null && str.trim().equals("")){
            return ;
        }
        if(str.trim().equals("163")){
            //发送163邮件
            mail163Service.sendSimpleMail(to, subject, content);
        }else if(str.trim().equals("qq")){
            //发送qq邮件
            mailQqService.sendQqMail(to,subject, content);
        }else{
            throw new RuntimeException("发送失败,找不到该163邮箱/qq邮箱!");
        }
        MailCodeVO vo=new MailCodeVO(code,to,now);                                      //封装邮件验证码等相关信息
        UserContext.setMailCodeVO(vo);                                                  //将当前的邮件验证码对象存入session中
    }

}
