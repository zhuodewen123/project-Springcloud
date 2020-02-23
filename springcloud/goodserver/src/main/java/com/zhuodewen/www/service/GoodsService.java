package com.zhuodewen.www.service;

import com.zhuodewen.www.domain.Goods;

public interface GoodsService {

    Goods selectById(int id);

    Goods selectById2(int id);
}
