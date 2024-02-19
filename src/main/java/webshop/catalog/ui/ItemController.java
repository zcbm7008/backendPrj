package webshop.catalog.ui;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import webshop.catalog.command.domain.product.Artwork;
import webshop.catalog.command.domain.product.Item;
import webshop.catalog.command.domain.product.ItemRepository;
import webshop.catalog.query.product.ItemService;
import webshop.common.model.Money;
import webshop.user.domain.seller.Seller;
import webshop.user.domain.seller.SellerService;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
@SessionAttributes("item")
public class ItemController {

    @Autowired
    ItemService itemService;

    @Autowired
    SellerService sellerService;

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder){
        dataBinder.setDisallowedFields("id");
    }

    @RequestMapping(value = "/my/sellers/{sellerId}/items/new", method = RequestMethod.GET)
    public String createForm(@PathVariable("sellerId") Long sellerId,Model model) {
        model.addAttribute("sellerId", sellerId);
        return "category/createItemForm.html";
    }

    @RequestMapping(value = "/my/sellers/{sellerId}/items/new", method = RequestMethod.POST)
    public String create(@PathVariable("sellerId") Long sellerId,ItemDTO item,Model model) {
        model.addAttribute("sellerId", sellerId);

        Seller seller = sellerService.findById(sellerId);

        Artwork newItem = new Artwork();

        newItem.setName(item.getName());
        newItem.setPrice(new Money(item.getPrice()));
        newItem.setContent(item.getContent());

        newItem.setSeller(seller);

        itemService.saveItem(newItem);

        return "redirect:/my/sellers/" + sellerId;
    }

//    @RequestMapping(value = "items", method = RequestMethod.GET)
//    public String items(ModelMap model){
//        List<Item> items = itemRepository.findAll();
//        model.addAttribute("items", items);
//        return "item/itemList";
//    }

    @RequestMapping("/items/{itemId}")
    public String detail(@PathVariable("itemId") Long itemId,
                         ModelMap model,
                         HttpServletResponse response) throws IOException {
        Optional<Item> item = itemService.findOne(itemId);
        if (item.isPresent()) {
            model.addAttribute("item", item.get());
            return "category/itemDetail";
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
    }



    @RequestMapping(value = "/items/{itemId}/edit", method=RequestMethod.GET)
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model){

        Item item = itemService.findOne(itemId)
                .orElseThrow(() -> new NoSuchElementException("Member not found with id: " + itemId));
        model.addAttribute("item",item);
        return "items/updateItemForm";
    }

    @RequestMapping(value = "/items", method = RequestMethod.GET)
    public String list(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute("items",items);
        return "category/itemList";
    }



}
