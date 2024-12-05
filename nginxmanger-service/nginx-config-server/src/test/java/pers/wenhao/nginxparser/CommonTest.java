/*
 * Copyright 2014 Alexey Plotnik
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package pers.wenhao.nginxparser;


import com.github.odiszapc.nginxparser.NgxBlock;
import com.github.odiszapc.nginxparser.NgxEntry;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static pers.wenhao.nginxparser.TestUtils.*;

public class CommonTest extends ParseTestBase {

    @Test
    public void testC1() throws Exception {
        Iterator<NgxEntry> it = parse("common/c1.conf").getEntries().iterator();

        assertParam(it.next(), "user", "nginx");
        assertParam(it.next(), "worker_processes", "2");
        assertParam(it.next(), "timer_resolution", "100ms");
        assertParam(it.next(), "worker_rlimit_nofile", "8192");
        assertParam(it.next(), "worker_priority", "-10");
        Assertions.assertFalse(it.hasNext());
    }

    @Test
    public void testC2() throws Exception {
        Iterator<NgxEntry> it = parse("common/c2.conf").getEntries().iterator();
        assertParam(it.next(), "error_log", "/var/log/nginx/error.log", "warn");
        assertParam(it.next(), "pid", "/var/run/nginx.pid");
        NgxBlock events = assertBlock(it.next(), "events");
        Iterator<NgxEntry> eventsIt = events.getEntries().iterator();
        assertParam(eventsIt.next(), "worker_connections", "2048");
        assertParam(eventsIt.next(), "use", "epoll");
        Assertions.assertFalse(it.hasNext());
    }

    @Test
    public void testC3() throws Exception {
        Iterator<NgxEntry> it = parse("common/c3.conf").getEntries().iterator();

        assertParam(it.next(), "user", "nginx");
        assertComment(it.next(), "worker_processes  2;");
        assertParam(it.next(), "worker_priority", "-10");
        assertParam(it.next(), "proxy_pass", "http://unix:/opt/apps/ipn/ipn.sock:/");
        Assertions.assertFalse(it.hasNext());
    }

    @Test
    public void testC4() throws Exception {
        Iterator<NgxEntry> it = parse("common/c4.conf").getEntries().iterator();

        assertBlock(it.next(), "location", "@backend");
        Assertions.assertFalse(it.hasNext());
    }

    @Test
    public void testC5() throws Exception {
        Iterator<NgxEntry> it = parse("common/c5.conf").getEntries().iterator();

        assertBlock(it.next(), "location", "~", "/\\.");
        Assertions.assertFalse(it.hasNext());
    }

    @Test
    public void testC6() throws Exception {
        Iterator<NgxEntry> it = parse("common/c6.conf").getEntries().iterator();

        assertBlock(it.next(), "location", "~*", "\\.(?:ico|css|js|gif|jpe?g|png)$");
        Assertions.assertFalse(it.hasNext());
    }

    @Test
    public void testC7() throws Exception {
        Iterator<NgxEntry> it = parse("common/c7.conf").getEntries().iterator();

        assertParam(it.next(), "fastcgi_split_path_info", "^(.+\\.php)(/.+)$");
        Assertions.assertFalse(it.hasNext());
    }
}
