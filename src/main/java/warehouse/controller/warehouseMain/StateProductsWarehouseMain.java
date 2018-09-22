package warehouse.controller.warehouseMain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import warehouse.entity.StateProducts;
import warehouse.repository.StateProductsRepository;

import java.util.List;
import java.util.Locale;

@Controller
public class StateProductsWarehouseMain {
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private StateProductsRepository stateProductsRepository;

    @RequestMapping("/warehouseMain/stateProducts")
    public String findAll(Model model,Locale locale){
        List<StateProducts> stateProducts = stateProductsRepository.findAllStateProductsbyWarehouse(1L);
        model.addAttribute("stateProducts",stateProducts);
        model.addAttribute("title",
                messageSource.getMessage("text.warehouseMain.state.title",null,locale));

        return "warehouseMain/state";
    }
}
