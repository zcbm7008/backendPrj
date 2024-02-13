package webshop.springconfig.security;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import webshop.user.domain.member.CustomMemberDetails;
import webshop.user.domain.member.Member;
import webshop.repository.MemberRepository;

import java.util.ArrayList;
import java.util.Collection;

@Service
@AllArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        Member member = memberRepository.findOneByName(loginId).orElseThrow(() -> new UsernameNotFoundException("not found loginId : " + loginId));

        CustomMemberDetails customMemberDetails = new CustomMemberDetails();

        customMemberDetails.setId(String.valueOf(member.getId()));
        customMemberDetails.setName(member.getName());
        customMemberDetails.setPassword(member.getPassword());
        customMemberDetails.setBlocked(member.isBlocked());

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return "ROLE_USER";
            }
        });

        customMemberDetails.setAuthorities(authorities);

        return customMemberDetails;
    }
}
