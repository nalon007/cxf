/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.cxf.systest.https;

import java.util.HashMap;
import java.util.Map;

import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.testutil.common.AbstractBusTestServerBase;
import org.apache.cxf.testutil.common.TestUtil;

/**
 * This server just instantiates a Bus, full stop.
 * Everything else is designed to be spring-loaded.
 */
public class BusServer extends AbstractBusTestServerBase {
    public static final Map<String, String> PORTMAP = new HashMap<>();
    public static void resetPortMap() {
        PORTMAP.clear();
        for (int x = 0; x < 9; x++) {
            PORTMAP.put("PORT" + x, TestUtil.getNewPortNumber(BusServer.class, x));
        }
    }
    public static String getPort(int x) {
        if (PORTMAP.isEmpty()) {
            for (int y = 0; y < 9; y++) {
                PORTMAP.put("PORT" + y, TestUtil.getPortNumber(BusServer.class, y));
            }
        }
        return PORTMAP.get("PORT" + x);
    }


    protected void run()  {
        //
        // Just instantiate the Bus; services will be instantiated
        // and published automatically through Spring
        //
        final BusFactory factory = BusFactory.newInstance();
        Bus bus = factory.createBus();
        setBus(bus);
        BusFactory.setDefaultBus(bus);
        BusFactory.setThreadDefaultBus(bus);
    }

    public static void main(String[] args) {
        try {
            BusServer s = new BusServer();
            s.start();
        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(-1);
        }
    }
}
