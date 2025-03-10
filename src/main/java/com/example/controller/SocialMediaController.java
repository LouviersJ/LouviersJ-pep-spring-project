package com.example.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    @Autowired
    private MessageService messageService;
    @Autowired
    private AccountService accountService;

    @PostMapping("/register")
    public ResponseEntity<Account> addAccount(@RequestBody Account account){
        if(account.getUsername().length() > 0 && account.getPassword().length() >= 4)
            try{
                accountService.addAccount(account);
                return ResponseEntity.ok(account);
            }catch(DataAccessException e){
                return ResponseEntity.status(409).build();
            }

        return ResponseEntity.status(400).build();
    }

    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account account){
        String username = account.getUsername();
        String password = account.getPassword();

        boolean account_exists = accountService.checkAccount(username, password);

        if(account_exists)
            return ResponseEntity.ok(accountService.findByUsername(username));
        return ResponseEntity.status(401).build();
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> addMessage(@RequestBody Message message){
        int message_length = message.getMessageText().length();

        if(message_length > 0 && message_length < 255){
            messageService.addMessage(message);
            return ResponseEntity.ok(message);
        }
        return ResponseEntity.status(400).build();
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages()
        {return ResponseEntity.ok(messageService.getAllMessages());}

    @GetMapping("/messages/{message_id}")
    public ResponseEntity<Message> getMessage(@PathVariable("message_id") int id)
        {return ResponseEntity.ok(messageService.getMessage(id));}

    @DeleteMapping("/messages/{message_id}")
    public ResponseEntity<Integer> deleteMessage(@PathVariable("message_id") int id ){
        int rows_deleted = messageService.deleteMessage(id);
        return rows_deleted > 0 ? ResponseEntity.ok(rows_deleted) : ResponseEntity.ok().build(); 
    }

    @PatchMapping("/messages/{message_id}")
    public ResponseEntity<Integer> updateMessage(@RequestBody String message_text, @PathVariable("message_id") int id){
        int message_length = message_text.length();

        if(messageService.existsByMessageId(id) && message_length > 0 && message_length < 255)
            return ResponseEntity.ok(1);

        return ResponseEntity.status(400).build();
    }

    @GetMapping("/accounts/{account_id}/messages")
    public ResponseEntity<List<Message>> getMessagesFromUser(@PathVariable("account_id") int id)
        {return ResponseEntity.ok(messageService.getAccountMessages(id));}

}
