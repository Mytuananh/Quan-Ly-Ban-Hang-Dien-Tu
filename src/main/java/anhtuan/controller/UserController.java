package anhtuan.controller;

import anhtuan.model.User;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class UserController {

    @GetMapping("/login")
    public String showForm(Model model) {
        model.addAttribute("user", new User());
        return "/user/list";
    }

    @PostMapping("/login")
    public String checkValidation(@Validated @ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
        new User().validate(user, bindingResult);
        if (bindingResult.hasFieldErrors()) {
            return "/user/list";
        } else {
            model.addAttribute("user", user);
            return "redirect:/product";
        }
    }
}
