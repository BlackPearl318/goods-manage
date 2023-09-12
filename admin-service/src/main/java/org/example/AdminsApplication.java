package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import utils.FormatException;

@SpringBootApplication
@Import(FormatException.class) //将异常处理类导入启动类
public class AdminsApplication {

    public static void main(String[] args) {

        SpringApplication.run(AdminsApplication.class,args);

    }
}
