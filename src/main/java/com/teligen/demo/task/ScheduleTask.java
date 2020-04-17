package com.teligen.demo.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class ScheduleTask {
    private static Logger logger = LoggerFactory.getLogger(ScheduleTask.class);


    //3.添加定时任务
    @Scheduled(cron = "0 0 8 * * ?")
    //或直接指定时间间隔，例如：5秒
//        @Scheduled(fixedRate=3600000)
    private void runPushTasks() {
//            logger.info("执行定时任务时间开始");
//            riskCorpCheckService.runPushTasks();
//            logger.info("执行定时任务时间结束");
    }
}
