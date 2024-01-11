package webshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import webshop.domain.Review;

public interface ReviewRepository extends JpaRepository<Review,Long> {
}
