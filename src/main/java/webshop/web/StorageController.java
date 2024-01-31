package webshop.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import webshop.catalog.command.domain.product.Image;
import webshop.storage.GoogleCloudStorage;
import webshop.storage.StorageService;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
public class StorageController {
    @Autowired
    GoogleCloudStorage googleCloudStorage = new GoogleCloudStorage();

    @Autowired
    StorageService storageService = new StorageService(googleCloudStorage);


    @PostMapping("/upload")
    public String saveFile(@RequestParam("files") MultipartFile file) throws IOException{
        if (file.isEmpty()) {
            return "Failed to upload empty file";
        }

        String originalFileName = file.getOriginalFilename();
        String extension = StringUtils.getFilenameExtension(originalFileName);
        String newFileName = UUID.randomUUID().toString(); // 고유한 파일 이름 생성
        if (extension != null) {
            newFileName += "." + extension;
        }

        // 임시 파일로 저장
        File tempFile = new File(System.getProperty("java.io.tmpdir"), newFileName);
        file.transferTo(tempFile);

        System.out.println(tempFile.getAbsolutePath());
        // 업로드 서비스 호출
        googleCloudStorage.uploadObject(newFileName, extension, tempFile.getAbsolutePath());

        return "File uploaded : " + originalFileName;

    }
}
