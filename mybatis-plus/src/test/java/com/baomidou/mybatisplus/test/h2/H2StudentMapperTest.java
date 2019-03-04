package com.baomidou.mybatisplus.test.h2;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.test.h2.entity.enums.GenderEnum;
import com.baomidou.mybatisplus.test.h2.entity.enums.GradeEnum;
import com.baomidou.mybatisplus.test.h2.entity.mapper.H2StudentMapper;
import com.baomidou.mybatisplus.test.h2.entity.persistent.H2Student;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * Mybatis Plus H2 Junit Test
 *
 * @author hubin
 * @since 2018-06-05
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:h2/spring-test-h2.xml"})
class H2StudentMapperTest extends BaseTest {

    @Resource
    protected H2StudentMapper studentMapper;

    @Test
    @Order(1)
    void crudTest() {
//        H2Student stu = new H2Student();
//        stu.setGrade(GradeEnum.HIGH);
//        studentMapper.update(stu, null);
        H2Student student = new H2Student();
        Long id = 10086L;
        student.setId(id);
        student.setAge(188);
        student.setGender(GenderEnum.MALE);
        student.setGrade(GradeEnum.PRIMARY);
        studentMapper.insert(student);

        List<H2Student> list = studentMapper.selectList(new QueryWrapper<>());
        for (H2Student s : list) {
            System.out.println(s.getGrade());
            if (Objects.equals(s.getId(), id)) {
                Assert.notNull(s.getGrade(), "id=" + id + " should have grade");
                Assert.notNull(s.getGender(), "id=" + id + " should have gender");
            }
        }
        H2Student updateStu = new H2Student();
        updateStu.setId(15L);
        updateStu.setGrade(GradeEnum.HIGH);
        updateStu.setGender(GenderEnum.FEMALE);
        Assert.isTrue(studentMapper.updateById(updateStu) == 1, "should update success");
        updateStu = studentMapper.selectById(15L);
        Assert.notNull(updateStu.getGrade(), "grade should updated");
        Assert.notNull(updateStu.getGender(), "gender should updated");

    }

    @Test
    @Order(Integer.MAX_VALUE)
    void pageCountZeroTest() {
        IPage<H2Student> page = studentMapper.selectPage(new Page<>(), Wrappers.<H2Student>query().eq("name", "无"));
        if (null != page) {
            System.out.println("total: " + page.getTotal());
        }
    }
}