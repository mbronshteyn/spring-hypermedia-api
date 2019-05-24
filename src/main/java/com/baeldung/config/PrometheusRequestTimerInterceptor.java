package com.baeldung.config;

import io.prometheus.client.Summary;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component
public class PrometheusRequestTimerInterceptor extends HandlerInterceptorAdapter {

    private static final String REQ_PARAM_TIMING = "timing";

    private static final Summary responseTimeInMs = Summary
            .build()
            .name("http_response_time_milliseconds")
            .labelNames("method", "handler", "status")
            .help("Request completed time in milliseconds")
            .maxAgeSeconds(120)
            .ageBuckets(2)
            .quantile(0.5, 0.05)
            .quantile(0.95, 0.05)
            .quantile(0.99, 0.05)
            .register();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setAttribute(REQ_PARAM_TIMING, System.currentTimeMillis());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        Long timingAttr = (Long) request.getAttribute(REQ_PARAM_TIMING);
        long requestTime = System.currentTimeMillis() - timingAttr;
        String handlerLabel = handler.toString();
        // get short form of handler method name
        if (handler instanceof HandlerMethod) {
            Method method = ((HandlerMethod) handler).getMethod();
            handlerLabel = method.getDeclaringClass().getSimpleName() + "." + method.getName();
        }
        responseTimeInMs.labels(request.getMethod(), handlerLabel, Integer.toString(response.getStatus())).observe(
                requestTime);
    }

}
