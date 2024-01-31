package webshop.storage;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

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

    public void downloadObject(String objectName, String destFilePath) throws IOException {
        cloudStorage.downloadObject(objectName,destFilePath);

    }
}
