package com.app.bluecotton.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class ImageServiceLocal {

    private final SomImageService somImageService;

    public Map<String, String> uploadToLocal(MultipartFile file, String folder) throws Exception {

        // 📌 기본 저장 경로: C:/bluecotton-uploads
        String baseDir = "C:/bluecotton-uploads";

        // 📌 folder 파라미터 그대로 유지
        // 예: som/2025/11/10
        String saveDirPath = baseDir + "/" + folder;

        // 📌 폴더 생성
        File saveDir = new File(saveDirPath);
        if (!saveDir.exists()) {
            boolean created = saveDir.mkdirs();
            log.info("폴더 생성: {} => {}", saveDirPath, created);
        }

        // 📌 저장할 파일명
        String originalName = file.getOriginalFilename();
        String filePath = saveDirPath + "/" + originalName;

        // 📌 실제 파일 저장
        File dest = new File(filePath);
        file.transferTo(dest);

        log.info("로컬 저장 완료: {}", filePath);

        // 📌 응답 JSON 구성
        Map<String, String> result = new HashMap<>();
        result.put("url", filePath);               // 절대 경로 그대로
        result.put("fileName", originalName);
        result.put("folder", folder);

        return result;
    }
}
