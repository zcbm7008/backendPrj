package webshop.storage;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@Setter
@Service
public class StorageService {

    @Autowired
    CloudStorage cloudStorage;

    private static String tmpdir = System.getProperty("java.io.tmpdir");

    public StorageService(CloudStorage cloudStorage){
        this.cloudStorage = cloudStorage;
    }

   public void uploadObject(String objectName, String filePath) throws IOException {
        cloudStorage.uploadObject(objectName,filePath);
   }

    public String uploadFileToCloud(MultipartFile file) throws IOException{
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
        cloudStorage.uploadObject(newFileName, tempFile.getAbsolutePath());

        return newFileName;
    }

    public String createUploadFileName(MultipartFile file) {
        String originalFileName = file.getOriginalFilename();
        String extension = StringUtils.getFilenameExtension(originalFileName);
        String newFileName = UUID.randomUUID().toString(); // 고유한 파일 이름 생성

        if (extension != null) {
            newFileName += "." + extension;
        }

        return newFileName;

    }
    private Path constructTempFilePath(String fileName) {
        return Paths.get(tmpdir, fileName);
    }

    public String uploadImageToCloud(MultipartFile file) throws IOException{

        if (file.isEmpty()) {
            throw new IOException("Empty file cannot be uploaded");
        }

        String newFileName = createUploadFileName(file);

        Optional<URL> signedURL = generatePutObjectSignedUrl(newFileName);
        URL url = signedURL.orElseThrow(() -> new IOException("Signed URL cannot be created for " + newFileName));

        try(TempFile tempFile = new TempFile(constructTempFilePath(newFileName))){
            // 임시 파일로 저장
            file.transferTo(tempFile.getFile());
            // 업로드 서비스 호출
            cloudStorage.uploadObject(newFileName, tempFile.getFile().getAbsolutePath());

            System.out.println(tempFile.getFile().getAbsolutePath());

            return newFileName;
        }
    }

    public Optional<URL> generatePutObjectSignedUrl(String objectName) {

        URL signedUrl = cloudStorage.generatePutObjectSignedUrl(objectName);

        if (signedUrl != null) {
            return Optional.of(signedUrl);
        }
        else {
            return Optional.empty();
        }
    }

    public void downloadObject(String objectName, String destFilePath) throws IOException {
        cloudStorage.downloadObject(objectName,destFilePath);

    }

    public String getObjectName(String objectName){
        return cloudStorage.getObjectName(objectName);
    }
}
