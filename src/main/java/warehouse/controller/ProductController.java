package warehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import warehouse.entity.Product;
import warehouse.repository.ProductRepository;

import java.util.List;
import java.util.Locale;

@Controller
public class ProductController {
    @Autowired
   private MessageSource messageSource;
    @Autowired
    private ProductRepository productRepository;

    @RequestMapping(value = "/products")
    public String findallProducts(Model model,
                          Locale locale){
        Product product = new Product();
        List<Product> products = productRepository.findall();

        model.addAttribute("newProduct",product);
        model.addAttribute("title",
                messageSource.getMessage("text.product.products.title",null,locale));
        model.addAttribute("cartdTitle",
                messageSource.getMessage("text.product.products.cardTitle",null,locale));
        model.addAttribute("products",products);
        return "product/products";
    }

    @RequestMapping(value = "/products",method = RequestMethod.POST)
    public String saveProduct(@ModelAttribute("newProduct") Product product,
                       Locale locale,RedirectAttributes redirectAttribute) {
        if (product.getId() == null) {
            redirectAttribute.addFlashAttribute("success",
                    messageSource.getMessage("text.product.products.successSave", null, locale));
            productRepository.save(product);
        } else {
            redirectAttribute.addFlashAttribute("success",
                    messageSource.getMessage("text.product.products.successEdit", null, locale));
            productRepository.save(product);
        }
            return "redirect:/products";

    }
   }
