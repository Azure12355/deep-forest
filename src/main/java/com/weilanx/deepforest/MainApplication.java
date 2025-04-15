package com.weilanx.deepforest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication; // ** 1. 导入注解 **
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 主类（项目启动入口）
 *
 * @author <a href="https://github.com/Azure12355">蔚蓝</a>
 * @from
 */
// todo 如需开启 Redis，须移除 exclude 中的内容 (这个注释可能与当前问题无关)
@SpringBootApplication // ** 2. 确保此注解存在 **
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
public class MainApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
}