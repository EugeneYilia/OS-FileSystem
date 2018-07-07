import java.io.File;
import java.io.IOException;

public class TestgetCanonicalPath {
    public static void main(String[] args) throws IOException {
        File directory = new File("");//参数为空
        String courseFile = directory.getCanonicalPath() ;
        System.out.println(courseFile);
    }
}
