package webshop.user.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import webshop.common.model.Image;
import webshop.service.SellerService;
import webshop.storage.StorageService;
import webshop.user.domain.seller.Seller;

import java.io.IOException;

@Controller
@SessionAttributes("seller")
public class SellerController {

    @Autowired
    SellerService sellerService;

    @Autowired
    StorageService storageService;

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder){
        dataBinder.setDisallowedFields("id");
    }

    @RequestMapping(value = "my/sellers/new", method = RequestMethod.GET)
    public String createForm() { return "member/createSellerForm";}

    @RequestMapping(value = "my/sellers/new", method = RequestMethod.POST)
    public String create(@RequestParam(value = "FileImage") MultipartFile FileImage, Seller seller) throws IOException {

        String fileName = storageService.uploadImageToCloud(FileImage);
//        Image FileImage = new Image(fileName);
        System.out.println(fileName);
//        seller.setImage(FileImage);

        sellerService.join(seller);

        return "redirect:/";
    }



}
