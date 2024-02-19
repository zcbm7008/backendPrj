package webshop.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;
import webshop.common.model.Image;
import webshop.catalog.command.domain.product.ImageRepository;
import webshop.storage.GoogleCloudStorage;
import webshop.storage.StorageService;

import javax.inject.Inject;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static com.jayway.jsonpath.internal.path.PathCompiler.fail;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("/appConfig.xml")
@Transactional
public class StorageIntegrationTest {
    @Autowired
    GoogleCloudStorage googleCloudStorage = new GoogleCloudStorage();

    StorageService storageService = new StorageService(googleCloudStorage);

    @Autowired
    ImageRepository imageRepository;
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

        googleCloudStorage.uploadObject(image.getUploadTime().toString(), image.getPath());

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
    public void shouldSaveUploadedImage() throws Exception{
        //Given
        MultipartFile multipartFile = createMultipartFileFromFilePath("D:\\[My Project]\\backendPrj\\backendPrj\\src\\download.png");
        String imagePath = storageService.uploadImageToCloud(multipartFile);
        Image newImage = new Image(imagePath);
        imageRepository.save(newImage);

        //When
        Image foundImage = imageRepository.findById(newImage.getId()).orElseThrow(IOException::new);

        //Then
        assertEquals("Uploaded image path should match",foundImage.getPath(),storageService.getObjectName(foundImage.getPath()));

    }



    @Test
    public void DownloadTest() throws Exception{
        googleCloudStorage.downloadObject("2024-01-30T19:47:55.594697800.png","./src/download/");
    }


    public MultipartFile createMultipartFileFromFilePath(String filePath) {
        try {
            Path path = Paths.get(filePath);
            String fileName = path.getFileName().toString();
            byte[] content = Files.readAllBytes(path);
            MultipartFile multipartFile = new MockMultipartFile(fileName, fileName, "image/png", content);
            return multipartFile;
        } catch (IOException e) {
            // 예외 처리 로직
            e.printStackTrace();
            return null;
        }

    }


}
