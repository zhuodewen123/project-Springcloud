package com.zhuodewen.www.controller;

import com.zhuodewen.www.service.WebService;
import com.zhuodewen.www.util.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/web")
public class WebController {

    @Autowired
    private WebService webService;

    /*@Autowired
    private RestTemplate restTemplate;*/

    /*@RequestMapping(value = "selectAll",method = RequestMethod.GET)
    public List<Goods> selectAll(){

        return restTemplate.getForObject("http://GOODS-SERVICE/selectAll",Goods.class);
    }*/

    /**
     * 进入学生页面(测试)
     */
    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    /**
     * 进入短信注册页面(测试)
     */
    @RequestMapping("/register")
    public String register(){
        return "register";
    }

    /**
     * 发送短信
     */
    @PostMapping("/sendVerifyCode")
    @ResponseBody
    public JSONResult sendVerifyCode(String  phoneNumber){
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

}
