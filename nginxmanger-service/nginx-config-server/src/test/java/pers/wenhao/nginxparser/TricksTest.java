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

import static pers.wenhao.nginxparser.TestUtils.assertBlock;
import static pers.wenhao.nginxparser.TestUtils.assertParam;

public class TricksTest extends ParseTestBase {

    @Test
    public void testC1() throws Exception {
        Iterator<NgxEntry> it = parse("tricks/c1.conf").getEntries().iterator();
        NgxBlock loc = (NgxBlock) it.next();
        assertBlock(loc, "events");

        Iterator<NgxEntry> it2 = loc.getEntries().iterator();
        assertParam(it2.next(), "worker_connections", "2048");
        assertParam(it2.next(), "use", "epoll");
        Assertions.assertFalse(it2.hasNext());
        Assertions.assertFalse(it.hasNext());
    }
}
