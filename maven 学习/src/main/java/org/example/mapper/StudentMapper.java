package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.example.pojo.Student;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentMapper extends BaseMapper<Student> {//这是定义对应的“映射接口”，而且必须要继承BaseMapper,<>内的泛型类传的是对应实体类

}
