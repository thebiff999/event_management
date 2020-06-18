package de.fhms.sweng.event_management.clients;

import de.fhms.sweng.event_management.dto.BusinessUserTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url="${users.url}/user",
        name="UserRestClient")
public interface UserClient {

    @GetMapping
    BusinessUserTO getUserById(@RequestHeader("Authorization") String jwt, @RequestParam("id") Integer id);

    @GetMapping
    BusinessUserTO getUserByMail(@RequestHeader("Authorization") String jwt, @RequestParam("email") String email);

}
