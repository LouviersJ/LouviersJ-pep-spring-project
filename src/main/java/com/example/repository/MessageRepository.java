package com.example.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.entity.Message;
import java.util.List;


@Repository
public interface MessageRepository extends JpaRepository<Message, Long>{
    public <S extends Message> S save(S message);

    public List<Message> findAll();

    public Message findByMessageId(int message_id);

    @Modifying
    @Query("DELETE FROM Message m WHERE m.messageId = :messageId")
    public int deleteByMessageId(@Param("messageId") int messageId);

    public boolean existsByMessageId(int message_id);

    public List<Message> findByPostedBy(int account_id);

}