package com.baeldung;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.composite.CompositeMeterRegistry;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringHypermediaApiApplication {

    //

    public static void main(String[] args) {
        SpringApplication.run(SpringHypermediaApiApplication.class, args);

        CompositeMeterRegistry meterRegistry = new CompositeMeterRegistry();
        meterRegistry.add( new PrometheusMeterRegistry( null ));
    }


}
