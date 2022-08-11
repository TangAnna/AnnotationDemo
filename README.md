# AnnotationDemo
java 注解使用

一、ButterKnife 实现原理

@BindView
1.使用APT的方式遍历项目中使用注解的字段；

2.获取字段修饰的元素的字段名、view类型、view 的id 放到一个Map中；

3.获取字段所在类的引用名字；

4.通过类名+_ViewBinding后缀 编写新的Java文件；

5.生成的Java文件构造方法:构造中完成findViewById；

```public MainActivity_ViewBinding(MainActivity target) {
    target.mTextView = (TextView)target.findViewById(2131231127);
}
```

6.有一个注入类
```public static void bind(Activity activity) {
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
```

通过反射创建 注解解释器生成的Java对象的实例同时完成了view的findViewById；