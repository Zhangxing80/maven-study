package org.example;

import com.google.gson.Gson;
import org.example.mapper.StudentMapper;
import org.example.pojo.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@SuppressWarnings("all")
public class Controller{
    @Autowired//这个注释能自动连接数据库和实体类，进行对应来进行操作
    StudentMapper studentMapper;
    //这里通过定义的studentMapper就能查到数据库了
    private Gson gson=new Gson();
    @GetMapping("/Zhangxing")//设置路由号，浏览器上输入这个路由就执行下面的函数
    public String hello(){
        List<Student> students= studentMapper.selectList(null);
          //（studentMapper.selectList(null)var主要是var让这句代码变）

        return gson.toJson(students);
    }
}
