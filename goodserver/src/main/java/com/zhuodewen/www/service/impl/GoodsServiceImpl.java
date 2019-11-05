package com.zhuodewen.www.service.impl;

import com.zhuodewen.www.domain.Goods;
import com.zhuodewen.www.mapper.GoodsMapper;
import com.zhuodewen.www.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 商品服务的实现类
 */
@Service
@Transactional
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private RedisTemplate redisTemplate;
    //private StringRedisTemplate redisTemplate;

    //@HystrixCommand(fallbackMethod = "fault")       //熔断器测试(方式一:在"提供者"具体的业务方法上,使用@HystrixCommand(fallbackMethod = "xxx")注解进行熔断)
    public Goods selectById(int  id) {
        //模拟异常--模拟调用接口失败
        //int b=1/0;
        Goods good=goodsMapper.selectByPrimaryKey(id);

        //redis缓存String
        ValueOperations val =redisTemplate.opsForValue();
        //测试redis--String--1:设置属性,指
        val.set("name",good.getGoodName());
        //测试redis--String--2:设置属性,指,超时时间
        val.set("testTimeOut","测试2分钟后自动删除",2, TimeUnit.MINUTES);

        //redis缓存list
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        //redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());

        ListOperations listOperations=redisTemplate.opsForList();

        List list=new ArrayList<>();
        for (int i=0;i<=9;i++){
            list.add(Integer.toString(i));
        }
        //测试redis--list:存入list
        listOperations.rightPushAll("list",list);

        return good;
    }

    /**
     * 熔断器--测试调用失败后的备选方法
     * @param id
     * @return
     */
    public Goods fault(int id){
        Goods good=new Goods();
        good.setGoodName("使用@HystrixCommand熔断");
        return good;
    }


}
