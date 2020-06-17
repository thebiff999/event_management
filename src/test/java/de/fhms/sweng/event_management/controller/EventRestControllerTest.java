package de.fhms.sweng.event_management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.fhms.sweng.event_management.dto.BusinessUserTO;
import de.fhms.sweng.event_management.dto.EventTO;
import de.fhms.sweng.event_management.entities.BusinessUser;
import de.fhms.sweng.event_management.entities.Event;
import de.fhms.sweng.event_management.entities.Location;
import de.fhms.sweng.event_management.entities.Preference;
import de.fhms.sweng.event_management.exceptions.ResourceNotFoundException;
import de.fhms.sweng.event_management.security.JwtTokenProvider;
import de.fhms.sweng.event_management.services.BusinessUserService;
import de.fhms.sweng.event_management.services.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@WebMvcTest(controllers= EventRestController.class)
public class EventRestControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private EventService eventService;
    @MockBean
    private BusinessUserService userService;
    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    private final ObjectMapper mapper = new ObjectMapper();

    private Event event;
    private Event event2;
    private Set<Event> eventSet;
    private Preference preference;
    private Set<Preference> preferenceSet;
    private BusinessUser user;
    private BusinessUser user2;
    private Location location;
    private LocalDateTime time;
    private EventTO eventTO;
    private Set<EventTO> eventTOSet;
    private static final String USERNAME = "max@mustermann.de";
    private final String AUTH_HEADER = "Bearer ANY-JWT-STRING";
    private ResourceNotFoundException e;

    @BeforeEach
    public void setUp() {

        //Setting up LocalDateTime variables
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String datetime1 = "2020-06-06 20:00";
        time = LocalDateTime.parse(datetime1, formatter);

        //Setting up BusinessUsers

        user = new BusinessUser();
        user.setId(1);
        user.setMail(USERNAME);
        user.setFirstName("Max");
        user.setLastName("Mustermann");

        user2 = new BusinessUser();
        user2.setId(2);
        user2.setMail("dingsda");
        user2.setFirstName("dings");
        user2.setLastName("da");

        //Setting up Locations
        location = new Location();
        location.setLongitude(10.00);
        location.setLatitude(10.00);

        //Setting up PreferenceSets
        preference = new Preference();
        preference.setValue("value");
        preference.setId(1);
        preferenceSet = new HashSet<Preference>();
        preferenceSet.add(preference);


        //Setting up the events
        event = new Event();
        event.setId(0);
        event.setName("TestEvent1");
        event.setDescription("Description 1");
        event.setRadius(1);
        event.setPreferences(preferenceSet);
        event.setLocation(location);
        event.setDatetime(time);
        event.setBusinessUserId(user);

        event2 = new Event();
        event2.setId(1);
        event2.setName("TestEvent2");
        event2.setDescription("Description 2");
        event2.setRadius(2);
        event2.setPreferences(preferenceSet);
        event2.setLocation(location);
        event2.setDatetime(time);
        event2.setBusinessUserId(user2);

        eventSet = new HashSet<Event>();
        eventSet.add(event);

        eventTO = new EventTO(event);
        eventTOSet = new HashSet<EventTO>();
        eventTOSet.add(eventTO);

        e = new ResourceNotFoundException("event not found");

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
    void testPostEvent() throws Exception {
        given(userService.getBusinessUser("test@test.de")).willReturn(user);
        given(eventService.createEvent(eventTO)).willReturn(eventTO);
        this.mvc.perform(post("/event")
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization",this.AUTH_HEADER)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(eventTO)))
                .andExpect(status().isCreated());
    }

    @Test
    void testUpdateEvent() throws Exception {
        given(userService.getBusinessUser("test@test.de")).willReturn(user);
        given(eventService.getEventById(1)).willReturn(event);
        given(eventService.updateEvent(1, eventTO)).willReturn(eventTO);
        this.mvc.perform(put("/event/1")
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization",this.AUTH_HEADER)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(eventTO)))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateEventFailure() throws Exception {
        given(userService.getBusinessUser("test@test.de")).willReturn(user);
        given(eventService.getEventById(1)).willReturn(event2);
        given(eventService.updateEvent(1, eventTO)).willReturn(eventTO);
        this.mvc.perform(put("/event/1")
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization",this.AUTH_HEADER)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(eventTO)))
                .andExpect(status().isForbidden());
    }

    @Test
    void testGetEventById() throws Exception {
        given(eventService.getEventTOById(0)).willReturn(eventTO);
        this.mvc.perform(get("/event/byId/?id=0")
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization",this.AUTH_HEADER))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(0))
                .andExpect(jsonPath("name").value("TestEvent1"));
    }

    @Test
    void testGetEventByIdFail() throws Exception {
        given(eventService.getEventTOById(5)).willThrow(e);
            this.mvc.perform(get("/event/byId?id=5")
                    .accept(MediaType.APPLICATION_JSON)
                    .header("Authorization",this.AUTH_HEADER))
                    .andExpect(status().isNotFound());

    }

    @Test
    void testGetAllEvents() throws Exception {
        given(eventService.getEvents()).willReturn(eventTOSet);
        this.mvc.perform(get("/event/all")
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization",this.AUTH_HEADER))
                .andExpect(status().isOk())
                .andExpect(jsonPath("*.id").value(0))
                .andExpect(jsonPath("*.name").value("TestEvent1"));

    }

    @Test
    void testGetEventByName() throws Exception {
        given(eventService.getEventByName("TestEvent1")).willReturn(eventTOSet);
        this.mvc.perform(get("/event/byName/?name=TestEvent1")
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization",this.AUTH_HEADER))
                .andExpect(status().isOk())
                .andExpect(jsonPath("*.id").value(0))
                .andExpect(jsonPath("*.name").value("TestEvent1"));
    }

    @Test
    void testGetEventByNameFail() throws Exception {
        given(eventService.getEventByName("diesdas")).willThrow(e);
        this.mvc.perform(get("/event/byName?name=diesdas")
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization",this.AUTH_HEADER))
                .andExpect(status().isNotFound());

    }

    @Test
    void testGetEventsByPreference() throws Exception {
        given(eventService.getAllEventsByPreference("value")).willReturn(eventTOSet);
        this.mvc.perform(get("/event/byPreference/?preference=value")
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization",this.AUTH_HEADER))
                .andExpect(status().isOk())
                .andExpect(jsonPath("*.id").value(0))
                .andExpect(jsonPath("*.name").value("TestEvent1"));
    }

    @Test
    void testGetEventsByPreferenceFail() throws Exception {
        given(eventService.getAllEventsByPreference("diesdas")).willThrow(e);
        this.mvc.perform(get("/event/byPreference?preference=diesdas")
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization",this.AUTH_HEADER))
                .andExpect(status().isNotFound());

    }

    @Test
    void testNotAuthorized() throws Exception {
        this.mvc.perform(get("/event/byUser")
                .accept(MediaType.APPLICATION_JSON)
                .header("fail", "willFail"))
                .andExpect(status().is4xxClientError());
    }


    @Test
    void testGetEventsByUser() throws Exception {
        given(eventService.getAllEventsByUser("test@test.de")).willReturn(eventTOSet);
        this.mvc.perform(get("/event/byUser")
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization",this.AUTH_HEADER))
                .andExpect(status().isOk())
                .andExpect(jsonPath("*.id").value(0))
                .andExpect(jsonPath("*.name").value("TestEvent1"));
    }

    @Test
    void testDeleteEvent() throws Exception {
        given(userService.getBusinessUser("test@test.de")).willReturn(user);
        given(eventService.getEventById(1)).willReturn(event);
        this.mvc.perform(delete("/event/1")
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization",this.AUTH_HEADER))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteEventFail() throws Exception {
        given(userService.getBusinessUser("test@test.de")).willReturn(user);
        given(eventService.getEventById(2)).willReturn(event2);
        this.mvc.perform(delete("/event/2")
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization",this.AUTH_HEADER))
                .andExpect(status().isForbidden());
    }


}
