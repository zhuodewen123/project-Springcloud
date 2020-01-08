package com.zhuodewen.www.controller;

import com.zhuodewen.www.domain.Goods;
import com.zhuodewen.www.service.GoodsService;
import com.zhuodewen.www.util.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 会员的controller
 */
@Controller
@RequestMapping("member")
public class GoodController {

    @Autowired
    private GoodsService goodsService;

    /**
     * 测试MybatisPlus
     * @return
     */
    @RequestMapping(value = "selectAll",method = RequestMethod.GET)
    @ResponseBody
    public List<Goods> selectAll(){
        return goodsService.selectAll();
    }

    @RequestMapping(value = "selectOne",method = RequestMethod.GET)
    @ResponseBody
    public Goods selectOne(Goods goods){
        return goodsService.selectOne(goods);
    }

    @RequestMapping(value = "updateOne",method = RequestMethod.GET)
    @ResponseBody
    public JSONResult updateOne(Goods goods){
        JSONResult js=new JSONResult();
        try{
            goodsService.updateOne(goods);
            js.mark("SUCCESS");
            js.setResult("更新成功");

        }catch(Exception e){
            e.printStackTrace();
            js.mark("FALSE");
            js.setResult("更新失败");

        }
        return js;
    }

    @RequestMapping(value = "deleteOne",method = RequestMethod.GET)
    @ResponseBody
    public JSONResult deleteOne(Long id){
        JSONResult js=new JSONResult();
        try{
            goodsService.deleteOne(id);
            js.mark("SUCCESS");
            js.setResult("删除成功");
        }catch(Exception e){
            e.printStackTrace();
            js.mark("FALSE");
            js.setResult("删除失败");
        }
        return js;
    }

    @RequestMapping(value = "insertOne",method = RequestMethod.GET)
    @ResponseBody
    public JSONResult insertOne(Goods goods){
        JSONResult js=new JSONResult();
        try{
            goodsService.insertOne(goods);
            js.mark("SUCCESS");
            js.setResult("新增成功");
        }catch(Exception e){
            e.printStackTrace();
            js.mark("FALSE");
            js.setResult("新增失败");
        }
        return js;
    }

}
