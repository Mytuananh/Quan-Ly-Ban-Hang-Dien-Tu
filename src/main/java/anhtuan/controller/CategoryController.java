package anhtuan.controller;

import anhtuan.model.Category;
import anhtuan.model.Product;
import anhtuan.service.category.ICategoryService;
import anhtuan.service.product.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IProductService productService;

    @GetMapping("")
    public ModelAndView showAll() {
        ModelAndView modelAndView = new ModelAndView("/category/list");
        Iterable<Category> categories = categoryService.findAll();
        modelAndView.addObject("categories", categories);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView create() {
        ModelAndView modelAndView = new ModelAndView("/category/create");
        modelAndView.addObject("category", new Category());
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView save(@ModelAttribute("category") Category category) {
            categoryService.save(category);
            ModelAndView modelAndView = new ModelAndView("redirect:/category");
            modelAndView.addObject("category", new Category());
            modelAndView.addObject("message", "New category created successfully");
            return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable Long id) {
        Optional<Category> category = categoryService.findById(id);
        if (!category.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("/error404");
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("/category/edit");
            modelAndView.addObject("category", category.get());
            return modelAndView;
        }
    }

    @PostMapping("/edit")
    public ModelAndView update(@ModelAttribute("category") Category category) {
        categoryService.save(category);
        ModelAndView modelAndView = new ModelAndView("/category/edit");
        modelAndView.addObject("category", category);
        modelAndView.addObject("message", "Category updated successfully");
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable Long id) {
        categoryService.remove(id);
        ModelAndView modelAndView = new ModelAndView("redirect:/category");
        modelAndView.addObject("message", "Category deleted successfully");
        return modelAndView;
    }

    @GetMapping("/view/{id}")
    public ModelAndView viewCategory(@PathVariable Long id,@PageableDefault(sort = "name", value = 5) Pageable pageable) {
        Optional<Category> categories = categoryService.findById(id);
        if (!categories.isPresent()) {
            return new ModelAndView("/error404");
        }
        Page<Product> products = productService.findAllByCategory(categories.get(), pageable);
        ModelAndView modelAndView = new ModelAndView("/category/view");
        modelAndView.addObject("category", categories);
        modelAndView.addObject("products", products);
        return modelAndView;
    }
}
