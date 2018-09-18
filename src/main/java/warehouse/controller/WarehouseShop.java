package warehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import warehouse.entity.Delivery;
import warehouse.entity.Product;
import warehouse.repository.DeliveryRepository;
import warehouse.service.DeliveryService;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Controller
public class WarehouseShop {
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private DeliveryRepository deliveryRepository;
    @Autowired
    private DeliveryService deliveryService;

    @RequestMapping(value = "/warehouseShop")
    public String findall(Model model,
                          Locale locale){

        model.addAttribute("title",
                messageSource.getMessage("text.warehouseShop.index.title",null,locale));
        return "warehouseShop/warehouseShop";
    }
    @RequestMapping(value = "/warehouseShop/state")
    public String findAll(Model model,
                          Locale locale){

        model.addAttribute("title",
                messageSource.getMessage("text.warehouseShop.state.title",null,locale));
        return "warehouseShop/state";
    }
    @RequestMapping(value = "/warehouseShop/document")
    public String findlAll(@RequestParam(value = "sort",required = false)String sort,
                           Model model,
                          Locale locale){
        List<Delivery> deliveries =deliveryService.sortedDelivery(sort);
        model.addAttribute("deliveries",deliveries);

        model.addAttribute("title",
                messageSource.getMessage("text.warehouseShop.document.documents.title",null,locale));
        return "warehouseShop/document/documents";
    }
    @RequestMapping(value = "/warehouseShop/document/{id}")
    public String find(@PathVariable("id")Long id,
                       Model model,
                       Locale locale){
        Delivery delivery = deliveryRepository.findById(id);

        model.addAttribute("delivery",delivery);
        model.addAttribute("title",
                messageSource.getMessage("text.warehouseShop.document.document.title",null,locale));
        return "warehouseShop/document/document";
    }

    @RequestMapping(value = "/warehouseShop/document/delete/{id}")
    public String delete(@PathVariable("id")Long id,
                         RedirectAttributes flash,
                         Locale locale) {

        Delivery delivery = deliveryRepository.findById(id);
        if (delivery == null){
            flash.addFlashAttribute("danger",
                    messageSource.getMessage("text.warehouseShop.document.document.errorDelete", null, locale));
            return "redirect:/warehouseShop/document";
        }
        deliveryRepository.delete(delivery);
        flash.addFlashAttribute("success",
                messageSource.getMessage("text.warehouseShop.document.document.successDelete", null, locale));

        return "redirect:/warehouseShop/document";
    }

    @RequestMapping(value = "/warehouseShop/document/form")
    public String create(Model model,
                           Locale locale){

        model.addAttribute("title",
                messageSource.getMessage("text.warehouseShop.document.form.title",null,locale));
        return "warehouseShop/document/form";
    }
}
