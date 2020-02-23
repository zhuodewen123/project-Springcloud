package com.zhuodewen.www.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.zhuodewen.www.domain.Goods;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodsMapper extends BaseMapper<Goods>{

    /**
     * 自定义的SQL贴该注解与xml里面的进行对应
     * @param record
     * @return
     */
    @Select("insert")
    Integer insert(Goods record);

    //Goods selectByPrimaryKey(Integer id);

    //List<Goods> selectAll();

    //int updateByPrimaryKey(Goods record);

    //int deleteByPrimaryKey(Integer id);
}