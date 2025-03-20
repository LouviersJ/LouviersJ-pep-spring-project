package com.example.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.entity.*;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{
    public <S extends Account> S save(S account);

    public Account findByUsername(String username);

    public boolean existsByUsername(String account);

    public boolean existsByPassword(String password);
}