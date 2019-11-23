package com.zhuodewen.www.controller;

import com.zhuodewen.www.config.StudentRepository;
import com.zhuodewen.www.domain.Student;
import com.zhuodewen.www.service.StudentService;
import com.zhuodewen.www.util.JSONResult;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * 会员的controller
 */
@Controller
@RequestMapping("student")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentService studentService;

    /*@RequestMapping(value = "selectById",method = RequestMethod.GET)
    @ResponseBody
    public Goods selectById(int id){
        return goodsService.selectById(id);
    }*/

    @RequestMapping(value = "index",method = RequestMethod.GET)
    public String index(){
        return "index";
    }

    /**
     * 新增/修改一个学生(已存在则更新)--测试ES
     * @return
     */
    @RequestMapping(value="/insertOrUpdate",method = RequestMethod.GET)
    @ResponseBody
    public JSONResult insertOrUpdate(Student student){
        JSONResult js=new JSONResult();
        try{
            if(student.getId()==null){
                studentService.insertOne(student);
                js.mark("新增成功");
            }else{
                studentService.updateOne(student);
                js.mark("更新成功");
            }
            studentRepository.save(student);
        }catch(Exception e){
            e.printStackTrace();
            if(student.getId()==null){
                js.mark("新增失败");
            }else{
                js.mark("更新失败");
            }
        }
        return js;
    }

    /**
     * 根据id删除学生--测试ES
     * @param id
     * @return
     */
    @RequestMapping(value="/deleteById",method = RequestMethod.GET)
    @ResponseBody
    public JSONResult deleteById(Long id){
        JSONResult js=new JSONResult();
        try{
            studentRepository.deleteById(id);
            js.mark("删除成功");
        }catch(Exception e){
            e.printStackTrace();
            js.mark("删除失败");
        }
        return js;
    }

    /**
     * 根据条件删除学生--测试ES(也是根据id删除?)
     * @return
     */
    @RequestMapping(value="/delete",method = RequestMethod.GET)
    @ResponseBody
    public JSONResult delete(Student student){
        JSONResult js=new JSONResult();
        try{
            studentRepository.delete(student);
            js.mark("删除成功");
        }catch(Exception e){
            e.printStackTrace();
            js.mark("删除失败");
        }
        return js;
    }

    /**
     * 根据id查找学生--测试ES
     * @return
     */
    @RequestMapping(value="/findById",method = RequestMethod.GET)
    @ResponseBody
    public JSONResult findById(Long id){
        JSONResult js=new JSONResult();
        Student student= new Student();
        try{
            Optional<Student> optionalGGoods=studentRepository.findById(id);
            //判断是都有结果,有结果使用get()获取Optional内的结果
            if(optionalGGoods.isPresent()){
                student=optionalGGoods.get();
                js.mark("查询成功");
                js.setResult(student);
            }else{
                js.mark("查询成功,不存在对应的数据");
            }
        }catch(Exception e){
            e.printStackTrace();
            js.mark("查询失败");
        }
        return js;
    }


    /**
     * 根据实体所有属性查找学生--测试ES
     * q    查询条件
     * @return
     */
    @RequestMapping(value="/search/{q}",method = RequestMethod.GET)
    @ResponseBody
    public JSONResult search(@PathVariable String q){
        JSONResult js=new JSONResult();
        try{
            QueryStringQueryBuilder builder = new QueryStringQueryBuilder(q);
            Iterable<Student> searchResult = studentRepository.search(builder);
            Iterator<Student> iterator = searchResult.iterator();
            List<Student> list = new ArrayList<Student>();
            while (iterator.hasNext()) {
                list.add(iterator.next());
            }
            js.mark("查询成功");
            js.setResult(list);
        }catch(Exception e){
            e.printStackTrace();
            js.mark("查询失败");
        }
        return js;
    }

    /**
     * 自定义查询方法--测试ES
     * @return
     */
    @RequestMapping(value="/findSomething",method = RequestMethod.GET)
    @ResponseBody
    //public JSONResult findSomething(String q){                    //测试自定义方法findByAge
    //public JSONResult findSomething(int age,int sex){             //测试自定义方法findNameByAgeOrSex
    //public JSONResult findSomething(String name,String address){  //测试自定义方法findByNameAndAddress
    public JSONResult findSomething(int minAge,int maxAge){         //测试自定义方法findByAgeBetween
        JSONResult js=new JSONResult();
        try{
            //List<Student> list=studentRepository.findByAge(q);
            //List<Student> list=studentRepository.findNameByAgeOrSex(age,sex);
            //List<Student> list=studentRepository.findByNameAndAddress(name,address);
            List<Student> list=studentRepository.findByAgeBetween(minAge,maxAge);
            js.mark("查询成功");
            js.setResult(list);
        }catch(Exception e){
            e.printStackTrace();
            js.mark("查询失败");
        }
        return js;
    }

}
