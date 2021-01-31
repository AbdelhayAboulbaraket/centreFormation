package com.projetDeSemestre.centreDeFormation.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projetDeSemestre.centreDeFormation.entities.User;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findByUsername(String username);
}
