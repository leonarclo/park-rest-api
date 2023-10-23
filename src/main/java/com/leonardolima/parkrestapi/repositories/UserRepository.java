package com.leonardolima.parkrestapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.leonardolima.parkrestapi.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{    
}
