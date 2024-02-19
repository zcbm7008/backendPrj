package webshop.user.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import webshop.user.domain.seller.Seller;
import webshop.user.query.seller.SellerDTO;
import webshop.user.domain.seller.SellerService;

import java.util.List;

@Controller
public class MySellerController {
    @Autowired
    SellerService sellerService;

    @RequestMapping(value = "/my/sellers", method = RequestMethod.GET)
    public String list(ModelMap model){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<SellerDTO> sellers = sellerService.getSellersWithMemberName(userDetails.getUsername());
        model.addAttribute("sellers",sellers);
        return "member/sellerList";
    }

    @RequestMapping(value = "/my/sellers/{sellerId}", method = RequestMethod.GET)
    public String sellerDetail(@PathVariable("sellerId") Long sellerId, ModelMap modelMap){
        Seller seller = sellerService.findById(sellerId);


        modelMap.addAttribute("seller", seller);

        return "member/sellerDetail";

    }


}
