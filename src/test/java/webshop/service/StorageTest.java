package webshop.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import webshop.catalog.command.domain.product.Image;
import webshop.storage.GoogleCloudStorage;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("/appConfig.xml")
@Transactional
public class StorageTest {
    @Autowired
    GoogleCloudStorage googleCloudStorage;

    @Test
    public void simpleTest() throws Exception {
        Image image = new Image("src/main/testIcon.png");
        String extension = "";

        int i = image.getPath().lastIndexOf('.');
        if (i > 0) {
            extension = image.getPath().substring(i+1);
        }

        googleCloudStorage.uploadImage(image.getUploadTime().toString(),extension,image.getPath());
    }

    @Test
    public void DownloadTest() throws Exception{
        googleCloudStorage.downloadObject("2024-01-30T19:47:55.594697800.png","./src/download/");
    }
}
