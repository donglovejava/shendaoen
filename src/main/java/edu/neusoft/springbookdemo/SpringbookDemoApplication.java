package edu.neusoft.springbookdemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("edu.neusoft.springbookdemo.mapper")
public class SpringbookDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbookDemoApplication.class, args);
    }

}
