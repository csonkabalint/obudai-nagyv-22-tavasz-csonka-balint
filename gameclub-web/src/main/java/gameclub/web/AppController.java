package gameclub.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AppController {

    @GetMapping(value = "/")
    public String test(Model model){
        model.addAttribute("name", "name");
        return "test";
    }

}
