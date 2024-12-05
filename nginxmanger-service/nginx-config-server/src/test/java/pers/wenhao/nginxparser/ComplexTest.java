package pers.wenhao.nginxparser;

import com.github.odiszapc.nginxparser.NgxBlock;
import com.github.odiszapc.nginxparser.NgxConfig;
import com.github.odiszapc.nginxparser.NgxDumper;
import com.github.odiszapc.nginxparser.NgxEntry;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;

public class ComplexTest extends ParseTestBase {
    @Test
    public void testC1() throws Exception {
        Iterator<NgxEntry> it = parse("complex/c1.conf").getEntries().iterator();
        NgxBlock loc = (NgxBlock) it.next();
        TestUtils.assertBlock(loc, "location", "~", "^/cat/(.*)^/cat(_)");
        Assertions.assertTrue(loc.getEntries().isEmpty());
        Assertions.assertFalse(it.hasNext());
    }

    @Test
    public void testC2() throws Exception {
        Iterator<NgxEntry> it = parse("complex/c2.conf").getEntries().iterator();
        NgxBlock loc = (NgxBlock) it.next();
        TestUtils.assertBlock(loc, "location", "~", "\\.(gif|png|jpe?g)$");
        Assertions.assertTrue(loc.getEntries().isEmpty());
        Assertions.assertFalse(it.hasNext());
    }

    @Test
    public void testHashSymbols() throws Exception {
        final NgxConfig parsedConfig = parse("complex/nginx-hash-symbols.conf");
        NgxDumper ngxDumper = new NgxDumper(parsedConfig);
        ngxDumper.dump(System.out);

        List<NgxEntry> server = parsedConfig.findAll(NgxConfig.BLOCK, "http", "server");
        Assertions.assertNotNull(server.get(0));
    }

    @Test
    public void mapWithRegexps() throws Exception {
        final NgxConfig parsedConfig = parse("complex/map_regexps.conf");
        NgxDumper ngxDumper = new NgxDumper(parsedConfig);
        ngxDumper.dump(System.out);

        Iterator<NgxEntry> it = parsedConfig.getEntries().iterator();
        NgxBlock map = (NgxBlock) it.next();
        TestUtils.assertBlock(map, "map", "$request_uri", "$site_redirect");

        Assertions.assertEquals(6, map.getEntries().size());
        final Iterator<NgxEntry> mapEntries = map.getEntries().iterator();
        TestUtils.assertParam(mapEntries.next(), "default", "no_match");
        TestUtils.assertParam(mapEntries.next(), "~*^/foo/?$", "http://example.com/foo.html");
        TestUtils.assertParam(mapEntries.next(), "~^/(?i)bar/?$", "http://example.com/#!/register/1?corporate=BAR");
        TestUtils.assertParam(mapEntries.next(), "\"~^/(?i)bar/?$\"", "http://example.com/#!/register/1?corporate=BAR");
        // Basically it's invalid regexp, it actually not a regexp
        // See: http://nginx.org/en/docs/http/ngx_http_map_module.html
        // Each regexp must be prefixed by ~ or ~*
        TestUtils.assertParam(mapEntries.next(), "\"^/(?i)bar/?$\"", "http://example.com/#!/register/1?corporate=BAR");
        TestUtils.assertParam(mapEntries.next(), "~^/(?i)baz/?$", "http://example.com/baz.html");
    }

    @Test
//    @Ignore("Semicolon is broken")
    public void semicolon() throws Exception {
        final NgxConfig parsedConfig = parse("complex/semicolon.conf");
        NgxDumper ngxDumper = new NgxDumper(parsedConfig);
        ngxDumper.dump(System.out);

        Iterator<NgxEntry> it = parsedConfig.getEntries().iterator();
        NgxBlock loc = (NgxBlock) it.next();
        TestUtils.assertBlock(loc, "location", "~*", "(^.+\\.(xhtml)(;.?))");
        Assertions.assertEquals(1, loc.getEntries().size());
        Assertions.assertFalse(it.hasNext());
    }
}
