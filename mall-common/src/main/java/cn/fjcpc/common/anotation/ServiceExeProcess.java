package cn.fjcpc.common.anotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE,ElementType.METHOD})//作用于类或方法上
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ServiceExeProcess {
    String value() default "";
}
