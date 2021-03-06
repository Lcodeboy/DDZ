package com.game.framework.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//使用在类型上
@Target(ElementType.TYPE)
// 运行时保留
@Retention(RetentionPolicy.RUNTIME)
public @interface HttpMessage {

    /**
     *
     * @Description: 处理器
     * @throws @return
     */
    public String value();
}
