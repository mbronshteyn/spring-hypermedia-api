package com.baeldung;

import io.micrometer.core.instrument.composite.CompositeMeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringHypermediaApiApplication {

    //

    public static void main(String[] args) {
        SpringApplication.run(SpringHypermediaApiApplication.class, args);

        CompositeMeterRegistry meterRegistry = new CompositeMeterRegistry();
        meterRegistry.add(new SimpleMeterRegistry());
//        meterRegistry.add( new PrometheusMeterRegistry( null ));
    }
}

