package com.zhuodewen.www.controller;

import com.zhuodewen.www.domain.Goods;
import com.zhuodewen.www.service.GoodsService;
import com.zhuodewen.www.service.RabbitMqService;
import com.zhuodewen.www.util.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * 商品的controller
 */
@Controller
@RequestMapping("goods")
public class GoodController {

    @Autowired
    private GoodsService goodsService;
    @Autowired
    private RabbitMqService rabbitMqService;


    @RequestMapping(value = "selectById",method = RequestMethod.GET)
    @ResponseBody
    public Goods selectById(int id){
        return goodsService.selectById(id);
    }

    /**
     * 测试rabbitMq--消息发送者
     * @param id
     * @return
     */
    @RequestMapping(value = "selectByRabbitMq",method = RequestMethod.GET)
    @ResponseBody
    public String selectByRabbitMq(int id){
        Message build=MessageBuilder.withPayload(id).build();
        Boolean b=rabbitMqService.sendMessage().send(build);       //接口接收须是SubscribableChannel
        return b.toString();
    }

    /**
     * 文件下载(项目中的文件)
     * @param response
     * @param request
     * @throws FileNotFoundException
     */
    @GetMapping("fileDownload")
    @ResponseBody
    public JSONResult fileDownload(HttpServletResponse response, HttpServletRequest request) throws FileNotFoundException {

        JSONResult js=new JSONResult();

        // 文件的默认保存名
        String fileName = "_data_dse_files_sign_sign.zip";
        // 文件的存放路径
        File file = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX+"file/_data_dse_files_sign_sign.zip");

        // 读到流中
        InputStream inputStream = new FileInputStream(file);
        // 设置输出的格式
        response.reset();
        response.setContentType("bin");
        response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
        // 循环取出流中的数据
        byte[] b = new byte[100];
        int len;
        try {
            while ((len = inputStream.read(b)) > 0)
                response.getOutputStream().write(b, 0, len);
            js.setResult("SUCCESS");
        } catch (Exception e) {
            e.printStackTrace();
            js.setResult("FALSE");
        }finally{
            try {
                //关闭资源
                if(inputStream!=null){
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return  js;
    }

}
