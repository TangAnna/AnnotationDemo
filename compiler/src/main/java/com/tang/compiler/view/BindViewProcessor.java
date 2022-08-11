package com.tang.compiler.view;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import com.tang.annotation.view.BindView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;

/**
 * description:
 * time: 2022/8/11 11:23 AM.
 *
 * @author TangAnna
 * email: tang_an@murongtech.com
 * copyright: 北京沐融信息科技股份有限公司
 */
@SupportedAnnotationTypes({"com.tang.annotation.view.BindView"})
public class BindViewProcessor extends AbstractProcessor {


    /**
     * 可以打印日志 传递message
     */
    private Messager mMessager;
    private Elements mElementUtils;
    private Filer mFiler;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mMessager = processingEnv.getMessager();
        mElementUtils = processingEnv.getElementUtils();
        mFiler = processingEnv.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        Map<TypeElement, List<FieldViewBinding>> targetMap = getTargetMap(roundEnvironment);

        createJavaFile(targetMap.entrySet());
        return true;
    }

    /**
     * 遍历获取所有使用注解的字段
     *
     * @param roundEnvironment
     * @return
     */
    private Map<TypeElement, List<FieldViewBinding>> getTargetMap(RoundEnvironment roundEnvironment) {
        Map<TypeElement, List<FieldViewBinding>> targetMap = new LinkedHashMap<>();
        //@BindView 注解修饰的所有字段
        Set<? extends Element> annotatedElements = roundEnvironment.getElementsAnnotatedWith(BindView.class);
        for (Element element : annotatedElements) {
            //字段名字 （mTvNum）
            String fieldName = element.getSimpleName().toString();
            //字段类型 （TextView）
            TypeMirror fieldType = element.asType();
            //@BindView注解元素中设置的值 (也就是id)
            int viewId = element.getAnnotation(BindView.class).value();
            //注解所在的类引用全名 (com.tang.xiangxuedemo.MainActivity)
            TypeElement typeElement = (TypeElement) element.getEnclosingElement();

            List<FieldViewBinding> list = targetMap.get(typeElement);
            if (list == null) {
                list = new ArrayList<>();
                targetMap.put(typeElement, list);
            }
            list.add(new FieldViewBinding(fieldName, fieldType, viewId));
        }
        return targetMap;
    }

    /**
     * 创建Java文件
     *
     * @param entries
     */
    public void createJavaFile(Set<Map.Entry<TypeElement, List<FieldViewBinding>>> entries) {

        for (Map.Entry<TypeElement, List<FieldViewBinding>> entrie : entries) {
            TypeElement typeElement = entrie.getKey();
            List<FieldViewBinding> list = entrie.getValue();
            if (list == null || list.size() == 0) {
                continue;
            }
            //获取包名
            String packageName = mElementUtils.getPackageOf(typeElement).getQualifiedName().toString();
            //根据旧Java类名创建新的Java文件
            String className = typeElement.getQualifiedName().toString().substring(packageName.length() + 1);
            String newClassName = className + "_ViewBinding";

            MethodSpec.Builder methodBuilder = MethodSpec.constructorBuilder()
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(ClassName.bestGuess(className), "target");

            for (FieldViewBinding fieldViewBinding : list) {
                String packageNameString = fieldViewBinding.fieldType.toString();
                ClassName viewClass = ClassName.bestGuess(packageNameString);
                methodBuilder.addStatement("target.$L=($T)target.findViewById($L)", fieldViewBinding.fieldName
                        , viewClass, fieldViewBinding.viewId);

            }
            TypeSpec typeBuilder = TypeSpec.classBuilder(newClassName)
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                    .addMethod(methodBuilder.build())
                    .build();

            JavaFile javaFile = JavaFile.builder(packageName, typeBuilder)
                    .addFileComment("Generated code from Butter Knife. Do not modify!")
                    .build();

            try {
                javaFile.writeTo(mFiler);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
