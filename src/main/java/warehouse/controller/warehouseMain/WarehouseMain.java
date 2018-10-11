package warehouse.controller.warehouseMain;

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
import javax.persistence.TransactionRequiredException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Controller
@SessionAttributes("delivery")
public class WarehouseMain {
    @Autowired
    private WarehouseRepository warehouseRepository;
    @Autowired
    private MessageSource messageSource;
    @Qualifier("DocumentWarehouseMain")
    @Autowired
    private DeliveryImpl deliveryImp;
    @Autowired
    private DeliveryService deliveryService;
    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ItemDeliveryIntefrace itemDeliveryIntefrace;

    @RequestMapping(value = {"/","/warehouseMain/documents"})
    public String findlAllDocuments(@RequestParam(value = "sort",required = false)String sort,
                           Model model,
                           Locale locale){
        List<Delivery> deliveries =deliveryService.sortedDelivery(1L,sort);
        model.addAttribute("deliveries",deliveries);
        model.addAttribute("title",
                messageSource.getMessage("text.warehouseMain.document.documents.title",null,locale));

        return "warehouseMain/document/documents";
    }

    @RequestMapping(value = "/warehouseMain/document/{id}")
    public String findDocumentById(@PathVariable("id")Long id,
                       Model model,
                       Locale locale){
        Delivery document = deliveryImp.findById(id);
        if (document == null){
            model.addAttribute("danger",
                    messageSource.getMessage("text.warehouseMain.document.document.notExist",null,locale));
            return "redirect:/warehouseMain/documents";
        }
        ItemsDelivery itemDocument = new ItemsDelivery();
        List<Product>products = productRepository.findall();

        model.addAttribute("itemDocument",itemDocument);
        model.addAttribute("delivery",document);
        model.addAttribute("products",products);
        model.addAttribute("title",
                messageSource.getMessage("text.warehouseMain.document.document.title",null,locale));

        return "warehouseMain/document/document";
    }

    @RequestMapping(value = "/warehouseMain/document/delete/{id}")
    public String deleteDocument(@PathVariable("id")Long id,
                         RedirectAttributes flash,
                         Locale locale) {

        Delivery document = deliveryImp.findById(id);
        if (document == null){
            flash.addFlashAttribute("danger",
                    messageSource.getMessage("text.warehouseMain.document.document.errorDelete", null, locale));
            return "redirect:/warehouseMain/documents";
        }
        try {
            deliveryImp.delete(document);

            flash.addFlashAttribute("success",
                    messageSource.getMessage("text.warehouseMain.document.document.successDelete", null, locale));

            return "redirect:/warehouseMain/documents";
        }catch (NullPointerException ne){
            flash.addFlashAttribute("danger",
                    messageSource.getMessage("text.warehouseMain.document.document.dangerDelete", null, locale));
            return "redirect:/warehouseMain/documents";
        }
    }


