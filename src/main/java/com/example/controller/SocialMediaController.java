package com.example.controller;
import java.sql.SQLException;

import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;
import com.example.entity.*;
import com.example.service.*;


/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@Controller
public class SocialMediaController {
    @PostMapping("/register")
    public @ResponseBody ResponseEntity<Account> addAccount(@RequestBody Account account){
        if(account.getUsername().length() > 0 && account.getPassword().length() >= 4)
            try{
                AccountService.addAccount(account);
                return ResponseEntity.ok(account);
            }catch(SQLException e){
                return ResponseEntity.status(409).build();
            }

        return ResponseEntity.status(400).build();
    }

    @PostMapping("/login")
    public @ResponseBody ResponseEntity<Account> login(@RequestBody Account account){
        if(AccountService.login(account)!=null)
            return ResponseEntity.ok(AccountService.login(account));
        return ResponseEntity.status(401).build();
    }
}
