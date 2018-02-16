package com.farmer.async.spider;

import com.farmer.async.spider.util.context.ApplicationContextHolder;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(
        scanBasePackages = {
                "com.farmer.async.spider"})
@MapperScan("com.farmer.async.spider.save.mapper")
public class Application {


    public static void main(String[] args) {

        ApplicationContext applicationContext = SpringApplication.run(Application.class, args);

        ApplicationContextHolder.setApplicationContext(applicationContext);
    }
}
