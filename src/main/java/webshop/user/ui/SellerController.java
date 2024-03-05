package webshop.user.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import webshop.common.model.Image;
import webshop.user.domain.member.CustomMemberDetails;
import webshop.user.domain.member.MemberService;
import webshop.user.domain.seller.SellerService;
import webshop.storage.StorageService;
import webshop.user.domain.member.Member;
import webshop.user.domain.seller.Seller;

import java.io.IOException;

@Controller
@SessionAttributes("seller")
public class SellerController {
    @Autowired
    MemberService memberService;

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
    public String create(@RequestParam(value = "FileImage") MultipartFile fileImage, Seller seller) throws IOException {

        CustomMemberDetails memberDetails = (CustomMemberDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Member member = memberService.findById(memberDetails.getId());

        seller.setMember(member);

        if(!fileImage.isEmpty()){
            String fileName = storageService.uploadImageToCloud(fileImage);
            seller.setImage(new Image(fileName));
        }


        // The join process includes validation of the fields in the seller object,
        // if it contains invalid data, the method may throw an exception.
        sellerService.join(seller);

        return "redirect:/";
    }





}
