package com.zhuodewen.www.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhuodewen.www.domain.Goods;
import com.zhuodewen.www.mapper.GoodsMapper;
import com.zhuodewen.www.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商品服务的实现类
 */
@Service
@Transactional
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

   /* @HystrixCommand(fallbackMethod = "fault")       //熔断器测试(方式一:在"提供者"具体的业务方法上,使用@HystrixCommand(fallbackMethod = "xxx")注解进行熔断)
    public Goods selectById(int  id) {
        Goods goods=new Goods();
        goods.setGoodName("gateway网关--有真实接口的路径不会根据gateway进行转发");
        return goods;
    }*/

    /**
     * 熔断器--测试调用失败后的备选方法
     * @param id
     * @return
     */
    /*public Goods fault(int id){
        Goods good=new Goods();
        good.setGoodName("使用@HystrixCommand熔断");
        return good;
    }*/

    /**
     * 测试MybatisPlus查询全部
     * @return
     */
    public List<Goods> selectAll() {
        return goodsMapper.selectList(new EntityWrapper<Goods>());
    }

    public Goods selectOne(Goods goods) {
        return goodsMapper.selectOne(goods);
    }

    public void updateOne(Goods goods) {
        goodsMapper.updateById(goods);
    }

    public void deleteOne(Long id) {
        goodsMapper.deleteById(id);
    }

    public void insertOne(Goods goods) {
        goodsMapper.insert(goods);
    }

    /**
     * 测试MybatisPlus分页查询
     * @return
     */
    /*public List<Goods> selectAllPage() {
        return goodsMapper.selectList(new EntityWrapper<Goods>());
    }*/
}
