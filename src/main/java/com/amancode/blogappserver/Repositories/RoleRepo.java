package com.amancode.blogappserver.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amancode.blogappserver.Entities.Role;

public interface RoleRepo extends JpaRepository<Role, Integer>{
    
}
