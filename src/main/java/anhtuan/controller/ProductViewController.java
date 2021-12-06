package anhtuan.controller;

import anhtuan.model.Cart;
import anhtuan.model.Product;
import anhtuan.service.product.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@SessionAttributes("cart")
@RequestMapping("/shop")
public class ProductViewController {

    @Autowired
    private IProductService productService;

    @ModelAttribute("cart")
    public Cart setupCart() {
        return new Cart();
    }

    @GetMapping("")
    public ModelAndView showShop(@PageableDefault(sort = "category", value = 5) Pageable pageable) {
        ModelAndView modelAndView = new ModelAndView("/product/shop");
        Iterable<Product> products = productService.findAll(pageable);
        modelAndView.addObject("products", products);
        return modelAndView;
    }

    @GetMapping("/add/{id}")
    public String addToCart(@PathVariable Long id, @ModelAttribute  Cart cart, @RequestParam("action") String action) {
        Optional<Product> productOptional = productService.findById(id);
        if (!productOptional.isPresent()) {
            return "/error404";
        }
        if (action.equals("show")) {
            cart.addProduct(productOptional.get());
            return "redirect:/shopping-cart";
        }
        if (action.equals("down")) {
            cart.removeOneForAProduct(productOptional.get());
            return "redirect:/shopping-cart";
        }
        cart.addProduct(productOptional.get());
        return "redirect:/shop";
    }

    @GetMapping("/delete/{id}")
    public ModelAndView remove(@PathVariable Long id, @ModelAttribute Cart cart) {
        Optional<Product> optionalProduct = productService.findById(id);
        if (!optionalProduct.isPresent()) {
            return new ModelAndView("/error404");
        } else {
            cart.deleteAllForAProduct(optionalProduct.get());
            return new ModelAndView("redirect:/shopping-cart");
        }
    }
}
