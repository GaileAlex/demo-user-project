package org.example.demo.demoproject.securety;

import lombok.RequiredArgsConstructor;
import org.example.demo.demoproject.entity.UserEntity;
import org.example.demo.demoproject.repository.UserRepository;
import org.example.demo.demoproject.securety.model.UserDetail;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Aleksei Gaile 05-Feb-24
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String data) throws UsernameNotFoundException {
        UserEntity user = userRepository.findUserByPhoneOrEmail(data)
                .orElseThrow(() ->   new AuthenticationCredentialsNotFoundException("user not found"));

        return UserDetail.build(user);
    }

    @Transactional
    public UserDetail loadUserById(Long id) throws UsernameNotFoundException {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() ->   new AuthenticationCredentialsNotFoundException("user not found"));

        return UserDetail.build(user);
    }

}
