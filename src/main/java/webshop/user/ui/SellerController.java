package webshop.user.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import webshop.service.SellerService;
import webshop.user.domain.member.Member;
import webshop.user.domain.seller.Seller;

@Controller
@SessionAttributes("seller")
public class SellerController {

    @Autowired
    SellerService sellerService;

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder){
        dataBinder.setDisallowedFields("id");
    }

    @RequestMapping(value = "my/sellers/new", method = RequestMethod.GET)
    public String createForm() { return "member/createSellerForm.html";}

    @RequestMapping(value = "my/sellers/new", method = RequestMethod.POST)
    public String create(Seller seller){

        sellerService.join(seller);
        return "redirect:/";
    }



}
