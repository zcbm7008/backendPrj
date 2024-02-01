package webshop.catalog.command.domain.product;

import jakarta.persistence.*;
import lombok.Getter;
import webshop.storage.StorageType;

import java.time.LocalDateTime;

@Entity
@Table(name = "IMAGE")
@Getter
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IMAGE_ID")
    private Long id;

    @Column(name = "image_path")
    private String path;

    @Column(name = "upload_time")
    private LocalDateTime uploadTime;

    StorageType storageType;

    protected Image() {

    }

    public Image(String path){
        this.path = path;
        this.uploadTime = LocalDateTime.now();
    }

    public String getUrl() {

        String storageUrl = "https://storage.googleapis.com/";
        String bucketName = System.getenv("LOCAL_BUCKET_NAME");

        System.out.println(bucketName);

        return storageUrl + bucketName + "/" + getUploadTime().toString() + "." + getExtension();


    }

    public String getExtension() {
        String extension = "";

        int i = getPath().lastIndexOf('.');
        if (i > 0) {
            extension = getPath().substring(i+1);
        }
        return extension;
    }

}
