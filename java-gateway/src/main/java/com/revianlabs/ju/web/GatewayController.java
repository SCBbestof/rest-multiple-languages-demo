package com.revianlabs.ju.web;

import com.revianlabs.ju.config.YamlConfig;
import com.revianlabs.ju.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/gateway")
public class GatewayController {

    private final YamlConfig yamlConfig;

    @Autowired
    public GatewayController(YamlConfig yamlConfig) {
        this.yamlConfig = yamlConfig;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/simple/{service}", produces = "application/text")
    public ResponseEntity<Object> forwardSimpleRequest(@PathVariable String service, @RequestParam String parameter) {
        String serviceUrl = yamlConfig.getServices().get(service);

        if (serviceUrl == null || serviceUrl.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        RestTemplate restTemplate = new RestTemplate();
        Object responseBody = restTemplate.postForObject(serviceUrl + "/simple", parameter, String.class);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/complex/{service}",
            consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> forwardComplexRequest(@PathVariable String service, @RequestBody Article article) {
        String serviceUrl = yamlConfig.getServices().get(service);

        if (serviceUrl == null || serviceUrl.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        RestTemplate restTemplate = new RestTemplate();
        Object responseBody = restTemplate.postForObject(serviceUrl + "/complex", article, Article.class);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/write/{service}", consumes = "application/json")
    public ResponseEntity<Object> WriteToFile(@PathVariable String service, @RequestBody Article article) {
        String jprocessorUrl = yamlConfig.getServices().get("jprocessor") + "/forward/" + service;
        RestTemplate restTemplate = new RestTemplate();
        Object responseBody = restTemplate.postForObject(jprocessorUrl, article, String.class);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
}
