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
import warehouse.repository.DeliveryRepository;
import warehouse.service.DeliveryService;

import java.util.List;
import java.util.Locale;

@Controller
public class WarehouseMain {
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private DeliveryRepository deliveryRepository;
    @Autowired
    private DeliveryService deliveryService;

    @RequestMapping(value = "/warehouseMain")
    public String findall(Model model,
                          Locale locale){

        model.addAttribute("title",
                messageSource.getMessage("text.warehouseMain.index.title",null,locale));
        return "warehouseMain/warehouseMain";
    }
    @RequestMapping(value = "/warehouseMain/state")
    public String findAll(Model model,
                          Locale locale){

        model.addAttribute("title",
                messageSource.getMessage("text.warehouseMain.state.title",null,locale));
        return "warehouseMain/state";
    }
    @RequestMapping(value = "/warehouseMain/document")
    public String findlAll(@RequestParam(value = "sort",required = false)String sort,
                           Model model,
                           Locale locale){
        List<Delivery> deliveries =deliveryService.sortedDelivery(sort);
        model.addAttribute("deliveries",deliveries);
        model.addAttribute("title",
                messageSource.getMessage("text.warehouseMain.document.documents.title",null,locale));

        return "warehouseMain/document/documents";
    }
    @RequestMapping(value = "/warehouseMain/document/{id}")
    public String find(@PathVariable("id")Long id,
                       Model model,
                       Locale locale){
        Delivery delivery = deliveryRepository.findById(id);

        model.addAttribute("delivery",delivery);
        model.addAttribute("title",
                messageSource.getMessage("text.warehouseMain.document.document.title",null,locale));
        return "warehouseMain/document/document";
    }
    @RequestMapping(value = "/warehouseMain/document/delete/{id}")
    public String delete(@PathVariable("id")Long id,
                         RedirectAttributes flash,
                         Locale locale) {

        Delivery delivery = deliveryRepository.findById(id);
        if (delivery == null){
            flash.addFlashAttribute("danger",
                    messageSource.getMessage("text.warehouseMain.document.document.errorDelete", null, locale));
            return "redirect:/warehouseMain/document";
        }
        deliveryRepository.delete(delivery);

        flash.addFlashAttribute("success",
                messageSource.getMessage("text.warehouseMain.document.document.successDelete", null, locale));

        return "redirect:/warehouseMain/document";
    }
    @RequestMapping(value = "/warehouseMain/document/form")
    public String create(Model model,
                         Locale locale){

        model.addAttribute("title",
                messageSource.getMessage("text.warehouseMain.document.form.title",null,locale));
        return "warehouseMain/document/form";
    }
}
