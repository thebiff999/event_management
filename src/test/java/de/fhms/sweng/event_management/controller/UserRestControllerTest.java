package de.fhms.sweng.event_management.controller;

import de.fhms.sweng.event_management.clients.ClientService;
import de.fhms.sweng.event_management.dto.BusinessUserTO;
import de.fhms.sweng.event_management.entities.BusinessUser;
import de.fhms.sweng.event_management.security.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers= UserRestController.class)
public class UserRestControllerTest {


    @Autowired
    private MockMvc mvc;

    @MockBean
    private ClientService clientService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    private BusinessUserTO userTO;
    private BusinessUser user;

    private static final String USERNAME = "max@mustermann.de";
    private final String AUTH_HEADER = "Bearer ANY-JWT-STRING";

    @BeforeEach
    public void setUp() {
        userTO = new BusinessUserTO();
        userTO.setId(1);

        user = new BusinessUser();
        user.setId(1);
        user.setMail(USERNAME);
        user.setFirstName("Max");
        user.setLastName("Mustermann");

        String TEST_USER = "test@test.de";
        UserDetails userDetails = User.withUsername(USERNAME)
                .password("***")
                .authorities(user.getRole())
                .build();

        given(jwtTokenProvider.isValidJWT(any(String.class))).willReturn(true);
        given(jwtTokenProvider.getUsername(any(String.class))).willReturn(TEST_USER);
        given(jwtTokenProvider.resolveToken(any(HttpServletRequest.class))).willReturn(AUTH_HEADER.substring(7));
        given(jwtTokenProvider.getAuthentication(any(String.class))).willReturn(new UsernamePasswordAuthenticationToken(userDetails, "s", userDetails.getAuthorities()));

    }

    @Test
    void testGetUserById() throws Exception {
        given(clientService.getUserById(1)).willReturn(userTO);
        this.mvc.perform(get("/user/byId/?id=1")
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization",this.AUTH_HEADER))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1));
    }

    @Test
    void testGetUserByMail() throws Exception {
        given(clientService.getUserByMail("max@mustermann.de")).willReturn(userTO);
        this.mvc.perform(get("/user/byMail?mail=max@mustermann.de")
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization",this.AUTH_HEADER))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1));
    }

}
