package com.zhuodewen.www.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.zhuodewen.www.domain.Goods;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodsMapper extends BaseMapper<Goods> {
    //int deleteByPrimaryKey(Integer id);

    /**
     * 自定义的SQL贴该注解与xml里面的进行对应
     * @param record
     * @return
     */
    /*@Select("insert")
    Integer insert(Goods record);*/

    //Goods selectByPrimaryKey(Integer id);

    //List<Goods> selectAll();

    //int updateByPrimaryKey(Goods record);
}