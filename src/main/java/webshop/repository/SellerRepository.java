package webshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import webshop.user.domain.seller.Seller;
import webshop.user.query.seller.SellerDTO;

import java.util.List;

public interface SellerRepository extends JpaRepository<Seller, Long> {

    List<Seller> findByName(String name);

    @Query("SELECT new webshop.user.query.seller.SellerDTO(s.id, s.name) FROM Seller s JOIN s.member m WHERE m.name = :memberName")
    List<SellerDTO> findSellersWithMemberName(@Param("memberName") String MemberName);
}
