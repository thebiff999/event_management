package de.fhms.sweng.event_management.clients;

import de.fhms.sweng.event_management.dto.UserTO;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(url="${identity.url}/rest/users/login",
        name="LoginRestClient")
public interface LoginClient {

    @PostMapping
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    String loginUser(@RequestBody UserTO login);

}
