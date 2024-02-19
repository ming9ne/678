package com.sw678.crud.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Controller
@RequestMapping("/map")
public class MapController {

    @GetMapping("/geojson")
    public ResponseEntity<String> getGeoJson() throws IOException {

        ClassPathResource resource = new ClassPathResource("geojson.json");

        String content = new String(Files.readAllBytes(resource.getFile().toPath()));
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(content);
    }
}
