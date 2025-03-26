package com._1.musicrm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com._1.musicrm.model.Administrator;
import com._1.musicrm.service.AdministratorService;

@Controller
public class AdministratorController {
    @Autowired
    private AdministratorService adminService;

    @GetMapping("/adminRegister")
    public String getRegisterAdminForm(Model model){
        model.addAttribute("admin", new Administrator());
        return "adminRegister";  // return to adminRegister.html
    }

    @PostMapping("/adminRegister")
    public String registerAdmin(@ModelAttribute Administrator admin){
        adminService.saveAdmin(admin);
        return "redirect:/adminLogin"; 
    }

    @GetMapping("/adminLogin")
    public String getLoginAdminForm(Model model){
        model.addAttribute("admin", new Administrator());
       return "adminLogin";  // return to adminLogin.html
    }

    @PostMapping("/adminLogin")
    public String loginAdmin(@ModelAttribute Administrator admin, Model model){
        String result = adminService.loginAdmin(admin);
        switch(result){
            case "Administrator Not Found!" -> {
                model.addAttribute("error", "Administrator Not Found!");
                return "adminLogin";
            }
            case "Password Mismatch!" -> {
                model.addAttribute("error", "Password Mismatch!");
                return "adminLogin";
            } 
            case "Login Success!" -> {
                return "redirect:/adminHome";
            }
            default -> {
                model.addAttribute("error", "Unknown Error!");
                return "adminLogin";
            }
        }
    }

    @GetMapping("/adminHome")
    public String showAdminHome(){
        return "adminHome";  // return to adminHome.html
    }
}