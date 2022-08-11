package com.tang.compiler.view;

import javax.lang.model.type.TypeMirror;

/**
 * description:使用注解的字段对象
 * time: 2022/8/11 11:45 AM.
 *
 * @author TangAnna
 * email: tang_an@murongtech.com
 * copyright: 北京沐融信息科技股份有限公司
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
