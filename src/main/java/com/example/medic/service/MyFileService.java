package com.example.medic.service;

import com.example.medic.entity.MyFile;
import com.example.medic.payload.Result;
import com.example.medic.repository.MyFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MyFileService {

    private final MyFileRepository repository;
    Result result=new Result();

    @Value("${upload}")
    private String downloadPath;


    public Result save(MultipartFile multipartFile) {

        MyFile myFile = new MyFile();

        myFile.setContentType(multipartFile.getContentType());
        myFile.setFileSize(multipartFile.getSize());
        myFile.setName(multipartFile.getOriginalFilename());
        myFile.setExtension(getExtension(myFile.getName()).toLowerCase());
        myFile.setHashId(UUID.randomUUID().toString());

        LocalDate date = LocalDate.now();

        // change value downloadPath
        String localPath = downloadPath + String.format(
                "/%d/%d/%d/%s",
                date.getYear(),
                date.getMonthValue(),
                date.getDayOfMonth(),
                myFile.getExtension().toLowerCase());

        myFile.setUploadPath(localPath);


        File file = new File(localPath);
        file.mkdirs();

        repository.save(myFile);

        try {
            multipartFile.transferTo(new File(file.getAbsolutePath() + "/" + String.format("%s.%s", myFile.getHashId(), myFile.getExtension())));

            Map<Object, Object> data = new HashMap<>();
            data.put("hashId", myFile.getHashId());
            return result.save(data);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.error();
    }


    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }


    public MyFile findByHashId(String hashId) {
        return repository.findByHashId(hashId);
    }


    public Result delete(String hashId) {
        MyFile myFile = findByHashId(hashId);

        File file = new File(String.format("%s/%s.%s", myFile.getUploadPath(), myFile.getHashId(), myFile.getExtension()));

        if (file.delete() && repository.deleteByHashId(hashId)) {
            return result.delete();
        }
        return result.error();
    }

}



