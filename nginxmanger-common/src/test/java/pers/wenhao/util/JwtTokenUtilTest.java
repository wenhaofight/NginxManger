package pers.wenhao.util;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

class JwtTokenUtilTest {

    @Test
    void generateTokenAdmin() {
        Map<String, Object> load = new HashMap<>();
        load.put("abc", "def");
        load.put("中文", "123");
        try {
            String adminToken = JwtTokenUtil.generateTokenAdmin(UUID.randomUUID().toString(), load, 900L);
            System.out.println(adminToken);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void generateTokenUser() {
    }

    @Test
    void generateToken() {
    }

    @Test
    void parseToken() throws Exception {
        Map<String, Object> load = new HashMap<>();
        load.put("abc", "def");
        load.put("中文", "123");
        String adminToken = JwtTokenUtil.generateTokenUser(UUID.randomUUID().toString(), load, 9000L);
        Map<String, Object> parseToken = JwtTokenUtil.parseToken(adminToken);
        System.out.println(parseToken);
    }
}
