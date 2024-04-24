package analysisOfEndpointConnections;

import java.io.File;
import java.util.Collection;

import com.thoughtworks.qdox.JavaProjectBuilder;
import com.thoughtworks.qdox.model.JavaAnnotation;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaMethod;

public class AnalysisOfEndpointConnections {

    public static void main(String[] args) {
        JavaProjectBuilder builder = new JavaProjectBuilder();
        for (String filePath : args) {
            builder.addSourceTree(new File(filePath));
        }

        Collection<JavaClass> classes = builder.getClasses();
        for (JavaClass javaClass : classes) {
            for (JavaMethod method : javaClass.getMethods()) {
                for (JavaAnnotation annotation : method.getAnnotations()) {
                    if (annotation.getType().getFullyQualifiedName().startsWith("org.springframework.web.bind.annotation")) {
                        System.out.println("Endpoint: " + method.getName());
                        System.out.println("HTTP Method: " + annotation.getType().getName());
                        System.out.println("Class: " + javaClass.getFullyQualifiedName());
                        System.out.println("---------------------------------------------------");
                    }
                }
            }
        }
    }
}
