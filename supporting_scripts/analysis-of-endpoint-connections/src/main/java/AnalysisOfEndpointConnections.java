package analysisOfEndpointConnections;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import com.thoughtworks.qdox.JavaProjectBuilder;
import com.thoughtworks.qdox.model.JavaAnnotation;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaMethod;

public class AnalysisOfEndpointConnections {

    /**
     * This is the main method which makes use of addNum method.
     *
     * @param args Unused.
     */
    public static void main(String[] args) {
        String[] serverFiles = Arrays.stream(args).filter(filePath -> filePath.endsWith(".java")).toArray(String[]::new);
        analyzeServerEndpoints(serverFiles);
    }

    private static void analyzeServerEndpoints(String[] filePaths) {
        JavaProjectBuilder builder = new JavaProjectBuilder();
        for (String filePath : filePaths) {
            builder.addSourceTree(new File(filePath));
        }

        Collection<JavaClass> classes = builder.getClasses();
        for (JavaClass javaClass : classes) {
            Optional<JavaAnnotation> requestMappingOptional = javaClass.getAnnotations().stream()
                .filter(annotation ->
                    annotation.getType().getFullyQualifiedName().startsWith("org.springframework.web.bind.annotation")
                    && annotation.getProperty("value").equals("RequestMapping"))
                .findFirst();
            for (JavaMethod method : javaClass.getMethods()) {
                for (JavaAnnotation annotation : method.getAnnotations()) {
                    if (annotation.getType().getFullyQualifiedName().startsWith("org.springframework.web.bind.annotation")) {
                        if (requestMappingOptional.isPresent()) {
                            System.out.println("Request Mapping: " + requestMappingOptional.get().getProperty("value"));
                        };
                        System.out.println("Endpoint: " + method.getName());
                        System.out.println("HTTP Method: " + annotation.getType().getName());
                        System.out.println("Path: " + annotation.getProperty("value"));
                        System.out.println("Class: " + javaClass.getFullyQualifiedName());
                        System.out.println("Line: " + method.getLineNumber());
                        System.out.println("---------------------------------------------------");
                    }
                }
            }
        }
    }
}
