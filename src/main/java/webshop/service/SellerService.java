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
    @Autowired
    ItemService itemService;

    public Long join(Seller seller) {

        validateDuplicateSeller(seller);
        sellerRepository.save(seller);
        return seller.getId();
    }

    public Seller addItemToSeller(Long sellerId, Long itemId) {

        Seller findSeller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new NoSuchElementException("Seller not found with id: " + sellerId));

        Item findItem = itemService.findOne(itemId)
                .orElseThrow(() -> new NoSuchElementException("Item not found with id: " + itemId));


        findSeller.addSellerItem(findItem);
        return findSeller;

    }

    private void validateDuplicateSeller(Seller seller) {
        List<Seller> findSeller =
                sellerRepository.findByName(seller.getName());
        if(!findSeller.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 판매자 회원입니다.");
        }
    }


}
