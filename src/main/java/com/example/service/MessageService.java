package com.example.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.entity.Message;
import com.example.repository.MessageRepository;
import java.util.List;
import javax.transaction.Transactional;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    public Message addMessage(Message message){
        return messageRepository.save(message);
    }

    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    public Message getMessage(int message_id){
        return messageRepository.findByMessageId(message_id);
    }

    @Transactional
    public int deleteMessage(int message_id) {
        return messageRepository.deleteByMessageId(message_id);
    }

    public boolean existsByMessageId(int message_id){
        return messageRepository.existsByMessageId(message_id);
    }

    public List<Message> getAccountMessages(int account_id){
        return messageRepository.findByPostedBy(account_id);
    }
}