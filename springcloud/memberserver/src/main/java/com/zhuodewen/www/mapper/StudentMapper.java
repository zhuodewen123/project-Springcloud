package com.zhuodewen.www.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.zhuodewen.www.domain.Student;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentMapper extends BaseMapper<Student> {

    /**
     * 自定义的SQL贴该注解与xml里面的进行对应
     * @param record
     * @return
     */
    /*@Select("insert")
    Integer insert(Student record);*/

    //Student selectByPrimaryKey(Long id);

    //List<Student> selectAll();

    //int updateByPrimaryKey(Student record);

    //int deleteByPrimaryKey(Long id);
}
