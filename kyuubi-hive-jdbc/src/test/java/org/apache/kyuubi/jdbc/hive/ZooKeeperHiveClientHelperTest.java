/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.kyuubi.jdbc.hive;

import static org.apache.kyuubi.jdbc.hive.Utils.extractURLComponents;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;
import java.util.Properties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class ZooKeeperHiveClientHelperTest {

  private String uri;

  @Parameterized.Parameters
  public static Collection<String[]> data() {
    return Arrays.asList(
        new String[][] {
          {"jdbc:hive2://hostname:10018/db;zooKeeperNamespace=zookeeper/namespace"},
          {"jdbc:hive2://hostname:10018/db;zooKeeperNamespace=/zookeeper/namespace"},
          {"jdbc:hive2://hostname:10018/db;zooKeeperNamespace=zookeeper/namespace/"},
          {"jdbc:hive2://hostname:10018/db;zooKeeperNamespace=/zookeeper/namespace/"},
          {"jdbc:hive2://hostname:10018/db;zooKeeperNamespace=///zookeeper/namespace///"}
        });
  }

  public ZooKeeperHiveClientHelperTest(String uri) {
    this.uri = uri;
  }

  @Test
  public void testGetZooKeeperNamespace() throws JdbcUriParseException {
    JdbcConnectionParams jdbcConnectionParams = extractURLComponents(uri, new Properties());
    assertEquals(
        "zookeeper/namespace",
        ZooKeeperHiveClientHelper.getZooKeeperNamespace(jdbcConnectionParams));
  }
}
