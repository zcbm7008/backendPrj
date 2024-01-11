package webshop.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import webshop.catalog.command.domain.product.Artwork;
import webshop.catalog.command.domain.product.Item;
import webshop.service.ItemService;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
@SessionAttributes("item")
public class ItemController {

    @Autowired
    ItemService itemService;

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder){
        dataBinder.setDisallowedFields("id");
    }

    @RequestMapping(value = "items/new", method = RequestMethod.GET)
    public String createForm() {
        return "items/createItemForm";
    }

    @RequestMapping(value = "/itemss/new", method = RequestMethod.POST)
    public String create(Artwork item) {
        itemService.saveItem(item);
        return "redirect:/items";
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
        return "items/itemList";
    }



}
