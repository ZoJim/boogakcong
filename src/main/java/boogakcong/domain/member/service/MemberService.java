package boogakcong.domain.member.service;

import boogakcong.domain.member.entity.Member;
import boogakcong.domain.member.repository.MemberRepository;
import boogakcong.global.exception.BusinessError;
import boogakcong.global.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;

    public Member getMemberById(Long id) {
        return memberRepository.findById(id).orElseThrow(() -> new BusinessException(BusinessError.MEMBER_NOT_FOUND));
    }

    @Transactional
    public Member createMember(Member member) {
        return memberRepository.save(member);
    }

    public void validateDuplicateEmail(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new BusinessException(BusinessError.MEMBER_DUPLICATE_EMAIL);
        }
    }

    public Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(() -> new BusinessException(BusinessError.MEMBER_NOT_FOUND));
    }

    @Transactional
    public void updateMember(Member member) {
        memberRepository.save(member);
    }

    @Transactional
    public void confirmCaffeManager(Member member) {
        member.confirmCafeOwner();
        memberRepository.save(member);
    }
}
