package webshop.storage;

import java.io.IOException;

public interface CloudStorage {

    public void uploadObject(String objectName, String extension,String filePath) throws IOException;

    public void downloadObject(String objectName, String destFilePath) throws IOException;
}
