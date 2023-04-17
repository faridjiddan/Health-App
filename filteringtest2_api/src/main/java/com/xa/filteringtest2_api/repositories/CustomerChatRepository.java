package com.xa.filteringtest2_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xa.filteringtest2_api.models.CustomerChat;

public interface CustomerChatRepository extends JpaRepository<CustomerChat,Long> {
    
}
