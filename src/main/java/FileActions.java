import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileActions {
    public static String read(String pathString) {
        return null;
    }

    public static String write(String data,String fileName) {
        Path path = Paths.get(fileName);
        byte[] strToBytes = data.getBytes();
        try {
            Files.write(path, strToBytes);
            return fileName;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
