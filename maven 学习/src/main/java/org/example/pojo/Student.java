package org.example.pojo;

import com.sun.xml.internal.ws.api.ha.StickyFeature;
import lombok.Data;

@Data
public class Student {//创建的实体类，根据数据库内数据的结构来写
    private int id;
    private String number;
    private String name;
    private int age;
    private int chi;
    private int math;
}
