import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class JsonReader {
    public static String readJsonFromResource(String resourcePath) {
        StringBuilder content = new StringBuilder();

        try (InputStream inputStream = JsonReader.class.getClassLoader()
                .getResourceAsStream(resourcePath);
             BufferedReader reader = new BufferedReader(
                     new InputStreamReader(inputStream))) {

            if (inputStream == null) {
                throw new IllegalArgumentException("Файл не найден в ресурсах: " + resourcePath);
            }

            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }

        } catch (IOException e) {
            throw new RuntimeException("Ошибка при чтении файла: " + resourcePath, e);
        }

        return content.toString();
    }
}
