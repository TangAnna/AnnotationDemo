package com.tang.annotation.view;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * description:
 * time: 2022/8/11 11:20 AM.
 *
 * @author TangAnna
 * email: 18201399976@163.com
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.FIELD)
public @interface BindView {

    /**
     * @return view 对应的id
     */
    int value();
}
