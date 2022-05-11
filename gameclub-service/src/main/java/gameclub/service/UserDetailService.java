package gameclub.service;

import gameclub.domain.Player;
import gameclub.domain.UserDetailContainer;
import gameclub.persistence.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailService implements UserDetailsService {

    @Autowired
    PlayerRepository playerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Player player =  playerRepository.findByLoginName(username);

        return new UserDetailContainer(player);
    }
}
