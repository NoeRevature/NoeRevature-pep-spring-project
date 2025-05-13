package com.example.service;

import org.springframework.stereotype.Service;

import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import com.example.entity.*;

import java.util.List;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final AccountRepository accountRepository;

    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository){
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    public Message createMessage(Message message){
        if(message.getMessageText().isEmpty()) return null;
        if(message.getMessageText().length() > 255) return null;
        if(accountRepository.findAccountByAccountId(message.getPostedBy()) == null) return null;

        return messageRepository.save(message);
    }

    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    public Message getMessageByMessageId(int messageId){
        return messageRepository.findMessageByMessageId(messageId);
    }

    public int deleteMessage(int messageId){
        if(messageRepository.existsById(messageId)){
            messageRepository.deleteById(messageId);
            return 1;
        }

        return 0; 
    }

    public Message updateMessage(Message message, int messageId){
        if(message.getMessageText().isEmpty()) return null;
        if(message.getMessageText().length() > 255) return null;
        if(!messageRepository.existsById(messageId)) return null;

        Message newMessage = messageRepository.findMessageByMessageId(messageId);

        message.setMessageText(message.getMessageText());
        
        return messageRepository.save(newMessage);
    }

    public List<Message> getAllMessageFromAccount(int accountId){
        return messageRepository.findByPostedBy(accountId);
    }
}
