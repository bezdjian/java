package com.springapp.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by bezdj on 23/01/2017.
 */

@Controller
public class HomeController {
    @RequestMapping("/home")
    public String printName(@RequestParam String account, @RequestParam String email, @RequestParam String name,
                            @RequestParam String password , Model m){
        m.addAttribute("name",account);
        m.addAttribute("name",email);
        m.addAttribute("name",name);
        m.addAttribute("name",password);
        return "home";
    }
}
