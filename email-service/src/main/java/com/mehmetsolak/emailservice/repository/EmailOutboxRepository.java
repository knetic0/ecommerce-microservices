package com.mehmetsolak.emailservice.repository;

import com.mehmetsolak.emailservice.entity.EmailOutbox;
import com.mehmetsolak.emailservice.enums.EmailStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmailOutboxRepository extends JpaRepository<EmailOutbox, Long> {
    List<EmailOutbox> findByStatusAndRetryCountLessThan(EmailStatus status, int maxRetryCount);
}