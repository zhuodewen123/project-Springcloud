package com.zhuodewen.www.junit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.stereotype.Repository;

@RunWith(JUnit4.class)
@Repository
public class TestGoods {

    @Before
    public void before(){
        System.out.println("before======================");
    }

    @Test
    public void test(){
        System.out.println("test========================");
    }

    @After
    public void after(){
        System.out.println("after======================");
    }

}
