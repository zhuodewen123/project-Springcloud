package com.zhuodewen.www.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 商品类
 */
@ToString
@Getter
@Setter
@TableName("goods")                         //MybatisPlus
@Document(indexName = "shop",type="goods")  //ES
public class Goods implements Serializable{

    @TableId(value = "id", type = IdType.AUTO)  //MybatisPlus
    @Id                                         //ES
    private Integer id;

    //商品名称
    @Field  //ES
    private String good_name;

    //商品编码
    @Field  //ES
    private String good_code;

    //商品标题
    @Field  //ES
    private String title;

    //价格
    @Field  //ES
    private BigDecimal price;

    //折扣
    @Field  //ES
    private BigDecimal discount;

    //商品描述
    @Field  //ES
    private String contant;

    //商品链接
    @Field  //ES
    private String url;

    //图片地址
    @Field  //ES
    private String pic_url;

    //domain类中有但库中表没有的属性--Mybatis
    @TableField(exist = false)
    private String other;

}