package webshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import webshop.domain.Member;
import webshop.domain.Seller;

import java.util.List;

public interface SellerRepository extends JpaRepository<Seller, Long> {

    List<Seller> findByName(String name);
}
