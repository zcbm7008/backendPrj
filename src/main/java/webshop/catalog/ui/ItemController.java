package webshop.catalog.ui;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import webshop.catalog.command.domain.category.Category;
import webshop.catalog.command.domain.category.CategoryRepository;
import webshop.catalog.command.domain.product.Artwork;
import webshop.catalog.command.domain.product.Item;
import webshop.catalog.command.domain.product.ItemSpecification;
import webshop.catalog.query.product.CategoryItem;
import webshop.catalog.query.product.ItemService;
import webshop.common.model.Image;
import webshop.common.model.Money;
import webshop.storage.StorageService;
import webshop.user.domain.seller.Seller;
import webshop.user.domain.seller.SellerService;

import java.io.IOException;
import java.util.*;

@Controller
@SessionAttributes("item")
public class ItemController {

    @Autowired
    ItemService itemService;

    @Autowired
    SellerService sellerService;

    @Autowired
    StorageService storageService;

    @Autowired
    CategoryRepository categoryRepository;

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder){
        dataBinder.setDisallowedFields("id");
    }

    @RequestMapping("/categories")
    public String categories(ModelMap model){
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        return "category/categoryList";
    }

    @RequestMapping("/categories/search")
    public String searchItems(ModelMap model, HttpServletRequest request,
                             @RequestParam(name = "page", required = false, defaultValue = "1") int page,
                             @RequestParam(name = "query", required = false) String query,
                              @RequestParam(name ="categoryId", required = false) Long categoryId
                              ){

        CategoryItem searchItems;
        String currentUrl = request.getRequestURI();
        Specification<Item> specs = Specification.where(null);

        if(query != null && !query.isEmpty()){
            specs = specs.and(ItemSpecification.nameContains(query));
            model.addAttribute("query",query);
        } else{
            return "/category/categoryList";
        }

        if(categoryId != null){
            specs = specs.and(ItemSpecification.hasCategory(categoryId));
            model.addAttribute("categoryId",categoryId);
        }

        searchItems = itemService.findItems(page,10,specs);

        int startPage = ((searchItems.getPage() -1) / 10) * 10 + 1;
        int endPage = Math.min(startPage+9, searchItems.getTotalPages());

        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);

        model.addAttribute("currentUrl", currentUrl);
        model.addAttribute("itemInCategory", searchItems);

        return "category/itemList";
    }

    @RequestMapping("/categories/{categoryId}")
    public String list(@PathVariable("categoryId") Long categoryId,
                       @RequestParam(name = "page", required = false, defaultValue = "1") int page,
                       ModelMap model) {

        CategoryItem itemInCategory = itemService.getItemInCategory(categoryId, page, 10);

        int startPage = ((itemInCategory.getPage() -1) / 10) * 10 + 1;
        int endPage = Math.min(startPage+9, itemInCategory.getTotalPages());

        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);

        model.addAttribute("itemInCategory",itemInCategory);


        return "category/itemList";
    }

    @RequestMapping(value = "/my/sellers/{sellerId}/items/new", method = RequestMethod.GET)
    public String createCategoryForm(@PathVariable("sellerId") Long sellerId,Model model){
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("sellerId",sellerId);

        return "category/createItemCategorySelectForm";
    }

    @RequestMapping(value = "/my/sellers/{sellerId}/{categoryId}/items/new", method = RequestMethod.GET)
    public String createCategoryItem(@PathVariable("sellerId") Long sellerId, @PathVariable("categoryId") Long categoryId,Model model){
        return "category/createItemForm";
    }

    @RequestMapping(value = "/my/sellers/{sellerId}/{categoryId}/items/new", method = RequestMethod.POST)
    public String create(@PathVariable("sellerId") Long sellerId, @PathVariable("categoryId") Long categoryId,@RequestParam(value = "FileImages") MultipartFile[] fileImages, ItemDTO item, Model model) throws IOException {

        Seller seller = sellerService.findById(sellerId);

        Artwork newItem = new Artwork();

        newItem.setName(item.getName());
        newItem.setPrice(new Money(item.getPrice()));
        newItem.setContent(item.getContent());

        newItem.addCategoryId(categoryId);
        newItem.setSeller(seller);

        for (MultipartFile fileImage : fileImages) {
            if (!fileImage.isEmpty()) {
                String fileName = storageService.uploadImageToCloud(fileImage);
                newItem.addImage((new Image(fileName)));
            }
        }

        itemService.saveItem(newItem);

        return "redirect:/my/sellers/" + sellerId;
    }


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
