package com.zhuodewen.www.service;

import com.zhuodewen.www.domain.Goods;
import java.util.List;

public interface GoodsService {

    //Goods selectById(int id);

    /**
     * 测试MybatisPlus
     * @return
     */
    List<Goods> selectAll();
    Goods selectOne(Goods goods);
    void updateOne(Goods goods);
    void deleteOne(Integer goods);
    void insertOne(Goods goods);

}
