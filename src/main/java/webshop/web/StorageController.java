package webshop.web;

import com.google.cloud.storage.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import webshop.storage.GoogleCloudStorage;
import webshop.storage.StorageService;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
public class StorageController {
    @Autowired
    GoogleCloudStorage googleCloudStorage = new GoogleCloudStorage();
    StorageService storageService = new StorageService(googleCloudStorage);

    @PostMapping("/upload")
    public String uploadFileToCloud(@RequestParam("files") MultipartFile file) throws IOException{

        storageService.uploadFileToCloud(file);
        return "File uploaded : " + file.getOriginalFilename();
    }
}
