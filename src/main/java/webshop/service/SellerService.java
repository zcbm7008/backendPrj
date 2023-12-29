package webshop.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webshop.domain.Member;
import webshop.domain.Order;
import webshop.domain.OrderItem;
import webshop.domain.Seller;
import webshop.domain.item.Item;
import webshop.repository.MemberRepository;
import webshop.repository.SellerRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class SellerService {

    @Autowired
    SellerRepository sellerRepository;
    @Autowired
    MemberRepository memberRepository;

    public Long seller(Long memberId, Seller seller) {
        validateDuplicateSeller(seller);
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("Member not found with id: " + memberId));;

            Seller.createSeller(findMember, seller.getName());
            sellerRepository.save(seller);
            return seller.getId();

    }

    private void validateDuplicateSeller(Seller seller) {
        List<Seller> findSeller =
                sellerRepository.findByName(seller.getName());
        if(!findSeller.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }


}
