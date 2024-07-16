package com.amancode.blogappserver.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.amancode.blogappserver.Entities.User;
import com.amancode.blogappserver.Exceptions.ResourceNotFoundException;
import com.amancode.blogappserver.Repositories.UserRepo;

@Service
public class CustomUserDetailService implements UserDetailsService{

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //loading user from database by username
        User user= this.userRepo.findByEmail(username).orElseThrow(()-> new ResourceNotFoundException("User"," Email : "+username, 0));		

        return user;

    }


    
}
