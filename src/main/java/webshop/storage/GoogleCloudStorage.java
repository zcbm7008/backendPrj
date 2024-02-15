package webshop.storage;

import com.google.cloud.storage.*;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@NoArgsConstructor
@Component
public class GoogleCloudStorage implements CloudStorage {
    static String bucketName = System.getenv("LOCAL_BUCKET_NAME");
    static String projectId = System.getenv("LOCAL_PROJECT_ID");
    static Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();

    public void uploadObject(String objectName, String extension,String filePath) throws IOException {

        System.out.println(bucketName);
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

    public String getObjectName(String objectName) throws StorageException {
        Blob blob =
                storage.get(bucketName, objectName, Storage.BlobGetOption.fields(Storage.BlobField.values()));

        return blob.getName();

    }


        /**
         * explicitly using the Storage.SignUrlOption.signWith(ServiceAccountSigner) option. If you don't,
         * you could also pass a service account signer to StorageOptions, i.e.
         * StorageOptions().newBuilder().setCredentials(ServiceAccountSignerCredentials). In this example,
         * neither of these options are used, which means the following code only works when the
         * credentials are defined via the environment variable GOOGLE_APPLICATION_CREDENTIALS, and those
         * credentials are authorized to sign a URL. See the documentation for Storage.signUrl for more
         * details.
         */
        public URL generatePutObjectSignedUrl(String objectName) throws StorageException {
            // Define Resource
            BlobInfo blobInfo = BlobInfo.newBuilder(BlobId.of(bucketName, objectName)).build();

            // Generate Signed URL
            Map<String, String> extensionHeaders = new HashMap<>();
            extensionHeaders.put("Content-Type", "application/octet-stream");

            URL url =
                    storage.signUrl(
                            blobInfo,
                            15,
                            TimeUnit.MINUTES,
                            Storage.SignUrlOption.httpMethod(HttpMethod.PUT),
                            Storage.SignUrlOption.withExtHeaders(extensionHeaders),
                            Storage.SignUrlOption.withV4Signature());

            System.out.println("Generated PUT signed URL:");
            System.out.println(url);

            return url;
        }
    }



