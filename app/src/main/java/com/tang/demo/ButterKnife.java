package com.tang.demo;

import android.app.Activity;

/**
 * description:
 * time: 2022/8/11 2:32 PM.
 *
 * @author TangAnna
 * email: 18201399976@163.com
 */
public class ButterKnife {

    public static void bind(Activity activity) {
        //1、获取全限定类名
        String name = activity.getClass().getName();
        try {
            //2、 根据全限定类名获取通过注解解释器生成的Java类
            Class<?> clazz = Class.forName(name + "_ViewBinding");
            //3、 通过反射获取构造方法并创建实例完成依赖注入
            clazz.getConstructor(activity.getClass()).newInstance(activity);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
