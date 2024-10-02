package com.rush.banking.api.gateway;

import com.rush.banking.api.gateway.constant.Constants;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiFallbackController {

    @GetMapping("/authority-service-fallBack")
    public String authorityServiceFallBackMethod() {
        return Constants.AUTHORITY_FALLBACK;
    }

    @GetMapping("/user-service-fallBack")
    public String userServiceFallBackMethod() {
        return Constants.USER_FALLBACK;
    }
}
