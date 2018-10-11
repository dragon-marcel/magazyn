package warehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Locale;

@Controller
public class LoginController {
    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value = "login")
    public String login(RedirectAttributes flash,
                        Principal principal,
                        @RequestParam(value = "error",required = false)String error,
                        @RequestParam(value = "logout",required = false)String logout,
                        Locale locale) {

        if (principal != null) {

            flash.addFlashAttribute("success",
                    messageSource.getMessage("text.login.successLogin",null,locale));
            return "redirect:/users";
        }
        if (error != null){

            flash.addFlashAttribute("danger",
                    messageSource.getMessage("text.login.errorLogin",null,locale));

            return "redirect:/login";
        }
        if (logout != null){

            flash.addFlashAttribute("success",
                    messageSource.getMessage("text.login.successLogout",null,locale));
            return "redirect:/login";
        }
        return "login/login";
    }
}
