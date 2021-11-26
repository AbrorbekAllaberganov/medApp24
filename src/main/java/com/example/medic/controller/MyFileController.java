package com.example.medic.controller;


import com.example.medic.entity.MyFile;
import com.example.medic.payload.Result;
import com.example.medic.service.MyFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileUrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.net.URLEncoder;

@RestController
@RequestMapping("/api/admin/file")
@RequiredArgsConstructor
@CrossOrigin(maxAge = 36000000)
public class MyFileController {
    @Value("${upload}")
    private String uploadFolder;

    private final MyFileService myFileService;

    @PostMapping("/save")
    public ResponseEntity<Result> saveFile(@RequestParam(name = "file") MultipartFile multipartFile) {
        Result result= myFileService.save(multipartFile);
        return ResponseEntity.status(result.isSuccess()?200:409).body(result);
    }

    @GetMapping("/preview/{hashId}")
    public ResponseEntity<?> preview(@PathVariable String hashId) throws MalformedURLException {
        MyFile myFile = myFileService.findByHashId(hashId);
        return ResponseEntity.ok()
                .header(HttpHeaders.EXPIRES, "inline; fileName=" + URLEncoder.encode(myFile.getName()))
                .contentType(MediaType.parseMediaType(myFile.getContentType()))
                .body(new FileUrlResource(String.format("%s/%s.%s", myFile.getUploadPath(), myFile.getHashId(), myFile.getExtension())));
    }

    @GetMapping("/download/{hashId}")
    public ResponseEntity<?> download(@PathVariable String hashId) throws MalformedURLException {
        MyFile myFile = myFileService.findByHashId(hashId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=" + URLEncoder.encode(myFile.getName()))
                .contentType(MediaType.parseMediaType(myFile.getContentType()))
                .body(new FileUrlResource(String.format("%s/%s.%s", myFile.getUploadPath(), myFile.getHashId(), myFile.getExtension())));
    }

    @DeleteMapping("/delete/{hashId}")
    public ResponseEntity<Result> delete(@PathVariable String hashId){
        Result result= myFileService.delete(hashId);
        return ResponseEntity.status(result.isSuccess()?200:409).body(result);
    }


}
