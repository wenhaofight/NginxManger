package pers.wenhao;

import com.github.odiszapc.nginxparser.NgxBlock;
import com.github.odiszapc.nginxparser.NgxConfig;
import com.github.odiszapc.nginxparser.NgxDumper;
import com.github.odiszapc.nginxparser.NgxParam;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class NginxConfGenerationTest {
    @Test
    public void testFileGeneration() throws IOException {
        String path = "nginx1.conf";
        // 获取当前线程的类加载器
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        // 通过类加载器获取对应的URL（这里先尝试获取到所在的目录路径相关信息等）
        // 注意这个获取的路径可能不同环境有不同格式等情况，下面只是一种常见处理思路示例
        String basePath = classLoader.getResource("").getPath();
        // 拼接上要创建的文件名，形成完整的文件路径
        String fullPath = basePath + path;
        System.out.println("文件路径：" + fullPath);
        try {
            // 创建文件对象
            File file = new File(fullPath);
            // 获取文件所在的父目录，若不存在则创建父目录
            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }
            // 创建文件（如果文件不存在的话）
            if (!file.exists()) {
                file.createNewFile();
            }
            // 这里可以进行后续比如往文件里写入内容等操作，下面简单示例打开输出流（实际可按需写内容进去）
            OutputStream outputStream = new FileOutputStream(file);
//            byte[] contentBytes = "hello".getBytes();
//            outputStream.write(contentBytes);
            nginxFileGeneration().dump(outputStream);
            // 关闭输出流（记得合适的时候关闭资源，这里只是简单示例打开关闭操作流程）
            outputStream.close();
            System.out.println("文件创建成功");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("文件创建失败");
        }
    }


    public static NgxDumper nginxFileGeneration() throws IOException {
        NgxConfig ngxConfig = new NgxConfig();

        NgxParam basicParam = new NgxParam();
        basicParam.addValue("test" + " " + "123");
        ngxConfig.addEntry(basicParam);

        NgxBlock ngxBlockHttp = new NgxBlock();
        ngxBlockHttp.addValue("http");
        NgxParam httpParam = new NgxParam();
        httpParam.addValue("httptest" + " " + "456");
        ngxBlockHttp.addEntry(httpParam);


        NgxBlock ngxBlockServer = new NgxBlock();
        ngxBlockServer.addValue("upstream " + "upstream_name");

        NgxParam ngxParam = new NgxParam();
        ngxParam.addValue("server " + "127.0.0.1:8080");
        ngxBlockServer.addEntry(ngxParam);

        ngxBlockHttp.addEntry(ngxBlockServer);

        for (int i = 0; i < 10; i++) {
            ngxConfig.addEntry(ngxBlockHttp);
        }
        ngxConfig.addEntry(ngxBlockHttp);

        NgxDumper dumper = new NgxDumper(ngxConfig);
//        dumper.dump(System.out);
        return dumper;
    }
}

