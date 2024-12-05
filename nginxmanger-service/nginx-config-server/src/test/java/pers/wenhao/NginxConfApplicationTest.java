package pers.wenhao;

import com.github.odiszapc.nginxparser.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NginxConfParseTest {
    @Test
    void nginxConfParseTest() throws IOException {
        // How to perform basic parsing of the following Nginx config:
        NgxConfig conf = NgxConfig.read("src/test/resources/nginx.conf");
        NgxParam workers = conf.findParam("worker_processes");       // Ex.1
        String value = workers.getValue();// "1"
        assertEquals("1", value);
        NgxParam listen = conf.findParam("http", "server", "listen"); // Ex.2
        String listenPort = listen.getValue();// "8889"
        assertEquals("8889", listenPort);
        ArrayList<NgxToken> tokens = (ArrayList<NgxToken>) listen.getTokens();
        tokens.set(1, new NgxToken("9999"));
        System.out.println(listen);


        List<NgxEntry> rtmpServers = conf.findAll(NgxConfig.BLOCK, "rtmp", "server"); // Ex.3
        for (NgxEntry entry : rtmpServers) {
            ((NgxBlock) entry).getName(); // "server"
            ((NgxBlock) entry).findParam("application", "live"); // "on" for the first iter, "off" for the second one
        }

        // dumper
        NgxDumper dumper = new NgxDumper(conf);
        dumper.dump(System.out);
    }

}