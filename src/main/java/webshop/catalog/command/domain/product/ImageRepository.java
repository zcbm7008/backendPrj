package webshop.catalog.command.domain.product;

import org.springframework.data.jpa.repository.JpaRepository;
import webshop.common.model.Image;

public interface ImageRepository extends JpaRepository<Image,Long> {
}
