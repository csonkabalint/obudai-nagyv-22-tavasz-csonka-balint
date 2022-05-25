package gameclub.domain;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_SUPERUSER,
    ROLE_GROUP_ADMIN,
    ROLE_PLAYER;

    @Override
    public String getAuthority() {
        return name();
    }
}
