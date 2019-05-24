package com.baeldung.config;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.exporter.MetricsServlet;
import io.prometheus.client.hotspot.GarbageCollectorExports;
import io.prometheus.client.hotspot.MemoryPoolsExports;
import io.prometheus.client.hotspot.StandardExports;
import io.prometheus.client.hotspot.ThreadExports;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.prometheus.client.Collector;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ConditionalOnClass(CollectorRegistry.class)
public class Config {
    private static final CollectorRegistry metricRegistry = CollectorRegistry.defaultRegistry;


    @Bean
    ServletRegistrationBean registerPrometheusExporterServlet() {
        return new ServletRegistrationBean(new MetricsServlet(metricRegistry), "/metrics");
    }

    @Bean
    ExporterRegister exporterRegister() {
        List<Collector> collectors = new ArrayList<>();
        collectors.add(new StandardExports());
        collectors.add(new MemoryPoolsExports());
        collectors.add(new GarbageCollectorExports());
        collectors.add(new ThreadExports());
        ExporterRegister register = new ExporterRegister(collectors);
        return register;
    }
}
