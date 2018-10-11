package warehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import warehouse.entity.Product;
import warehouse.repository.ProductRepository;
import warehouse.service.ProductService;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

@Controller
public class ProductController {
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductService productService;

 @RequestMapping(value = "/products")
    public String findallProducts(Model model,
                                  Locale locale) {
        Product product = new Product();
        List<Product> products = productRepository.findall();

        model.addAttribute("newProduct", product);
        model.addAttribute("title",
                messageSource.getMessage("text.product.products.title", null, locale));

        model.addAttribute("products", products);
        return "product/products";
    }

    @RequestMapping(value = "/products", method = RequestMethod.POST)
    public String saveProduct(@Valid @ModelAttribute("newProduct") Product product,
                              BindingResult result,
                              Locale locale,
                              RedirectAttributes redirectAttribute,
                              Model model) {

        if (result.hasErrors()) {

            List<Product> products = productRepository.findall();
            model.addAttribute("products", products);
            model.addAttribute("title",
                    messageSource.getMessage("text.product.products.title", null, locale));
            return "/product/products";
        } else {

            boolean checkExistProduct = productService.chackExistProductByName(product);

            if (!checkExistProduct) {

                redirectAttribute.addFlashAttribute("success",
                        messageSource.getMessage("text.product.products.successSave", null, locale));
                productRepository.save(product);

                return "redirect:/products";
            } else {

                redirectAttribute.addFlashAttribute("danger",
                        messageSource.getMessage("text.product.products.dangerSave", null, locale));

                return "redirect:/products";
            }
        }
    }
}