package webshop.storage;

import com.google.cloud.storage.StorageException;

import java.io.IOException;
import java.net.URL;

public interface CloudStorage {

    public void uploadObject(String objectName, String extension,String filePath) throws IOException;

    public void downloadObject(String objectName, String destFilePath) throws IOException;

    public String getObjectName(String objectName) throws StorageException;

    public URL generatePutObjectSignedUrl(String objectName) throws StorageException;
}
