package webshop.storage;

import com.google.cloud.storage.StorageException;

import java.io.IOException;

public interface CloudStorage {

    public void uploadObject(String objectName, String extension,String filePath) throws IOException;

    public void downloadObject(String objectName, String destFilePath) throws IOException;

    public String getObjectName(String objectName) throws StorageException;
}
