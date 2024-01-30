package webshop.storage;

import com.google.cloud.storage.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@NoArgsConstructor
@Service
public class GoogleCloudStorage {
    String bucketName = "backendprj-bucket-1";
    String projectId = "analog-arbor-376803";
    Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();

    public void uploadImage(String objectName, String extension,String filePath) throws IOException {

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


