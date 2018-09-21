package warehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import warehouse.entity.*;
import warehouse.repository.*;
import warehouse.service.DeliveryService;

import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;
import javax.transaction.TransactionalException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Controller
@SessionAttributes("itemdelivery")
public class WarehouseShop {
    @Autowired
    private WarehouseRepository warehouseRepository;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private DeliveryService deliveryService;
    @Autowired
    private DeliveryMainShopRepository deliveryShopInterface;
    @Autowired
    private ProductRepository productRepository;

    @RequestMapping(value = "/warehouseShop")
    public String findall(Model model,
                          Locale locale){
        Warehouse warehouse = warehouseRepository.findOnebyId(2L);

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
        List<Delivery> deliveries =deliveryService.sortedDelivery(2L,sort);
        model.addAttribute("deliveries",deliveries);

        model.addAttribute("title",
                messageSource.getMessage("text.warehouseShop.document.documents.title",null,locale));
        return "warehouseShop/document/documents";
    }
    @RequestMapping(value = "/warehouseShop/document/{id}")
    public String find(@PathVariable("id")Long id,
                       Model model,
                       Locale locale){
        Delivery delivery = deliveryShopInterface.findById(id);
        ItemsDelivery itemsDelivery = new ItemsDelivery();
        List<Product>products = productRepository.findall();
        model.addAttribute("itemdelivery",itemsDelivery);
        model.addAttribute("delivery",delivery);
        model.addAttribute("products",products);
        model.addAttribute("title",
                messageSource.getMessage("text.warehouseShop.document.document.title",null,locale));
        return "warehouseShop/document/document";
    }
    @RequestMapping(value = "/warehouseShop/document/{id}",method = RequestMethod.POST)
    public String saveItem(@PathVariable("id")Long id,
                       Model model,
                       Locale locale,@ModelAttribute("itemdelivery") ItemsDelivery itemsDelivery) {

        Delivery delivery = deliveryShopInterface.findById(id);
       List<ItemsDelivery> itemsDeliveries =delivery.getItemdeliveries();
       itemsDelivery.setDelivery(delivery);
       itemsDeliveries.add(itemsDelivery);
       model.addAttribute("item",itemsDeliveries);
       ///delivery.setItemdeliveries(itemsDeliveries);
      // deliveryShopInterface.merge(delivery);

        return "warehouseShop/document/"+delivery.getId();
    }

    @RequestMapping(value = "/warehouseShop/document/delete/{id}")
    public String delete(@PathVariable("id")Long id,
                         RedirectAttributes flash,
                         Locale locale) {

        Delivery delivery = deliveryShopInterface.findById(id);
        if (delivery == null){
            flash.addFlashAttribute("danger",
                    messageSource.getMessage("text.warehouseShop.document.document.errorDelete", null, locale));
            return "redirect:/warehouseShop/document";

        }
        deliveryShopInterface.delete(delivery);
        flash.addFlashAttribute("success",
                messageSource.getMessage("text.warehouseShop.document.document.successDelete", null, locale));

        return "redirect:/warehouseShop/document";
    }

    @RequestMapping(value = "/warehouseShop/document/form")
    public String create(Model model,
                         Locale locale    ){
        Warehouse warehouse =warehouseRepository.findOnebyId(2L);
        Delivery newDelivery = new Delivery();
        List<Document>documents = documentRepository.findall();
        model.addAttribute("warehouse",warehouse);
        model.addAttribute("documents",documents);
        model.addAttribute("newDelivery",newDelivery);
        model.addAttribute("title",
                messageSource.getMessage("text.warehouseShop.document.form.title",null,locale));

        return "warehouseShop/document/form";
    }

    @RequestMapping(value = "/warehouseShop/document/form",method = RequestMethod.POST)
    public String save(@ModelAttribute("newDelivery") Delivery delivery,
                       Model model,Locale locale,
                       RedirectAttributes flash)throws Exception {
            try {
                deliveryShopInterface.save(delivery);
                model.addAttribute("success",
                        messageSource.getMessage("text.warehouseShop.document.success.save", null, locale));
            } catch (PersistenceException pe) {
                flash.addFlashAttribute("danger",
                        messageSource.getMessage("text.warehouseShop.document.danger.delivery", null, locale));
                return "redirect:/warehouseShop/document/form";
            }
                model.addAttribute("title",
                        messageSource.getMessage("text.warehouseShop.document.client.form.title", null, locale));
        model.addAttribute("title",
                messageSource.getMessage("text.warehouseShop.document.form.title", null, locale));
        return "redirect:/warehouseShop/document/"+ delivery.getId();
    }}

