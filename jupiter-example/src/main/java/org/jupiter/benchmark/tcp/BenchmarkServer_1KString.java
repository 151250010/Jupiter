/*
 * Copyright (c) 2015 The Jupiter Project
 *
 * Licensed under the Apache License, version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jupiter.benchmark.tcp;

import org.jupiter.common.util.SystemPropertyUtil;
import org.jupiter.monitor.MonitorServer;
import org.jupiter.transport.netty.JNettyTcpAcceptor;
import org.jupiter.transport.netty.NettyAcceptor;

/**
 * 飞行记录: -XX:+UnlockCommercialFeatures -XX:+FlightRecorder
 *
 * jupiter
 * org.jupiter.benchmark.tcp
 *
 * @author jiachun.fjc
 */
public class BenchmarkServer_1KString {

    public static void main(String[] args) {
        final int processors = Runtime.getRuntime().availableProcessors();
        SystemPropertyUtil
                .setProperty("jupiter.processor.executor.core.num.workers", String.valueOf(processors));
        SystemPropertyUtil
                .setProperty("jupiter.metric.csv.reporter", "false");
        SystemPropertyUtil
                .setProperty("jupiter.metric.report.period", "1");

        NettyAcceptor server = new JNettyTcpAcceptor(18099, processors);
        MonitorServer monitor = new MonitorServer();
        try {
            monitor.start();

            server.serviceRegistry()
                    .provider(new ServiceImpl_1KString())
                    .register();

            server.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
