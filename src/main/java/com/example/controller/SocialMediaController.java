package com.example.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

    private final AccountService accountService;
    private final MessageService messageService;

    @Autowired  // Constructor injection
    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }
    
    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<Account> registerAccount(@RequestBody Account account){
        Account newAccount = accountService.createAccount(account);
        if (newAccount == null) return new ResponseEntity<>(account, HttpStatus.BAD_REQUEST);
        if (newAccount.getPassword().equals("-1")) return new ResponseEntity<>(account, HttpStatus.CONFLICT);
        
        return new ResponseEntity<>(newAccount, HttpStatus.OK);
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<Account> loginAccount(@RequestBody Account account){
        Account newAccount = accountService.findAccountByUsernameAndPassword(account);
        if(newAccount == null) return new ResponseEntity<>(account, HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(newAccount, HttpStatus.OK);
    }

    @PostMapping("/messages")
    @ResponseBody
    public ResponseEntity<Message> createMessage(@RequestBody Message message){
        Message newMessage = messageService.createMessage(message);
        if(newMessage == null) return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(newMessage, HttpStatus.OK);
    }

    @GetMapping("/messages")
    @ResponseBody
    public List<Message> getAllMessages(){
        return messageService.getAllMessages();
    }

    @GetMapping("/messages/{messageId}")
    @ResponseBody
    public ResponseEntity<Message> getMessageById(@PathVariable int messageId){
        return new ResponseEntity<>(messageService.getMessageByMessageId(messageId), HttpStatus.OK);
    }
    
    @DeleteMapping("/messages/{messageId}")
    @ResponseBody
    public ResponseEntity<Integer> deleteMessageById(@PathVariable int messageId){
        if(messageService.deleteMessage(messageId) == 1){
            return new ResponseEntity<>(1, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PatchMapping("/messages/{messageId}")
    @ResponseBody
    public ResponseEntity<Integer> updateMessage(@RequestBody Message message, @PathVariable int messageId){
        Message newMessage = messageService.updateMessage(message, messageId);

        if(newMessage == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(1, HttpStatus.OK);
    }

    @GetMapping("/accounts/{accountId}/messages")
    @ResponseBody
    public List<Message> getMessagesFromAccountId(@PathVariable int accountId){
        return messageService.getAllMessageFromAccount(accountId);
    }

}
