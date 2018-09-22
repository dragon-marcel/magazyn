package warehouse.controller.warehouseShop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import warehouse.entity.StateProducts;
import warehouse.repository.StateProductsRepository;

import java.util.List;
import java.util.Locale;

@Controller
public class StateProductsShop {
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private StateProductsRepository stateProductsRepository;

    @RequestMapping("/warehouseShop/stateProducts")
    public String findAll(Model model,Locale locale){
        List<StateProducts> stateProducts = stateProductsRepository.findAllStateProductsbyWarehouse(2L);
        model.addAttribute("stateProducts",stateProducts);
        model.addAttribute("title",
                messageSource.getMessage("text.warehouseShop.state.title",null,locale));

        return "warehouseShop/state";
    }
}
