package com.zhuodewen.www.junit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.stereotype.Repository;

@RunWith(JUnit4.class)
@Repository
public class TestGoods {

    @Test
    public void test(){
        System.out.println("还有@After,@Before等注解======================");
    }

}
