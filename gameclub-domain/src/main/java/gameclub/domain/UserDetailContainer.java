package gameclub.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class UserDetailContainer implements UserDetails {

    private Player player;

    public UserDetailContainer(Player player) {
        this.player = player;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<Role> roles = new ArrayList<>();
        if(player.getRoles().contains(Role.ROLE_PLAYER)){
            roles.add(Role.ROLE_PLAYER);
        }
        if(player.getRoles().contains(Role.ROLE_SUPERUSER)){
            roles.add(Role.ROLE_SUPERUSER);
        }
        if(player.getRoles().contains(Role.ROLE_GROUP_ADMIN)){
            roles.add(Role.ROLE_GROUP_ADMIN);
        }
        return roles;
    }

    @Override
    public String getPassword() {
        return player.password;
    }

    @Override
    public String getUsername() {
        return player.loginName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
