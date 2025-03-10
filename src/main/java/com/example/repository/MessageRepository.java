package com.example.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.entity.Message;
import java.util.List;


@Repository
public interface MessageRepository extends JpaRepository<Message, Long>{
    public <S extends Message> S save(S message);

    public List<Message> findAll();

    public Message findByMessageId(int message_id);

    public int deleteByMessageId(int message_id);

    public boolean existsByMessageId(int message_id);

    public List<Message> findByPostedBy(int account_id);

}
