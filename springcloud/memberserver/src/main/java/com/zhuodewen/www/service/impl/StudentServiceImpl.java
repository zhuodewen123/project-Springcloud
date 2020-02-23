package com.zhuodewen.www.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zhuodewen.www.domain.Student;
import com.zhuodewen.www.mapper.StudentMapper;
import com.zhuodewen.www.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * 商品服务的实现类
 */
@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    /**
     * 测试MybatisPlus查询全部
     * @return
     */
    public List<Student> selectAll() {
        return studentMapper.selectList(new EntityWrapper<Student>());
    }

    public Student selectOne(Student student) {
        return studentMapper.selectOne(student);
    }

    public void updateOne(Student student) {
        studentMapper.updateById(student);
    }

    public void deleteOne(Long id) {
        studentMapper.deleteById(id);
    }

    public void insertOne(Student student) {
        studentMapper.insert(student);
    }

}
