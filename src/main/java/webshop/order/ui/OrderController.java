package webshop.order.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import webshop.catalog.query.product.ItemData;
import webshop.common.ValidationErrorException;
import webshop.order.command.application.NoOrderProductException;
import webshop.order.command.application.OrderProduct;
import webshop.order.command.application.OrderRequest;
import webshop.order.command.application.PlaceOrderService;
import webshop.order.command.domain.*;
import webshop.catalog.query.product.ItemService;
import webshop.user.domain.member.CustomMemberDetails;
import webshop.user.domain.member.MemberService;
import webshop.user.domain.member.Member;
import webshop.catalog.command.domain.product.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class OrderController {

    @Autowired
    PlaceOrderService placeOrderService;
    @Autowired
    MemberService memberService;
    @Autowired
    ItemService itemService;

    @PostMapping("/orders/orderConfirm")
    public String orderConfirm(@ModelAttribute("orderReq") OrderRequest orderRequest, ModelMap modelMap){
        CustomMemberDetails memberDetails = (CustomMemberDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        orderRequest.setOrdererMemberId(memberDetails.getId());
        populateProductsAndTotalAmountsModel(orderRequest,modelMap);

        return"order/confirm";

    }

    private void populateProductsAndTotalAmountsModel(OrderRequest orderRequest, ModelMap modelMap) {
        List<Item> products = getProducts(orderRequest.getOrderProducts());
        modelMap.addAttribute("products", products);
        int totalAmounts = 0;
        for (int i = 0 ; i < orderRequest.getOrderProducts().size() ; i++) {
            OrderProduct oi = orderRequest.getOrderProducts().get(i);
            Item prod = products.get(i);
            totalAmounts += oi.getQuantity() * prod.getPrice().getValue();
        }
        modelMap.addAttribute("totalAmounts", totalAmounts);
    }

    private List<Item> getProducts(List<OrderProduct> orderProducts){
        List<Item> results = new ArrayList<>();
        for(OrderProduct op : orderProducts){
            Optional<Item> productOpt = itemService.findOne(op.getProductId());
            Item item = productOpt.orElseThrow(() -> new NoOrderProductException(op.getProductId()));
            results.add(item);
        }
        return results;
    }

    @PostMapping("/orders/order")
    public String order(@ModelAttribute("orderReq") OrderRequest orderRequest, BindingResult bindingResult, ModelMap modelMap){
        CustomMemberDetails memberDetails = (CustomMemberDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        orderRequest.setOrdererMemberId(memberDetails.getId());

        try{
            OrderNo orderNo = placeOrderService.placeOrder(orderRequest);
            modelMap.addAttribute("orderNo", orderNo.getNumber());
            return "order/orderComplete";
        } catch (ValidationErrorException e){
            e.getErrors().forEach(err -> {
                if(err.hasName()) {
                    bindingResult.rejectValue(err.getName(),err.getCode());
                } else{
                    bindingResult.reject(err.getCode());
                }
            });
            populateProductsAndTotalAmountsModel(orderRequest, modelMap);
            return "order/confirm";
        }
    }

    @InitBinder
    public void init(WebDataBinder binder) {
        binder.initDirectFieldAccess();
    }


}
