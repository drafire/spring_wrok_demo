package com.teligen.demo;


import com.teligen.demo.enums.PeopleType;
import com.teligen.demo.enums.SexType;
import com.teligen.demo.model.People;
import com.teligen.demo.utils.EnumUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.KieBase;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Test
    public void contextLoads() {
//        String requestUrl="/WEB/tomcat/iac-tomcat-6.0.16/webapps/iac/uploadfile/support9/case_sync_temp/2021/1/28/thumbnail_B9F0BEB7A5A15A38E0536A01A8C04F38.jpg";
//        int i = requestUrl.lastIndexOf(".");
//
//
//        String substring = requestUrl.substring(0, i-1);
//        String[] split = requestUrl.split("/");
//        String name = split[split.length-1];
//        System.out.println(name);


        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        //【南海公安】在#year#年#month#月#day#日#hour#时#month#分，xxx案件编号指派了一个新任务给你。
        final String tpl_value = "#year#=" + cal.get(cal.YEAR) + "&#month#=" + (cal.get(cal.MONTH) + 1)
                + "&#day#=" + cal.get(cal.DAY_OF_MONTH) + "&#hour#=" + cal.get(cal.HOUR) + "&#minute#=" + cal.get(cal.MINUTE)
                + "&#jjdbh#=";
    }

    int ctlOf(int rs, int wc) {
        return rs | wc;
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

    @Autowired
    private KieSession session;

    @Autowired
    private KieBase kieBase;

    @Test
    public void testDrl() {
        People people = new People("张三", 10, 1);
        People people2 = new People("和消化", 30, 2);

        session.insert(people);   //插入
        session.insert(people2);   //插入
        session.fireAllRules();   //执行规则

        session.dispose();//释放资源
    }


}
