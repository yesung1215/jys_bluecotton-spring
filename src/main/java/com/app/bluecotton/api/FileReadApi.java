package com.app.bluecotton.api;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/file")
public class FileReadApi {

    private static final Path BASE_DIR = Paths.get("C:/bluecottonimage");

    @GetMapping("/{type}/{fileName:.+}")
    public ResponseEntity<Resource> serve(
            @PathVariable String type,
            @PathVariable String fileName
    ) throws Exception {

        // URL 인코딩된 파일명을 UTF-8로 디코딩 (한글 파일명 대응)
        String decodedName = UriUtils.decode(fileName, StandardCharsets.UTF_8);

        // 실제 파일 경로: C:/bluecottonimage/{type}/{fileName}
        Path file = BASE_DIR.resolve(type).resolve(decodedName);

        // 파일 없으면 404에러
        if (!Files.exists(file)) {
            return ResponseEntity.notFound().build();
        }
        Resource resource = new UrlResource(file.toUri());

        return ResponseEntity.ok(resource);
    }
}
