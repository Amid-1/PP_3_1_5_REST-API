package ru.kata.spring.rest;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.kata.spring.rest.entity.User;

import java.util.List;

@Component
public class Communication {

    private final RestTemplate restTemplate;
    private final String URL = "http://94.198.50.185:7081/api/users";
    private String sessionId;

    public Communication(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String sequence() {

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<List<User>> response = restTemplate.exchange(
                URL, HttpMethod.GET, entity,
                new ParameterizedTypeReference<>() {}
        );

        List<String> cookies = response.getHeaders().get("Set-Cookie");
        if (cookies != null) {
            sessionId = cookies.get(0).split(";")[0];
        }


        headers.set("Cookie", sessionId);

        User user = new User(3L, "James", "Brown", (byte) 25);
        HttpEntity<User> postEntity = new HttpEntity<>(user, headers);
        ResponseEntity<String> postResponse = restTemplate.exchange(URL, HttpMethod.POST, postEntity, String.class);
        String code1 = postResponse.getBody();

        user.setName("Thomas");
        user.setLastName("Shelby");
        HttpEntity<User> putEntity = new HttpEntity<>(user, headers);
        ResponseEntity<String> putResponse = restTemplate.exchange(URL, HttpMethod.PUT, putEntity, String.class);
        String code2 = putResponse.getBody();

        HttpEntity<String> deleteEntity = new HttpEntity<>(headers);
        ResponseEntity<String> deleteResponse = restTemplate.exchange(URL + "/3", HttpMethod.DELETE, deleteEntity, String.class);
        String code3 = deleteResponse.getBody();

        return code1 + code2 + code3;
    }
}