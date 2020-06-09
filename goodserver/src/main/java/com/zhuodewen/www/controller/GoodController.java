package com.zhuodewen.www.controller;

import com.zhuodewen.www.domain.Goods;
import com.zhuodewen.www.service.GoodsService;
import com.zhuodewen.www.service.RabbitMqService;
import com.zhuodewen.www.util.JSONResult;
import com.zhuodewen.www.util.RedisLockUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品的controller
 */
@Controller
@RequestMapping("goods")
public class GoodController {

    @Autowired
    private GoodsService    goodsService;
    @Autowired
    private RabbitMqService rabbitMqService;
    @Autowired
    private RedisTemplate   redisTemplate;
    @Autowired
    private RedisLockUtil redisLockUtil;

    @RequestMapping(value = "inertIntoRedis",method = RequestMethod.GET)
    @ResponseBody
    public JSONResult inertIntoRedis(){
        JSONResult js=new JSONResult();
        List<Map<String,Object>> list=new ArrayList<>();
        try {
            //删除已有的
            redisTemplate.opsForHash().delete("user:id:1","id");
            redisTemplate.opsForHash().delete("user:id:1","name");
            redisTemplate.opsForHash().delete("user:id:1","age");
            redisTemplate.opsForHash().delete("user:id:1","tel");
            redisTemplate.opsForHash().delete("user:id:1","address");
            //新增
            Map<String,Object> map=new HashMap<>();
            map.put("id","1");
            map.put("name","卓德文");
            map.put("age","27");
            map.put("tel","13422075804");
            map.put("address","广州");
            //加分布式锁--能加锁则表示不被占用,不能则表示已被占用
            boolean isLock=redisLockUtil.getLock("user:set:lock",60);
            if(isLock){
                redisTemplate.opsForHash().putAll("user:id:1",map);
                redisLockUtil.releaseLock("user:set:lock");
            }

            Cursor<Map.Entry<Object,Object>> cursor =redisTemplate.opsForHash().scan(
                    "user:id:1", ScanOptions.scanOptions().match("*").count(10000).build());
            while (cursor.hasNext()) {
                Map.Entry<Object,Object> temp=cursor.next();
                Object key = temp.getKey();
                Object valueSet = temp.getValue();
                System.out.println("==========="+key+"==========="+valueSet+"===========");

                Map<String,Object> tempMap=new HashMap<>();
                tempMap.put(key.toString(),valueSet);
                list.add(tempMap);
                js.setResult(list);
                js.setMsg("success");
            }
            //关闭cursor
            cursor.close();
        } catch (IOException e) {
            e.printStackTrace();
            js.setMsg("fail");
        }
        return js;
    }

    @RequestMapping(value = "inertIntoRedis2",method = RequestMethod.GET)
    @ResponseBody
    public JSONResult inertIntoRedis2(){
        JSONResult js=new JSONResult();
        try {
            ListOperations ops=redisTemplate.opsForList();
            //删除原有的
            ops.remove("resource:type:ontime:dataList",0,"1");
            ops.remove("resource:type:ontime:dataList",0,"12");
            ops.remove("resource:type:ontime:dataList",0,"123");
            ops.remove("resource:type:ontime:dataList",0,"1234");
            ops.remove("resource:type:ontime:dataList",0,"12345");
            //新增(批量)
            List<String> list =new ArrayList<>();
            list.add("1");
            list.add("12");
            list.add("123");
            list.add("1234");
            list.add("12345");
            ops.rightPushAll("resource:type:ontime:dataList",list);
            //迭代redis的list类型
            List<String> list2=ops.range("resource:type:ontime:dataList",0,ops.size("resource:type:ontime:dataList"));
            System.out.println(list2);
            js.setMsg("success");
            js.setResult(list2);
        } catch (Exception e) {
            e.printStackTrace();
            js.setMsg("fail");
        }
        return js;
    }

    /**
     * 根据id查询商品对象
     */
    @RequestMapping(value = "selectById",method = RequestMethod.GET)
    @ResponseBody
    public Goods selectById(int id){
        return goodsService.selectById(id);
    }

    /**
     * 测试Async异步调用(贴了@Async的方法不能再当前类中)
     * @param id
     * @return
     */
    @RequestMapping(value = "selectById2",method = RequestMethod.GET)
    @ResponseBody
    public String selectById2(int id){
        goodsService.selectById2(id);
        return "测试Async--OK";
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
