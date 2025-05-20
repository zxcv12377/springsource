package com.example.movie.service;

import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.movie.dto.AuthMemberDTO;
import com.example.movie.dto.MemberDTO;
import com.example.movie.dto.PasswordDTO;
import com.example.movie.entity.Member;
import com.example.movie.repository.MemberRepository;
import com.example.movie.repository.ReviewRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class CustomMemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    private final ReviewRepository reviewRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원탈퇴
    @Transactional
    public void leaveMember(MemberDTO dto) {
        // 비밀번호가 일치하는지 확인
        // 일치 => 리뷰제거, 회원탈퇴
        Member member = memberRepository.findByEmail(dto.getEmail());
        if (!passwordEncoder.matches(dto.getPassword(), member.getPassword())) {
            throw new IllegalStateException("비밀번호가 다릅니다.");
        } else {
            reviewRepository.deleteByMember(member);
            memberRepository.delete(member);
        }
    }

    // 닉네임 변경
    @Transactional
    public void updateNickname(MemberDTO dto) {
        // Member member = memberRepository.findByEmail(null);
        // member.changeNickname("null");
        // memberRepository.save(member);
        memberRepository.updateNickname(dto.getNickname(), dto.getEmail());
    }

    // 비밀번호 변경
    public void updatePassword(PasswordDTO dto) throws IllegalStateException {

        Member memberrrr = memberRepository.findByEmail(dto.getEmail());
        if (!passwordEncoder.matches(dto.getCurrentPassword(), memberrrr.getPassword())) {
            throw new IllegalStateException("현재 비밀번호가 다릅니다");
        } else {
            memberrrr.changePassword(passwordEncoder.encode(dto.getNewPassword()));
            memberRepository.save(memberrrr);
        }

    }

    public void register(MemberDTO dto) throws IllegalStateException {
        validateEmail(dto.getEmail());

        Member member = Member.builder()
                .mid(dto.getMid())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .nickname(dto.getNickname())
                .memberRole(dto.getMemberRole())
                .build();

        memberRepository.save(member);
    }

    private void validateEmail(String Email) {
        Member member = memberRepository.findByEmail(Email);

        if (member != null) {
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }

    // 로그인 처리
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("username anaaa {}", email);

        Member member = memberRepository.findByEmail(email);
        if (member == null)
            throw new UsernameNotFoundException("이메일 확인");

        MemberDTO memberDTO = MemberDTO.builder()
                .email(member.getEmail())
                .password(member.getPassword())
                .nickname(member.getNickname())
                .memberRole(member.getMemberRole())
                .build();

        log.info("email, memberDTO / {} / {}", email, memberDTO);
        AuthMemberDTO authMemberDTO = new AuthMemberDTO(memberDTO);

        return authMemberDTO;
    }

}
