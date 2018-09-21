package warehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import warehouse.entity.*;
import warehouse.repository.*;
import warehouse.service.DeliveryService;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Locale;

@Controller
public class WarehouseMain {
    @Autowired
    private WarehouseRepository warehouseRepository;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private DeliveryMainInterface deliveryMainInterface;
    @Autowired
    private DeliveryService deliveryService;
    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private ProductRepository productRepository;
    @RequestMapping(value = "/warehouseMain")
    public String findall(Model model,
                          Locale locale){
        Warehouse warehouse = warehouseRepository.findOnebyId(1L);
        model.addAttribute("title",
                messageSource.getMessage("text.warehouseMain.index.title",null,locale));
        return "warehouseMain/warehouseMain";
    }
    @RequestMapping(value = "/warehouseMain/state")
    public String findAll(Model model,
                          Locale locale){
        List<Product>state = productRepository.productWarehouseMain();
        model.addAttribute("state",state);
        model.addAttribute("title",
                messageSource.getMessage("text.warehouseMain.state.title",null,locale));
        return "warehouseMain/state";
    }
    @RequestMapping(value = "/warehouseMain/document")
    public String findlAll(@RequestParam(value = "sort",required = false)String sort,
                           Model model,
                           Locale locale){
        List<Delivery> deliveries =deliveryService.sortedDelivery(1L,sort);
        model.addAttribute("deliveries",deliveries);
        model.addAttribute("title",
                messageSource.getMessage("text.warehouseMain.document.documents.title",null,locale));

        return "warehouseMain/document/documents";
    }
    @RequestMapping(value = "/warehouseMain/document/{id}")
    public String find(@PathVariable("id")Long id,
                       Model model,
                       Locale locale){
        Delivery delivery = deliveryMainInterface.findById(id);

        model.addAttribute("delivery",delivery);
        model.addAttribute("title",
                messageSource.getMessage("text.warehouseMain.document.document.title",null,locale));
        return "warehouseMain/document/document";
    }
    @RequestMapping(value = "/warehouseMain/document/delete/{id}")
    public String delete(@PathVariable("id")Long id,
                         RedirectAttributes flash,
                         Locale locale) {

        Delivery delivery = deliveryMainInterface.findById(id);
        if (delivery == null){
            flash.addFlashAttribute("danger",
                    messageSource.getMessage("text.warehouseMain.document.document.errorDelete", null, locale));
            return "redirect:/warehouseMain/document";
        }
        deliveryMainInterface.delete(delivery);

        flash.addFlashAttribute("success",
                messageSource.getMessage("text.warehouseMain.document.document.successDelete", null, locale));

        return "redirect:/warehouseMain/document";
    }
    @RequestMapping(value = "/warehouseMain/document/form")
    public String create(Model model,
                         Locale locale
                        ){
        Warehouse warehouse =warehouseRepository.findOnebyId(1L);
        Delivery newDelivery = new Delivery();
        List<Document>documents = documentRepository.findall();
        model.addAttribute("warehouse",warehouse);
        model.addAttribute("documents",documents);
        model.addAttribute("newDelivery",newDelivery);
        model.addAttribute("title",
                messageSource.getMessage("text.warehouseMain.document.form.title",null,locale));
        return "warehouseMain/document/form";
    }
    @RequestMapping(value = "/warehouseMain/document/form",method = RequestMethod.POST)
    public String create(@ModelAttribute("newDelivery") Delivery delivery,
                         Model model,
                         RedirectAttributes flash,
                         Locale locale) throws Exception {
        try {
            deliveryMainInterface.save(delivery);
            model.addAttribute("success",
                    messageSource.getMessage("text.warehouseMain.document.success.save", null, locale));
        }catch (PersistenceException e){
            flash.addFlashAttribute("danger",
                    messageSource.getMessage("text.warehouseMain.document.danger.client", null, locale));
            return "redirect:/warehouseMain/document/form";
        }
        model.addAttribute("title",
                messageSource.getMessage("text.warehouseMain.document.success.save", null, locale));
        return "redirect:/warehouseMain/document/"+ delivery.getId();
    }
    }
