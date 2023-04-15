package cn.edu.cslg.cslglabmanager.controller.teacher;


import cn.edu.cslg.cslglabmanager.domain.BasicUser;
import cn.edu.cslg.cslglabmanager.utils.ResponseResult;
import database.domain.*;
import database.mapper.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class teacherBasicController {
    private final ProjectMapper projectMapper;

    private final ProjectProvinceMapper projectProvinceMapper;

    private final ProjectCountryMapper projectCountryMapper;

    private final ProjectSchoolMapper projectSchoolMapper;

    private final UserMapper userMapper;

    public teacherBasicController(ProjectMapper projectMapper, ProjectProvinceMapper projectProvinceMapper, ProjectCountryMapper projectCountryMapper, ProjectSchoolMapper projectSchoolMapper, UserMapper userMapper) {
        this.projectMapper = projectMapper;
        this.projectProvinceMapper = projectProvinceMapper;
        this.projectCountryMapper = projectCountryMapper;
        this.projectSchoolMapper = projectSchoolMapper;
        this.userMapper = userMapper;
    }


    @RequestMapping("/project/start")
    @PreAuthorize("hasAuthority('TEACHER')")
    public ResponseResult startProject(@RequestBody Project project) {
        // 新增校级项目
        ProjectSchool projectSchool = new ProjectSchool();
        projectSchool.setPid(project.getId());
        // 插入数据库
        projectSchoolMapper.insert(projectSchool);
        return new ResponseResult(200, "新增校级项目成功", null);
    }

    //    @RequestMapping("/project/then")
//    @PreAuthorize("hasAuthority('TEACHER')")
//    public ResponseResult thenProject(@RequestBody Project project){
//        //新增省级项目
//        ProjectProvince projectProvince=new ProjectProvince();
//        projectProvince.setPid(project.getId());
//        projectProvinceMapper.insert(projectProvince);
//        return new ResponseResult(200, "新增省级项目成功", null);
//    }
//    @RequestMapping("/project/end")
//    @PreAuthorize("hasAuthority('TEACHER')")
//    public ResponseResult endProject(@RequestBody Project project){
//        //新增国家级项目
//        ProjectCountry projectCountry=new ProjectCountry();
//        projectCountry.setPid(project.getId());
//        projectCountryMapper.insert(projectCountry);
//        return new ResponseResult(200, "新增国家级项目成功", null);
//    }
    //分别对各级项目删除操作
    @PostMapping("/project/delete/school")
    @PreAuthorize("hasAuthority('TEACHER')")
    public void deleteSchoolPro(@RequestBody Project project) {
        ProjectSchool projectSchool = new ProjectSchool();
        projectSchool.setPid(project.getId());
        projectSchoolMapper.deleteById(projectSchool);

    }

    @PostMapping("/project/delete/province")
    @PreAuthorize("hasAuthority('TEACHER')")
    public void deleteProvincePro(@RequestBody Project project) {
        projectProvinceMapper.deleteById(project.getId());
    }

    @PostMapping("/project/delete/country")
    @PreAuthorize("hasAuthority('TEACHER')")
    public void deleteCountryPro(@RequestBody Project project) {
        projectCountryMapper.deleteById(project.getId());
    }

    //分别对各级别项目进行更改
    @PostMapping("/project/update/school")
    @PreAuthorize("hasAuthority('TEACHER')")
    public void updateSchool(@RequestBody Project project) {
        ProjectSchool projectSchool = new ProjectSchool();
        projectSchool.setPid(project.getId());
        projectSchoolMapper.updateById(projectSchool);
    }

    @PostMapping("/project/update/province")
    @PreAuthorize("hasAuthority('TEACHER')")
    public void updateProvince(@RequestBody Project project) {
        ProjectProvince projectProvince = new ProjectProvince();
        projectProvince.setPid(project.getId());
        projectProvinceMapper.updateById(projectProvince);
    }

    @PostMapping("/project/update/country")
    @PreAuthorize("hasAuthority('TEACHER')")
    public void updateCountry(@RequestBody Project project) {
        ProjectCountry projectCountry = new ProjectCountry();
        projectCountry.setPid(project.getId());
        projectCountryMapper.updateById(projectCountry);
    }

    //获取各级项目
    @RequestMapping("/project/get")
    @PreAuthorize("hasAuthority('TEACHER')")
    public ResponseResult getProject() {
        // 获取所有项目
        List<Project> projects = projectMapper.selectList(null);
        // 获取所有校、省、国家级项目
        List<ProjectSchool> projectSchools = projectSchoolMapper.selectList(null);
        List<ProjectProvince> projectProvinces = projectProvinceMapper.selectList(null);
        List<ProjectCountry> projectCountries = projectCountryMapper.selectList(null);
        // 遍历所有项目，将校、省、国家级项目的id存入项目中
        for (Project project : projects) {
            // 根据项目的指导uid获取指导老师的姓名
            BasicUser basicUser = new BasicUser();
            basicUser.setId(project.getGuide());
            User user = new User();
            user.setId(project.getGuide());
            user = userMapper.selectById(user);
            basicUser.setName(user.getName());
            basicUser.setUid(user.getUid());
            project.setGuideUser(basicUser);
            // 获取项目的申报人信息
            BasicUser report = new BasicUser();
            report.setId(project.getUid());
            User userReport = new User();
            userReport.setId(project.getUid());
            userReport = userMapper.selectById(userReport);
            report.setName(userReport.getName());
            report.setUid(userReport.getUid());
            project.setUser(report);
            // 初始化状态
            project.setStatus(0);
            for (ProjectSchool projectSchool : projectSchools) {
                if (project.getId().equals(projectSchool.getPid())) {
                    project.setStatus(1);
                }
            }
            for (ProjectProvince projectProvince : projectProvinces) {
                if (project.getId().equals(projectProvince.getPid())) {
                    project.setStatus(2);
                }
            }
            for (ProjectCountry projectCountry : projectCountries) {
                if (project.getId().equals(projectCountry.getPid())) {
                    project.setStatus(3);
                }
            }
        }
        return new ResponseResult(200, "获取项目成功", projects);
    }


}
