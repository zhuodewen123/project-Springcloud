package com.zhuodewen.www.controller;

import com.zhuodewen.www.service.WebSiteService;
import com.zhuodewen.www.util.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Api(value="web模块查看接口",tags={"webAPi"})
@Controller
@RequestMapping("/web")
public class WebController {

    @Autowired
    private WebSiteService webService;

    /*@Autowired
    private RestTemplate restTemplate;*/

    /*@RequestMapping(value = "selectAll",method = RequestMethod.GET)
    public List<Goods> selectAll(){

        return restTemplate.getForObject("http://GOODS-SERVICE/selectAll",Goods.class);
    }*/

    /**
     * 登录页面
     * @return
     */
    @ApiOperation("进入登录页面(测试)")
    @RequestMapping(value="/loginPage",method = RequestMethod.GET)
    public String loginPage(){
        return "loginPage";
    }

    /**
     * 进入学生页面(测试)
     */
    @ApiOperation("进入学生页面(测试)")
    @RequestMapping(value="/index",method = RequestMethod.GET)
    public String index(){
        return "index";
    }

    /**
     * 进入注册页面(测试)
     */
    @ApiOperation("进入注册页面(测试)")
    @RequestMapping(value="/register",method = RequestMethod.GET)
    public String register(){
        return "register";
    }

    /**
     * 发送短信
     */
    @ApiOperation("发送短信")
    @PostMapping("/sendVerifyCode")
    @ResponseBody
    public JSONResult sendVerifyCode(@ApiParam(name="phoneNumber",value="对方手机号码",required=true)String  phoneNumber){
        JSONResult js=new JSONResult();
        try{
            webService.sendVerifyCode(phoneNumber);
            js.mark("发送成功");
        }catch(Exception e){
            e.printStackTrace();
            js.mark("发送失败");
        }
        return js;
    }

    /**
     * 发送简单邮件(163/QQ)
     * @return
     */
    @ApiOperation("发送简单邮件(163/QQ)")
    @PostMapping("/sendMailCode")
    @ResponseBody
    public JSONResult sendSimpleMail(@ApiParam(name="to",value="对方邮箱",required=true)String to) {
        JSONResult js=new JSONResult();
        try{
            webService.sendMail(to);
            js.mark("发送成功");
            js.setResult("SUCCESS");
        }catch(Exception e){
            e.printStackTrace();
            js.mark("发送失败");
            js.setResult("FALSE");
        }
        return js;
    }

}
