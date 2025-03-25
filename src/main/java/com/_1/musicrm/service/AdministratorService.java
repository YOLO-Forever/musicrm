package com._1.musicrm.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com._1.musicrm.model.Administrator;
import com._1.musicrm.repository.AdministratorRepository;

@Service
public class AdministratorService {
    @Autowired
    private AdministratorRepository adminRepository;

    public Administrator saveAdmin(Administrator admin){
        return adminRepository.save(admin); 
    }

    public String loginAdmin(Administrator admin){
        Optional<Administrator> ad = adminRepository.findByAdminName(admin.getAdminName());
        if(ad.isEmpty()){
            return "Administrator Not Found!";
        }else if(!(ad.get().getAdminPassword()).equals(admin.getAdminPassword())){
            return "Password Mismatch!";
        }else{
            return "Login success!";
        }  
    }
}