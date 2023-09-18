package kaiqueefigui.com.github.springmicroservice.config.interceptor;

import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kaiqueefigui.com.github.springmicroservice.config.ApplicationProperties;
import kaiqueefigui.com.github.springmicroservice.config.MDCProperties;
import lombok.extern.log4j.Log4j2;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

@Log4j2
@WebFilter
public class CorrelationHeaderInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        var correlationId = getCurrentCorrelationId(request);
        MDC.put(MDCProperties.CORRELATION_ID, correlationId);
        MDC.put(MDCProperties.SERVICE, ApplicationProperties.APP_NAME);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        MDC.clear();
    }

    private static String getCurrentCorrelationId(HttpServletRequest httpServletRequest) {
        String currentCorrelationId = httpServletRequest.getHeader(MDCProperties.CORRELATION_ID);

        if (currentCorrelationId == null) {
            currentCorrelationId = UUID.randomUUID().toString();
            log.info("No correlationId found in Header. Generated : " + currentCorrelationId);
        } else {
            log.info("Found correlationId in Header : " + currentCorrelationId);
        }
        return currentCorrelationId;
    }
}
