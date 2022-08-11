package com.tang.compiler;


import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.FileObject;

/**
 * @author tanganna
 */
@SupportedAnnotationTypes({"com.tang.annotation.MyClass"})
public class MyClassProcessor extends AbstractProcessor {

    /**
     * @param set
     * @param roundEnvironment
     * @return 该注解是否可以被其他注解处理器处理
     */
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        if (!roundEnvironment.processingOver()) {
            Messager messager = processingEnv.getMessager();
            messager.printMessage(Diagnostic.Kind.NOTE, "开始");

            String code = "package com.tang.demo;\n" +
                    "\n" +
                    "import androidx.appcompat.app.AppCompatActivity;\n" +
                    "\n" +
                    "import android.os.Bundle;\n" +
                    "\n" +
                    "public class MainActivity2 extends AppCompatActivity {\n" +
                    "\n" +
                    "    @Override\n" +
                    "    protected void onCreate(Bundle savedInstanceState) {\n" +
                    "        super.onCreate(savedInstanceState);\n" +
                    "        setContentView(R.layout.activity_main);\n" +
                    "    }\n" +
                    "}";
            Filer filer = processingEnv.getFiler();
            OutputStream outputStream = null;
            try {
                FileObject fileObject = filer.createSourceFile("MainActivity2");
                outputStream = fileObject.openOutputStream();
                outputStream.write(code.getBytes());
                outputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
        return true;
    }
}