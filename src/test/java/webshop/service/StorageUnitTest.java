package webshop.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import webshop.common.model.Image;
import webshop.catalog.command.domain.product.ImageRepository;
import webshop.storage.CloudStorage;
import webshop.storage.StorageService;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration("/appConfig.xml")
@Transactional
public class StorageUnitTest {
    @Mock
    private CloudStorage cloudStorage;
    @InjectMocks
    private StorageService storageService = new StorageService(cloudStorage);

    @Mock
    private ImageRepository imageRepository;
    Image image;



    @Test
    public void uploadImageTest() throws Exception{
        // MockMultipartFile 생성
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "filename.txt",
                "text/plain",
                "some content".getBytes()
        );

        // 메소드 호출
        String imagePath = storageService.uploadImageToCloud(file);

        // 목 객체의 메소드 호출 검증
        verify(cloudStorage, times(1)).uploadObject(anyString(), anyString());
    }



}

