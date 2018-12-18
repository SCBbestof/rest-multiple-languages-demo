package com.revianlabs.ju.web;

import com.revianlabs.ju.config.YamlConfig;
import com.revianlabs.ju.model.Article;
import com.revianlabs.ju.model.ServiceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class ProcessingController {

    private final YamlConfig yamlConfig;

    @Autowired
    public ProcessingController(YamlConfig yamlConfig) {
        this.yamlConfig = yamlConfig;
    }

    @RequestMapping("/simple")
    public String simpleRequest() {
        return "Hi from jprocessor!";
    }

    @RequestMapping("/complex")
    public ResponseEntity<Article> complexRequest(@RequestBody Article article) {
        article.setTitle(article.getTitle().toUpperCase());
        return new ResponseEntity<>(article, HttpStatus.OK);
    }

    @RequestMapping("/forward/{service}")
    public ResponseEntity forwardingRequest(@PathVariable ServiceType service, @RequestBody Object request) {
        if (!service.equals(ServiceType.GOWORKER) && !service.equals(ServiceType.PYESSER)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Object response = forwardRequest(yamlConfig.getServices().get(service.toString().toLowerCase()), request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private Object forwardRequest(String serviceUrl, Object request) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(serviceUrl, request, String.class);
    }
}
