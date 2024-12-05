package pers.wenhao.nginxparser;

import com.github.odiszapc.nginxparser.NgxConfig;
import com.github.odiszapc.nginxparser.NgxDumper;
import com.github.odiszapc.nginxparser.NgxEntry;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AlterConfigTest extends ParseTestBase {
    @Test
    public void removeOneLocation() throws Exception {
        NgxConfig conf = parse("nested/c2.conf");
        NgxEntry ngxEntry = conf.find(NgxConfig.BLOCK, "http", "server", "location");
        conf.remove(ngxEntry);

        final String expected = "" +
                "http {\n" +
                "  server {\n" +
                "  }\n" +
                "}\n";

        assertEquals(new NgxDumper(conf).dump(), expected);
    }

    @Test
    public void removeManyLocations() throws Exception {
        NgxConfig conf = parse("nested/c4.conf");
        List<NgxEntry> locations = conf.findAll(NgxConfig.BLOCK, "http", "server", "location");
        assertEquals(2, locations.size());
        conf.removeAll(locations);
        assertEquals(0, conf.findAll(NgxConfig.BLOCK, "http", "server", "location").size());

        final String expected = "" +
                "http {\n" +
                "  server {\n" +
                "  }\n" +
                "}\n";

        assertEquals(new NgxDumper(conf).dump(), expected);
    }


    @Test
    public void removeNullElement() throws Exception {
        NullPointerException exception = Assertions.assertThrows(NullPointerException.class, () -> {
            NgxConfig conf = parse("nested/c4.conf");
            conf.remove(null);
        });
        Assertions.assertEquals("Item can not be null", exception.getMessage());
    }

    @Test
    public void removeNullElements() throws Exception {
        NullPointerException exception = Assertions.assertThrows(NullPointerException.class, () -> {
            NgxConfig conf = parse("nested/c4.conf");
            conf.removeAll(null);
        });
        Assertions.assertEquals("Items can not be null", exception.getMessage());
    }
}
