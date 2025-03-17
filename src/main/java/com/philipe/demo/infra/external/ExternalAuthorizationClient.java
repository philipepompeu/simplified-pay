package com.philipe.demo.infra.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "externalAuthService", url = "https://util.devi.tools/api/v2")
public interface ExternalAuthorizationClient {
    @GetMapping("/authorize")
    String authorize();
}
