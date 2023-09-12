package org.example;

import org.example.client.AdminClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import utils.FormatException;

@SpringBootApplication
@Import(FormatException.class) //将异常处理类导入启动类
@EnableFeignClients(clients=AdminClient.class) //开启feign
public class GoodsApplication {

    public static void main(String[] args) {

        SpringApplication.run(GoodsApplication.class,args);
    }

}
