package webshop.service;

import jakarta.transaction.Transactional;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import webshop.catalog.command.domain.product.Image;
import webshop.storage.GoogleCloudStorage;
import webshop.storage.StorageType;

import java.net.HttpURLConnection;
import java.net.URL;

import static com.jayway.jsonpath.internal.path.PathCompiler.fail;
import static org.junit.Assert.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("/appConfig.xml")
@Transactional
public class StorageTest {
    @Autowired
    GoogleCloudStorage googleCloudStorage;

    Image image;

    @BeforeEach
    void setUp() {
        image = new Image("src/main/testIcon.png");
    }



    @Test
    public void CloudUploadTest() throws Exception{

        String extension = "";

        int i = image.getPath().lastIndexOf('.');
        if (i > 0) {
            extension = image.getPath().substring(i+1);
        }

        googleCloudStorage.uploadObject(image.getUploadTime().toString(),extension,image.getPath());

        try{
            URL url = new URL(image.getUrl());
            System.out.println(image.getUrl());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");
            int responseCode = connection.getResponseCode();

            // HTTP 상태 코드 200은 성공적인 응답을 의미합니다.
            assertEquals(HttpURLConnection.HTTP_OK, responseCode);

        }catch(Exception e){
            fail("Can't access url" + e.getMessage());
        }
    }

    @Test
    public void DownloadTest() throws Exception{
        googleCloudStorage.downloadObject("2024-01-30T19:47:55.594697800.png","./src/download/");
    }

}
