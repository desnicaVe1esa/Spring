package ru.ma1gus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Translator {
    public static void main(String[] args) {
        System.out.println("Введи суда свою хуйню: ");
        Scanner scanner = new Scanner(System.in);
        String sentenceToTranslate = scanner.nextLine();

        RestTemplate restTemplate = new RestTemplate();
        String url = "https://translate.api.cloud.yandex.net/translate/v2/translate";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer" + "Токен, который выдает Яндекс");

        Map<String, String> jsonData = new HashMap<>();
        jsonData.put("folderId", "ID папки, который выдает Яндекс");
        jsonData.put("targeLanguageCode", "en");
        jsonData.put("texts", "[" + sentenceToTranslate + "]");

        HttpEntity<Map<String, String>> request = new HttpEntity<>(jsonData, headers);

        String response = restTemplate.patchForObject(url, request, String.class);
        System.out.println(response);

        // Парсим полученный JSON с помощью Jackson
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode obj = objectMapper.readTree(response);
            System.out.println("Перевод: " + obj.get("translations").get(0).get("text"));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        // Второй вариант
        YandexResponse yandexResponse = restTemplate.postForObject(url, request, YandexResponse.class);
        System.out.println("Перевод: " + yandexResponse.getTranslations().get(0).getText());
    }
}
