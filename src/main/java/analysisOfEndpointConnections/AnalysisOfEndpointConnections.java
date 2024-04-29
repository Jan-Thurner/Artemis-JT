package analysisOfEndpointConnections;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;

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
        String[] testArray = new String[3];
        testArray[0] = "src/main/java/de/tum/in/www1/artemis/web/rest/tutorialgroups/TutorialGroupFreePeriodResource.java";
        testArray[1] = "src/main/webapp/app/course/tutorial-groups/services/tutorial-group-free-period.service.ts";
        testArray[2] = "src/main/java/de/tum/in/www1/artemis/web/rest/tutorialgroups/TutorialGroupFreePeriodResource-test.java";

        String[] modifiedServerFiles = Arrays.stream(testArray).filter(filePath -> filePath.endsWith(".java")).toArray(String[]::new);
        String[] stillExistingServerFiles = Arrays.stream(modifiedServerFiles).filter(filePath -> Files.exists(Paths.get(filePath))).toArray(String[]::new);
        String[] removedServerFiles = Arrays.stream(modifiedServerFiles).filter(filePath -> !Files.exists(Paths.get(filePath))).toArray(String[]::new);

        analyzeServerEndpoints(stillExistingServerFiles);
        analyzeClientRESTCalls(testArray);
    }

    private static void analyzeServerEndpoints(String[] filePaths) {
        JavaProjectBuilder builder = new JavaProjectBuilder();
        for (String filePath : filePaths) {
            builder.addSourceTree(new File(filePath));
        }

        Collection<JavaClass> classes = builder.getClasses();
        for (JavaClass javaClass : classes) {
            for (JavaMethod method : javaClass.getMethods()) {
                for (JavaAnnotation annotation : method.getAnnotations()) {
                    if (annotation.getType().getFullyQualifiedName().startsWith("org.springframework.web.bind.annotation")) {
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

    private static void analyzeClientRESTCalls(String[] filePaths) {

    }
}
