package com.zhuodewen.www.service;

import com.zhuodewen.www.util.Assert;
import com.zhuodewen.www.util.DateUtil;
import com.zhuodewen.www.util.HttpClientUtil;
import com.zhuodewen.www.util.UserContext;
import com.zhuodewen.www.vo.VerifyCodeVO;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class WebServiceImpl implements WebService {

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
        System.out.println("验证码:"+code);
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

}
