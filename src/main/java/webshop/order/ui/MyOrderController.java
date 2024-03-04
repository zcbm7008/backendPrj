package webshop.order.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import webshop.order.query.application.OrderDetail;
import webshop.order.query.application.OrderDetailService;
import webshop.order.query.dao.OrderSummaryDao;
import webshop.springconfig.security.UserDetailServiceImpl;
import webshop.user.domain.member.CustomMemberDetails;
import webshop.user.domain.member.Member;
import webshop.user.domain.member.MemberService;

import java.util.Optional;

@Controller
public class MyOrderController {
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private OrderSummaryDao orderSummaryDao;
    @Autowired
    MemberService memberService;

    @RequestMapping("/my/orders")
    public String orders(ModelMap modelMap){
        CustomMemberDetails memberDetails = (CustomMemberDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        modelMap.addAttribute("orders", orderSummaryDao.findByOrdererId(memberDetails.getId()));
        return "my/orders";
    }

    @RequestMapping("/my/orders/{orderNo}")
    public String orderDetail(@PathVariable("orderNo") String orderNo, ModelMap modelMap){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Member member = memberService.findOneByName(userDetails.getUsername());
        Optional<OrderDetail> orderDetail = orderDetailService.getOrderDetail(orderNo);
        if(orderDetail.isPresent()){
            if(orderDetail.get().getOrderer().getMemberId().equals(member.getId())){
                modelMap.addAttribute("order", orderDetail.get());
                return "my/orderDetail";
            } else{
                return "my/notYourOrder";
            }
        } else{
            return "my/noOrder";
        }
    }

}
