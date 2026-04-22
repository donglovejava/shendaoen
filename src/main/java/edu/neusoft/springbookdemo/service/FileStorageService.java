package edu.neusoft.springbookdemo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
@Service
public class FileStorageService {

    @Value("${app.upload.dir}")
    private String uploadDir;

    /**
     * 保存文件到 uploads/{subDir}/，返回相对路径（用于入库 + 前端拼 /files/）。
     */
    public String save(MultipartFile file, String subDir) {
        if (file == null || file.isEmpty()) return null;
        String original = file.getOriginalFilename() == null ? "file" : file.getOriginalFilename();
        String ext = "";
        int dot = original.lastIndexOf('.');
        if (dot >= 0) ext = original.substring(dot);
        String newName = UUID.randomUUID().toString().replace("-", "") + ext;

        Path dir = Paths.get(uploadDir, subDir).toAbsolutePath();
        try {
            Files.createDirectories(dir);
            Path dst = dir.resolve(newName);
            file.transferTo(dst.toFile());
            String rel = subDir + "/" + newName;
            log.info("文件已保存: {} -> {}", original, rel);
            return rel;
        } catch (IOException e) {
            throw new RuntimeException("文件保存失败: " + e.getMessage(), e);
        }
    }
}
