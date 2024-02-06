package webshop.User.domain.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import webshop.service.SellerService;

@Controller
@SessionAttributes("seller")
public class SellerController {

    @Autowired
    SellerService sellerService;

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder){
        dataBinder.setDisallowedFields("id");
    }

    @RequestMapping(value = "member/newSeller", method = RequestMethod.GET)
    public String createForm() { return "member/createSellerForm.html";}
}
