package com.app.bluecotton.api;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/file")
public class FileReadApi {

    private static final String BASE_DIR = "C:/bluecottonimage";


    @GetMapping("/{type}/{fileName:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serve(
            @PathVariable String type,
            @PathVariable String fileName
    ) throws Exception {

        String decoded = UriUtils.decode(fileName, StandardCharsets.UTF_8);

        Path file = Paths.get(BASE_DIR, type, decoded);

        if (!Files.exists(file)) return ResponseEntity.notFound().build();

        String contentType = Files.probeContentType(file);
        if (contentType == null) contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;

        Resource resource = new UrlResource(file.toUri());
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);
    }
}
