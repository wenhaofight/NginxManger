package pers.wenhao;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class PathTest {
    @Test
    void testClassPAth() throws IOException {
        String path = "nginx.conf";
        StringBuilder stringBuilder;
        try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(path)) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            stringBuilder = new StringBuilder();

            if (inputStream != null) {
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    stringBuilder.append(new String(buffer, 0, bytesRead, StandardCharsets.UTF_8));
                }
            }
        }


        String fileContent = stringBuilder.toString();
        System.out.println(fileContent);

    }
}
