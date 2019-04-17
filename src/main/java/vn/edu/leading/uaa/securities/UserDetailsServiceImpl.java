package vn.edu.leading.uaa.securities;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.edu.leading.uaa.models.RoleModel;
import vn.edu.leading.uaa.models.UserModel;
import vn.edu.leading.uaa.repositories.UserRepository;


import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserModel userModel = userRepository.findByUsername(s).orElse(null);
        if (userModel == null) {
            throw new UsernameNotFoundException("not_found");
        }
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (RoleModel roleModel : userModel.getRoleModels()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(roleModel.getName()));
        }
        return new User(userModel.getUsername(),userModel.getPassword(),grantedAuthorities);
    }
}
