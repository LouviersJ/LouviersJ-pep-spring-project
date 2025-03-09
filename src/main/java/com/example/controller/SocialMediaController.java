package com.example.controller;
import java.sql.SQLException;
import org.jboss.logging.Messages;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.entity.*;
import com.example.service.*;


/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    @PostMapping("/register")
    public ResponseEntity<Account> addAccount(@RequestBody Account account){
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
    public ResponseEntity<Account> login(@RequestBody Account account){
        if(AccountService.login(account)!=null)
            return ResponseEntity.ok(AccountService.login(account));
        return ResponseEntity.status(401).build();
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> addMessage(@RequestBody Message message){
        int message_length = message.getMessageText().length();

        if(message_length > 0 && message_length < 255){
            MessageService.addMessage(message);
            return ResponseEntity.ok(message);
        }
    }

    @GetMapping("/messages")
    public ResponseEntity<Message[]> getAllMessages()
        {return ResponseEntity.ok(MessageService.getAllMessages());}

    @GetMapping("/messages/{message_id}")
    public ResponseEntity<Message> getMessage(@PathVariable("message_id") int id)
        {return ResponseEntity.ok(MessageService.getMessage(id));}

    @DeleteMapping("/messages/{message_id}")
    public ResponseEntity<Integer> deleteMessage(@PathVariable("message_id") int id ){
        int rows_deleted = MessageService.deleteMessage(id);
        return rows_deleted > 0 ? ResponseEntity.ok(rows_deleted) : ResponseEntity.ok().build(); 
    }

    @PatchMapping("/messages/{message_id}")
    public ResponseEntity<Integer> updateMessage(@RequestBody Message message, @PathVariable("message_id") int id){
        int rows_updated = MessageService.updateMessage(message, id);
        int message_length = message.getMessageText().length();

        if(rows_updated > 0 && message_length > 0 && message_length < 255)
            return ResponseEntity.ok(rows_updated);

        return ResponseEntity.status(400).build();
    }

    @GetMapping("/accounts/{account_id}/messages")
    public ResponseEntity<Message[]> getMessagesFromUser(@PathVariable("account_id") int id)
        {return ResponseEntity.ok(AccountService.getMessagesFromUser(id));}

}
