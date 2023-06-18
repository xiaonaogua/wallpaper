package com.huoji.bing.wallpaper;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;
import java.net.InetAddress;

/**
 * Spring Boot Application 启 动 类
 */
@Slf4j
@EnableScheduling
@EnableAsync
@ServletComponentScan
@MapperScan({"com.huoji.**.mapper"})
// @SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, org.activiti.spring.boot.SecurityAutoConfiguration.class, SecurityAutoConfiguration.class})
@SpringBootApplication
public class Application {

    public static void main(String[] args) throws IOException {

        ConfigurableApplicationContext app = SpringApplication.run(Application.class, args);
        Environment env = app.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        String dataBseUrl = env.getProperty("spring.datasource.url");
        String envSign = env.getProperty("spring.profiles.active");

        log.info("\n-------------------------------\n" +
                "当前启动环境:\t" + envSign + "\n" +
                "项目启动地址:\t" + ip + ":" + port + "\n" +
                "后端请求地址:\t" + ip + ":" + port + "\n" +
                "数据库访问地址:\t" + dataBseUrl + "\n" +
                "-------------------------------"
        );

//        if ("dev".equals(envSign))
//            Runtime.getRuntime().exec("cmd /c start http://" + ip + ":" + port);

    }
}
