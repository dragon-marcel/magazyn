package warehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import warehouse.entity.User;
import warehouse.repository.UserRepository;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

@Controller
@SessionAttributes("newUser")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @RequestMapping(value = "/users")
    public String findAll(Model model,Locale locale){
        User user = new User();
        model.addAttribute("newUser",user);

        List<User> users = userRepository.findall();
        model.addAttribute("users",users);
        model.addAttribute("title",messageSource.getMessage("text.user.users.title",null,locale));

        return "user/users";
    }

    @RequestMapping(value = "/users",method = RequestMethod.POST)
    public String save(@ModelAttribute(value = "newUser")User user,
                       RedirectAttributes attributes,
                       Locale locale){

        attributes.addFlashAttribute("success",
                messageSource.getMessage("text.user.users.succesSave",null,locale));

        userRepository.save(user);

        return "redirect:/users";
    }

   @RequestMapping(value = "/user")
        public String delete(@RequestParam(value = "delete",required = false)Long id,
                            RedirectAttributes attributes,
                            Locale locale){
        User user = userRepository.find(id);
        if(user == null){
            attributes.addFlashAttribute("danger",
                    messageSource.getMessage("text.user.users.dangerDelete",null,locale));
            return "redirect:/users";
        }
        attributes.addFlashAttribute("success",
                messageSource.getMessage("text.user.users.succesDelete",null,locale));
        userRepository.delete(user);
            return "redirect:/users";
       }
   }
