package com.ervin.myblog.security;

import com.ervin.myblog.entity.User;
import com.ervin.myblog.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = userRepository.getUser(name);
        if (user == null) {
            throw new UsernameNotFoundException("No user found with name " + name);
        }

        return new AccountUserDetails(user);
    }
}
