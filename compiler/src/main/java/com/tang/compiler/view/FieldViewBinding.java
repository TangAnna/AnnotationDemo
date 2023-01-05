package com.tang.compiler.view;

import javax.lang.model.type.TypeMirror;

/**
 * description:使用注解的字段对象
 * time: 2022/8/11 11:45 AM.
 *
 * @author TangAnna
 * email: 18201399976@163.com
 */
public class FieldViewBinding {
    public String fieldName;
    public TypeMirror fieldType;
    public int viewId;

    public FieldViewBinding(String fieldName, TypeMirror fieldType, int viewId) {
        this.fieldName = fieldName;
        this.fieldType = fieldType;
        this.viewId = viewId;
    }
}
