package com.ak.jpa_example.services;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ak.jpa_example.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
