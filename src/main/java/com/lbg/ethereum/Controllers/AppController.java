package com.lbg.ethereum.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class AppController {

    @GetMapping("/")
    public String appStatus(){
        return "Ethereum Application is running!";
    }
}
