package anhtuan.controller;


import anhtuan.model.Category;
import anhtuan.model.Product;
import anhtuan.model.ProductForm;
import anhtuan.service.category.ICategoryService;
import anhtuan.service.product.IProductService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.web.PageableDefault;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.data.domain.Pageable;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("/product")
@PropertySource("classpath:upload_file.properties")
public class ProductController {

    @Autowired
    private IProductService productService;
    @Value("${file-upload}")
    private String fileUpload;

    @Autowired
    private ICategoryService categoryService;

    @ModelAttribute("categoryList")
    public Iterable<Category> categories() {
        return categoryService.findAll();
    }

    @GetMapping("")
    public ModelAndView showAllListProduct(@RequestParam("search") Optional<String> search,@PageableDefault(sort = "category", value = 5) Pageable pageable) {

        Iterable<Product> products;
        if (search.isPresent()) {
            products = productService.findAllByNameContaining(search.get(), pageable);
        }
        else {
            products  = productService.findAll(pageable);
        }
        ModelAndView modelAndView = new ModelAndView("/product/list");
        modelAndView.addObject("products", products);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView createProduct() {
        ModelAndView modelAndView = new ModelAndView("/product/create");
        modelAndView.addObject("productForm", new ProductForm());
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView saveProduct(@ModelAttribute("productForm") ProductForm productForm) {
        ModelAndView modelAndView = new ModelAndView("redirect:/product");
        MultipartFile multipartFile = productForm.getImage();
        String fileName = multipartFile.getOriginalFilename();
        try {
            FileCopyUtils.copy(productForm.getImage().getBytes(), new File(fileUpload + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Product product = new Product(productForm.getName(), productForm.getPrice(), productForm.getDescription(), productForm.getCategory(),fileName);
        productService.save(product);
        modelAndView.addObject("message", "Them mot san pham moi thanh cong!");
        return modelAndView;
    }

    @GetMapping("/{id}/delete")
    public ModelAndView remove(@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView("redirect:/product");
        productService.remove(id);
        modelAndView.addObject("message", "Da xoa san pham thanh cong!");
        return modelAndView;
    }

    @GetMapping("/{id}/edit")
    public ModelAndView edit(@PathVariable Long id) throws IOException {
        Optional<Product> product = productService.findById(id);
        if (!product.isPresent()) {
            return new ModelAndView("/error404");
        } else {
            ModelAndView modelAndView = new ModelAndView("/product/edit");
            File file = new File(fileUpload + product.get().getImage());
            FileInputStream inputStream = new FileInputStream(file);
            MultipartFile multipartFile = new MockMultipartFile("fileNew", file.getName(), "jpg,.../img", IOUtils.toByteArray(inputStream));
            ProductForm productForm = new ProductForm(product.get().getId(), product.get().getName(),product.get().getPrice(),product.get().getDescription(), product.get().getCategory(),multipartFile);
            modelAndView.addObject("productForm", productForm);
            return modelAndView;
        }
    }

    @PostMapping("/edit")
    public ModelAndView update(@ModelAttribute("productForm") ProductForm productForm) {
        ModelAndView modelAndView = new ModelAndView("redirect:/product");
        MultipartFile multipartFile = productForm.getImage();
        String fileName = multipartFile.getOriginalFilename();
        try {
            FileCopyUtils.copy(productForm.getImage().getBytes(), new File(fileUpload + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Product product = new Product(productForm.getId(),productForm.getName(), productForm.getPrice(), productForm.getDescription(), productForm.getCategory(),fileName);
        productService.save(product);
        modelAndView.addObject("message", "Cap nhat san pham thanh cong!");
        return modelAndView;
    }

}
