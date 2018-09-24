package warehouse.controller.warehouseMain;

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
import java.util.List;
import java.util.Locale;

@Controller
@SessionAttributes("itemDocument")
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

    @RequestMapping(value = "/warehouseMain/documents")
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
        Delivery document = deliveryMainInterface.findById(id);
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

        Delivery document = deliveryMainInterface.findById(id);
        if (document == null){
            flash.addFlashAttribute("danger",
                    messageSource.getMessage("text.warehouseMain.document.document.errorDelete", null, locale));
            return "redirect:/warehouseMain/documents";
        }
        deliveryMainInterface.delete(document);

        flash.addFlashAttribute("success",
                messageSource.getMessage("text.warehouseMain.document.document.successDelete", null, locale));

        return "redirect:/warehouseMain/documents";
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
        return "redirect:/warehouseMain/document/form/"+ delivery.getId();
    }

    //NEW ITEM DOCUMENT//
    @RequestMapping(value = "/warehouseMain/document/form/{id}")
    public String createItemDocument(@PathVariable("id")Long id,
                                     Model model,
                                     Locale locale){
        Delivery delivery = deliveryMainInterface.findById(id);


        ItemsDelivery itemDocument = new ItemsDelivery();
        itemDocument.setDelivery(delivery);
        List<Product>products = productRepository.findall();
        model.addAttribute("itemDocument",itemDocument);
        model.addAttribute("delivery",delivery);
        model.addAttribute("products",products);
                model.addAttribute("title",

                        messageSource.getMessage("text.warehouseMain.document.document.title",null,locale));

                return "warehouseMain/document/documentItemsform";
    }

    @RequestMapping(value = "/warehouseMain/document/form/addItem",method = RequestMethod.POST)
    public String saveItemDocument(@ModelAttribute("itemDocument") ItemsDelivery itemDocument,
                                   RedirectAttributes flash){
        Delivery delivery = itemDocument.getDelivery();
        delivery.addItemsDelivery(itemDocument);
        try {
            deliveryMainInterface.merge(delivery);
        }catch (NullPointerException e){

            flash.addFlashAttribute("danger","Brak lub nie wystarczająca ilość towaru na magazynie");

            return "redirect:/warehouseMain/document/form/"+delivery.getId();
        }
        return "redirect:/warehouseMain/document/form/"+delivery.getId();
    }

    @RequestMapping(value ="warehouseMain/document/edit/{id}")
        public String editDocument(@PathVariable("id")Long id) {

         return "warehouseMain/document/edit";
    }
    }

