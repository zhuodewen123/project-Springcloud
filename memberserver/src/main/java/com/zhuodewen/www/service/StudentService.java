package com.zhuodewen.www.service;

import com.zhuodewen.www.domain.Student;
import java.util.List;

public interface StudentService {

    //Goods selectById(int id);

    /**
     * 测试MybatisPlus
     * @return
     */
    List<Student> selectAll();
    Student selectOne(Student student);
    void updateOne(Student student);
    void deleteOne(Long id);
    void insertOne(Student student);

}
