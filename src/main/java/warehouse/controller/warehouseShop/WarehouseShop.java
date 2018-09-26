package warehouse.controller.warehouseShop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import warehouse.entity.*;
import warehouse.repository.*;
import warehouse.service.DeliveryService;

import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Controller
@SessionAttributes("delivery")
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
    private DeliveryShopRepository deliveryShopInterface;
    @Autowired
    private ProductRepository productRepository;

    @RequestMapping(value = "/warehouseShop/documents")
    public String findlAllDocuments(@RequestParam(value = "sort",required = false)String sort,
                           Model model,
                           Locale locale){
        List<Delivery> documents =deliveryService.sortedDelivery(2L,sort);

        model.addAttribute("deliveries",documents);
        model.addAttribute("title",
                messageSource.getMessage("text.warehouseShop.document.documents.title",null,locale));

        return "warehouseShop/document/documents";
    }
    @RequestMapping(value = "/warehouseShop/document/{id}")
    public String findOneDocumentById(@PathVariable("id")Long id,
                       Model model,
                       Locale locale){
        Delivery delivery = deliveryShopInterface.findById(id);
        ItemsDelivery itemDocument = new ItemsDelivery();
        itemDocument.setDelivery(delivery);
        List<Product>products = productRepository.findall();
        model.addAttribute("itemDocument",itemDocument);
        model.addAttribute("delivery",delivery);
        model.addAttribute("products",products);
        model.addAttribute("title",
                messageSource.getMessage("text.warehouseShop.document.document.title",null,locale));
        return "warehouseShop/document/document";
    }

    @RequestMapping(value = "/warehouseShop/document/delete/{id}")
    public String deleteDocument(@PathVariable("id")Long id,
                         RedirectAttributes flash,
                         Locale locale) {

        Delivery document = deliveryShopInterface.findById(id);
        if (document == null){
            flash.addFlashAttribute("danger",
                    messageSource.getMessage("text.warehouseShop.document.document.errorDelete", null, locale));
            return "redirect:/warehouseShop/documents";

        }
        deliveryShopInterface.delete(document);
        flash.addFlashAttribute("success",
                messageSource.getMessage("text.warehouseShop.document.document.successDelete", null, locale));

        return "redirect:/warehouseShop/documents";
    }

    @RequestMapping(value = "/warehouseShop/document/form")
    public String create(Model model,
                         Locale locale){

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
        return "redirect:/warehouseShop/document/form/"+ delivery.getId();
    }

    @RequestMapping(value = "/warehouseShop/document/form/{id}")
    public String createItemDelivery(@PathVariable("id")Long id,
                                     Model model,
                                     Locale locale,
                                     RedirectAttributes flash,
                                     @RequestParam(value = "product",required = false)Long product,
                                     @RequestParam(value = "quantity",required = false)Long quantity){
        Delivery delivery = deliveryShopInterface.findById(id);
        List<ItemsDelivery>list = delivery.getItemdeliveries();

        if (product != null){
            Product product1 = productRepository.findOnebyId(product);
            List<ItemsDelivery>list1 = new ArrayList<>();
            delivery.addItemsDelivery(new ItemsDelivery(product1,quantity,delivery));
        }
        try {
            deliveryShopInterface.saveItem(delivery);
        }catch (NullPointerException e){

            flash.addFlashAttribute("danger","Brak lub nie wystarczająca ilość towaru na magazynie");

            return "redirect:/warehouseShop/document/form/"+delivery.getId();
        }

        List<Product>products = productRepository.findall();
        model.addAttribute("list",list);
        model.addAttribute("delivery",delivery);
        model.addAttribute("products",products);
        model.addAttribute("title",

                messageSource.getMessage("text.warehouseMain.document.document.title",null,locale));

        return "warehouseShop/document/documentItemsform";
    }
    @RequestMapping(value = "/warehouseShop/document/form/submit",method = RequestMethod.POST)
    public String saveItemDocument(@ModelAttribute("delivery") Delivery delivery,
                                   RedirectAttributes flash,
                                   Locale locale){

        if (!delivery.isConfirm()) {
            delivery.setConfirm(true);
            deliveryShopInterface.saveItem(delivery);
 flash.addFlashAttribute("success",messageSource.getMessage("text.warehouseShop.success.submit",null,locale));
            return "redirect:/warehouseShop/document/form/"+delivery.getId();
        }else{
            flash.addFlashAttribute("danger",messageSource.getMessage("text.warehouseMain.danger.submit",null,locale));
            return "redirect:/warehouseShop/document/form/"+delivery.getId();
        }

    }
    @RequestMapping(value ="warehouseShop/document/edit/{id}")
    public String editDocument(@PathVariable("id")Long id,
        RedirectAttributes flash,
        Locale locale) {
            Delivery delivery = deliveryShopInterface.findById(id);
            Long documentId = delivery.getDocument().getId();
            if (documentId == 1 || documentId == 2 ){
                flash.addFlashAttribute("danger",
                        messageSource.getMessage("text.warehouseShop.edit.danger",null,locale));
                return "redirect:/warehouseShop/documents";
            }
        return "warehouseShop/document/edit";
    }
}
