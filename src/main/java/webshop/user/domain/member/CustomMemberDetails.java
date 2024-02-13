package webshop.user.domain.member;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;


@Setter
@Getter
public class CustomMemberDetails implements UserDetails, Serializable {

    private String id;
    private String loginId;
    private String name;
    private String password;
    private boolean blocked;

    private Collection<GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return authorities;
    }

    @Override
    public String getPassword(){
        return password;

    }

    @Override
    public String getUsername(){
        return name;
    }

    @Override
    public boolean isAccountNonExpired(){
        return true;
    }

    @Override
    public boolean isAccountNonLocked(){
        return !blocked;
    }

    @Override
    public boolean isCredentialsNonExpired(){
        return true;
    }

    @Override
    public boolean isEnabled(){
        return !blocked;
    }
}
