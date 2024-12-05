package pers.wenhao;

import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class PathTest {
    @Test
    void testClassPAth() throws IOException {
        String path = Objects.requireNonNull(this.getClass().getResource("/nginx.conf")).getPath();
        System.out.println(path);
        StringBuilder stringBuilder;
        try (FileInputStream input = new FileInputStream(path)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            stringBuilder = new StringBuilder();

            while ((bytesRead = input.read(buffer)) != -1) {
                stringBuilder.append(new String(buffer, 0, bytesRead, StandardCharsets.UTF_8));
            }
        }

        String fileContent = stringBuilder.toString();
        System.out.println(fileContent);

    }
}
