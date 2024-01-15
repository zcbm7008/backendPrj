package webshop.catalog.query.product;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webshop.catalog.command.domain.product.Item;
import webshop.catalog.command.domain.product.ItemRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ItemService {

    @Autowired
    ItemRepository itemRepository;

    public void saveItem(Item item){
        itemRepository.save(item);
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Optional<Item> findOne(Long itemId){
        return itemRepository.findById(itemId);
    }
}
