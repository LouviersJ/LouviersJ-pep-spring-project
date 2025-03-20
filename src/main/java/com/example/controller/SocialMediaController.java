package com.example.controller;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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

    private boolean isValidAccount(Account account){
        return account != null && account.getUsername() != null && !account.getUsername().isEmpty()
            && account.getPassword() != null && account.getPassword().length() >= 4;
    }

    private boolean isValidMessage(String messageText)
        {return messageText != null && !messageText.isBlank() && messageText.length() < 255;}

    @PostMapping("/register")
    public ResponseEntity<?> addAccount(@RequestBody Account account){
        if(isValidAccount(account))
            try{
                accountService.addAccount(account);
                return ResponseEntity.ok(account);
            }catch(DataAccessException e)
                {return ResponseEntity.status(409).build();}

        return ResponseEntity.status(400).build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Account account){
        String username = account.getUsername();
        String password = account.getPassword();

        if(accountService.checkAccount(username, password))
            return ResponseEntity.ok(accountService.findByUsername(username));

        return ResponseEntity.status(401).build();
    }

    @PostMapping("/messages")
    public ResponseEntity<?> addMessage(@RequestBody Message message){
        if(isValidMessage(message.getMessageText())){
            try{
                messageService.addMessage(message);
                return ResponseEntity.ok(message);
            }catch(DataAccessException e)
                {return ResponseEntity.status(400).build();}
        }

        return ResponseEntity.status(400).build();
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages()
        {return ResponseEntity.ok(messageService.getAllMessages());}

    @GetMapping("/messages/{message_id}")
    public ResponseEntity<?> getMessage(@PathVariable("message_id") int id)
        {return ResponseEntity.ok(messageService.getMessage(id));}

    @GetMapping("/accounts/{account_id}/messages")
    public ResponseEntity<List<Message>> getMessagesFromUser(@PathVariable("account_id") int id)
        {return ResponseEntity.ok(messageService.getAccountMessages(id));}

    @DeleteMapping("/messages/{message_id}")
    public ResponseEntity<?> deleteMessage(@PathVariable("message_id") int id ){
        if(messageService.existsByMessageId(id)){
            int rows_deleted = messageService.deleteMessage(id);
            return ResponseEntity.ok(rows_deleted);
        }

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/messages/{message_id}")
    public ResponseEntity<?> updateMessage(@RequestBody Map<String, 
                                            String> mess_object, 
                                            @PathVariable("message_id") int id){
        String message_text = mess_object.get("messageText");

        if(messageService.existsByMessageId(id) & !message_text.isBlank() & message_text.length()  < 255){
            Message old_message = messageService.getMessage(id);
            Message message = new Message(id, 
                                        old_message.getPostedBy(),
                                        message_text,
                                        old_message.getTimePostedEpoch());

            messageService.addMessage(message);

            return ResponseEntity.ok(1);
        }

        return ResponseEntity.status(400).build();
    }
}