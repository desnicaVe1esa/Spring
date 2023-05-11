package ru.ma1gus;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class Consumer {
    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();

        Map<String, String> jsonToSend = new HashMap<>();
        jsonToSend.put("name", "Биба");
        jsonToSend.put("job", "Говно");

        HttpEntity<Map<String, String>> request = new HttpEntity<>(jsonToSend);

        String url = "https://reqres.in/api/users/";
        String response = restTemplate.postForObject(url, request, String.class);

        System.out.println(response);
    }
}
