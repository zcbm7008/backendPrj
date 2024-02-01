package webshop.storage;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import webshop.catalog.command.domain.product.Image;
import webshop.catalog.command.domain.product.ImageRepository;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Setter
@Service
public class StorageService {

    @Autowired
    CloudStorage cloudStorage;

    public StorageService(CloudStorage cloudStorage){
        this.cloudStorage = cloudStorage;
    }

   public void uploadObject(String objectName, String extension,String filePath) throws IOException {
        cloudStorage.uploadObject(objectName,extension,filePath);
   }

    public void uploadFileToCloud(MultipartFile file) throws IOException{
        if (file.isEmpty()) {
            throw new IOException("File not Found");
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

        // 업로드 서비스 호출
        cloudStorage.uploadObject(newFileName, extension, tempFile.getAbsolutePath());
    }

    public String uploadImageToCloud(MultipartFile file) throws IOException{
        if (file.isEmpty()) {
            throw new IOException("Empty file cannot be uploaded");
        }

        String originalFileName = file.getOriginalFilename();
        String extension = StringUtils.getFilenameExtension(originalFileName);
        String newFileName = UUID.randomUUID().toString(); // 고유한 파일 이름 생성

        if (extension != null) {
            newFileName += "." + extension;
        }

        File tempFile = null;

        try{
            // 임시 파일로 저장
            tempFile = new File(System.getProperty("java.io.tmpdir"), newFileName);
            file.transferTo(tempFile);

            System.out.println(tempFile.getAbsolutePath());


            // 업로드 서비스 호출
            cloudStorage.uploadObject(newFileName, extension, tempFile.getAbsolutePath());

            return newFileName;
            
        } finally {
//            if(tempFile != null && tempFile.exists()){
//                tempFile.delete();
//            }
        }

    }

    public void downloadObject(String objectName, String destFilePath) throws IOException {
        cloudStorage.downloadObject(objectName,destFilePath);

    }

    public String getObjectName(String objectName){
        return cloudStorage.getObjectName(objectName);
    }
}
