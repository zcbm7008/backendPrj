package webshop.storage;

import com.google.cloud.storage.*;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@NoArgsConstructor
@Component
public class GoogleCloudStorage implements CloudStorage {
    String bucketName = System.getenv("LOCAL_BUCKET_NAME");
    String projectId = System.getenv("LOCAL_PROJECT_ID");
    Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();

    public void uploadObject(String objectName, String extension,String filePath) throws IOException {

        objectName = objectName + "." +extension;

        BlobId blobId = BlobId.of(bucketName, objectName);

        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("image/" + extension).build();

        Storage.BlobWriteOption precondition;
        if (storage.get(bucketName, objectName) == null) {

            precondition = Storage.BlobWriteOption.doesNotExist();
        } else {

            precondition =
                    Storage.BlobWriteOption.generationMatch(
                            storage.get(bucketName, objectName).getGeneration());
        }
        storage.createFrom(blobInfo, Paths.get(filePath), precondition);

        System.out.println(
                "File " + filePath + " uploaded to bucket " + bucketName + " as " + objectName);

    }

    public void downloadObject(String objectName, String destFilePath){
        Blob blob = storage.get(BlobId.of(bucketName, objectName));

        String safeObjectName = objectName.replace(":", "-");
        Path destinationPath = Paths.get(destFilePath, safeObjectName);

        try {
            Files.createDirectories(destinationPath.getParent());
            blob.downloadTo(destinationPath);

            System.out.println("Downloaded object " + objectName + " from bucket name " + bucketName + " to " + destinationPath);
        } catch (IOException e) {
            e.printStackTrace(); // Or handle more gracefully
        }


}
}