    @RequestMapping(value = "/warehouseMain/document/form")
    public String createDocument(Model model,
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
    public String saveDocument(@ModelAttribute("newDelivery") Delivery delivery,
                         Model model,
                         RedirectAttributes flash,
                         Locale locale) throws Exception {
        try {
            deliveryImp.save(delivery);
            model.addAttribute("success",
                    messageSource.getMessage("text.warehouseMain.document.success.save", null, locale));
        }catch (NullPointerException e){
            flash.addFlashAttribute("danger",
                    messageSource.getMessage("text.warehouseMain.document.danger.client", null, locale));
            return "redirect:/warehouseMain/document/form";
        }
        model.addAttribute("title",
                messageSource.getMessage("text.warehouseMain.document.success.save", null, locale));
        return "redirect:/warehouseMain/document/form/"+ delivery.getId();
    }

    @RequestMapping(value = "/warehouseMain/document/form/{id}")
    public String createItemDocument(@PathVariable("id")Long id,
                                     Model model,
                                     Locale locale,
                                     RedirectAttributes flash,
                                     @RequestParam(value = "product",required = false)Long product,
                                     @RequestParam(value = "quantity",required = false)Long quantity){
        Delivery delivery = deliveryImp.findById(id);
        List<ItemsDelivery>list = delivery.getItemdeliveries();

        if (product != null){
           Product product1 = productRepository.findOnebyId(product);
           List<ItemsDelivery>list1 = new ArrayList<>();
            delivery.addItemsDelivery(new ItemsDelivery(product1,quantity,delivery));
        }
        try {
            deliveryImp.saveItem(delivery);
        }catch (NullPointerException e){

            flash.addFlashAttribute("danger","Brak lub nie wystarczająca ilość towaru na magazynie");

            return "redirect:/warehouseMain/document/form/"+delivery.getId();
        }

        List<Product>products = productRepository.findall();
        model.addAttribute("list",list);
        model.addAttribute("delivery",delivery);
        model.addAttribute("products",products);
                model.addAttribute("title",

                        messageSource.getMessage("text.warehouseMain.document.document.title",null,locale));

                return "warehouseMain/document/documentItemsform";
    }

    @RequestMapping(value = "/warehouseMain/document/form/submit",method = RequestMethod.POST)
    public String submitDocument(@ModelAttribute("delivery") Delivery delivery,
                                   RedirectAttributes flash,
                                   Locale locale){

        if (!delivery.isConfirm()) {
            delivery.setConfirm(true);
            deliveryImp.submit(delivery);
                        flash.addFlashAttribute("success",messageSource.getMessage("text.warehouseMain.success.submit",null,locale));
            return "redirect:/warehouseMain/document/form/"+delivery.getId();
        }else{
            flash.addFlashAttribute("danger",messageSource.getMessage("text.warehouseMain.danger.submit",null,locale));
            return "redirect:/warehouseMain/document/form/"+delivery.getId();
        }

    }
    @RequestMapping(value ="warehouseMain/document/edit/{id}")
    public String editDocument(@PathVariable("id")Long id,
                               @RequestParam(value = "product",required = false)Long product,
                               @RequestParam(value = "quantity",required = false)Long quantity,
                               RedirectAttributes flash,
                               Locale locale,
                               Model model)throws Exception {
        Delivery delivery = deliveryImp.findById(id);
        List<Product> products = productRepository.findall();
        if (product != null) {
            Product product1 = productRepository.findOnebyId(product);
            ItemsDelivery itemsDelivery = new ItemsDelivery(product1, quantity, delivery);
            delivery.addItemsDelivery(itemsDelivery);
            try {
                itemDeliveryIntefrace.saveItem(itemsDelivery);
            } catch (NullPointerException e) {

                flash.addFlashAttribute("danger", "Brak lub nie wystarczająca ilość towaru na magazynie");

                return "redirect:/warehouseMain/document/edit/" + delivery.getId();
            }

        }
        Long documentId = delivery.getDocument().getId();
        List<ItemsDelivery>itemsDeliveries = delivery.getItemdeliveries();
        model.addAttribute("delivery",delivery);
        model.addAttribute("itemsDeliveries",itemsDeliveries);
        model.addAttribute("products",products);
        model.addAttribute("title",messageSource.getMessage("text.warehouseMAIN.edit.title",null,locale));
        if (documentId == 1 || documentId == 2 ){
            flash.addFlashAttribute("danger",
                    messageSource.getMessage("text.warehouseShop.edit.danger",null,locale));
            return "redirect:/warehouseMain/documents";
        }
        return "warehouseMain/document/edit";
    }
    @RequestMapping(value ="warehouseMain/document/edit")
    public String deleteItem(@RequestParam(value = "deleteId",required = false)Long deleteId,
                             RedirectAttributes flash,
                             Locale locale)throws Exception{

        ItemsDelivery itemDelivery= itemDeliveryIntefrace.findOneById(deleteId);
        Delivery delivery = itemDelivery.getDelivery();

        try {
            itemDeliveryIntefrace.delete(itemDelivery);
        }catch (NullPointerException ne){
            flash.addFlashAttribute("danger",
                    messageSource.getMessage("text.warehouseMain.document.document.dangerDelete", null, locale));
            return "redirect:/warehouseMain/document/edit/"+delivery.getId();
        }

        flash.addFlashAttribute("success",
                messageSource.getMessage("text.warehouseShop.edit.deleteItem",null,locale));
        return "redirect:/warehouseMain/document/edit/"+delivery.getId();
    }
}

