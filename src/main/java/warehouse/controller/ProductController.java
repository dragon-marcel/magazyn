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
    public String findall(Model model,
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
    @RequestMapping(value = "/product/edit/{id}")
    public String edit(@PathVariable("id")long id,
                       Model model,
                       Locale locale) {
       Product product = productRepository.find(id);
        List<Product> products = productRepository.findall();
        model.addAttribute("products",products);
        model.addAttribute("title", messageSource.getMessage("text.product.products.editTitle",null,locale));
        model.addAttribute("newProduct",product);
       model.addAttribute("cartTitle",
               messageSource.getMessage("text.product.products.cardTitleEdit",null,locale));

        return "/product/products";
    }
    @RequestMapping(value = "/products",method = RequestMethod.POST)
    public String save(@ModelAttribute("newProduct") Product product,
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
    @RequestMapping(value = "/product")
    public String delete(@RequestParam(value = "delete")Long id,
                         RedirectAttributes redirectAttribute,
                         Locale locale) {
        Product product = productRepository.find(id);
        productRepository.delete(product);
        if (product == null){
            redirectAttribute.addFlashAttribute("error",
                    messageSource.getMessage("text.product.products.ErrorDelete",null,locale));
        }
        redirectAttribute.addFlashAttribute("success",
                messageSource.getMessage("text.product.products.successDelete",null,locale));
        return "redirect:/products";
    }
    @GetMapping(value = "/load-product/{term}",produces = {"application/json"})
    public @ResponseBody List<Product> searchProduct(@PathVariable String term){

        return productRepository.findProductByName(term);
    }


}
