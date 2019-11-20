package com.zhuodewen.www.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 商品类
 */

@TableName("goods")
@Getter
@Setter
public class Goods implements Serializable{

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    //商品名称
    private String good_name;

    //商品编码
    private String good_code;

    //商品标题
    private String title;

    //价格
    private BigDecimal price;

    //折扣
    private BigDecimal discount;

    //商品描述
    private String contant;

    //商品链接
    private String url;

    //图片地址
    private String pic_url;

    //domain类中有但库中表没有的属性
    @TableField(exist = false)
    private String other;

}