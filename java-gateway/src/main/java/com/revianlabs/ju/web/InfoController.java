package com.revianlabs.ju.web;

import com.revianlabs.ju.config.YamlConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/info")
public class InfoController {

    private final YamlConfig yamlConfig;

    @Autowired
    public InfoController(YamlConfig yamlConfig) {
        this.yamlConfig = yamlConfig;
    }

    @RequestMapping("/services")
    public String getServicesInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("Services Information")
                .append(System.lineSeparator());
        yamlConfig.getServices().values().forEach(service -> sb.append(System.lineSeparator()).append(service));
        return sb.toString();
    }

    @RequestMapping("/system")
    public String getSystemInfo() {
        StringBuilder sb = new StringBuilder();
        return sb.append("System Information")
                .append(System.lineSeparator())
                .append(System.lineSeparator()).append("Available processors (cores): ").append(Runtime.getRuntime().availableProcessors())
                .append(System.lineSeparator()).append("JVM Free memory (bytes): ").append(Runtime.getRuntime().freeMemory())
                .append(System.lineSeparator()).append("JVM Total memory (bytes): ").append(Runtime.getRuntime().totalMemory())
                .toString();
    }

    @RequestMapping("/phoenixer")
    public ResponseEntity<String> getPhoenixerHeartbeat() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForEntity(yamlConfig.getServices().get("phoenixer"), String.class);
    }
}
