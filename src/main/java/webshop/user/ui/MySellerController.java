package webshop.user.ui;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import webshop.user.query.seller.SellerDTO;
import webshop.service.SellerService;

import java.util.List;

@Controller
public class MySellerController {
    @Autowired
    SellerService sellerService;

    @RequestMapping(value = "/my/seller", method = RequestMethod.GET)
    public String list(ModelMap model){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<SellerDTO> sellers = sellerService.getSellersWithMemberName(user.getUsername());
        model.addAttribute("sellers",sellers);
        return "member/sellerList";
    }
}
