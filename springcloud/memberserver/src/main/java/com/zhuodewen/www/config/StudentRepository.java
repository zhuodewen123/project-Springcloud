package com.zhuodewen.www.config;

import com.zhuodewen.www.domain.Student;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StudentRepository extends ElasticsearchRepository<Student,Long> {

    //自定义查询方法1
    List<Student> findByAge(int age);

    //自定义查询方法2
    List<Student> findNameByAgeOrSex(int age,int sex);

    //自定义查询方法3
    List<Student> findByNameAndAddress(String name,String address);

    //自定义查询方法4
    List<Student> findByAgeBetween(int minAge,int maxAge);
}
