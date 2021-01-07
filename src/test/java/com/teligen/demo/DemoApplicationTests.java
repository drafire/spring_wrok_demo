package com.teligen.demo;


import com.teligen.demo.enums.PeopleType;
import com.teligen.demo.enums.SexType;
import com.teligen.demo.utils.EnumUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.InvocationTargetException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public void testEnum() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        boolean res = EnumUtils.isInclude(PeopleType.class, 1);

        boolean res2 = EnumUtils.isInclude(PeopleType.class, 5);
        System.out.println("结果1=[" + res + "]");

        System.out.println("结果2=[" + res2 + "]");

        boolean res3 = EnumUtils.isInclude(SexType.class, "man");

        boolean res4 = EnumUtils.isInclude(SexType.class, "w");
        System.out.println("结果3=[" + res3 + "]");

        System.out.println("结果4=[" + res4 + "]");

        SexType man = (SexType) EnumUtils.getEnum(SexType.class, "man");
        System.out.println("测试...." + man.getMsg());

        SexType wman = (SexType) EnumUtils.getEnum(SexType.class, "wman");

    }
}
