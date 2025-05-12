package com.example.board.security;

import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.board.entity.Member;
import com.example.board.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class CustomMemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    // 로그인 처리
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("username anaaa {}", username);

        Member member = memberRepository.findByEmailAndFromSocial(username, false);
        if (member == null)
            throw new UsernameNotFoundException("이메일 확인");

        AuthMemberDTO AuthMemberDTO = new AuthMemberDTO(member.getEmail(),
                member.getPassword(), member.isFromSocial(),
                member.getRoleSet().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                        .collect(Collectors.toList()));

        AuthMemberDTO.setName(member.getName());
        AuthMemberDTO.setFromSocial(member.isFromSocial());

        return AuthMemberDTO;
    }

}
