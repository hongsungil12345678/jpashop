package jpabook.jpashop.Service;

import jpabook.jpashop.Repository.MemberRepository;
import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor//생성자 주입
public class MemberService {
    private final MemberRepository memberRepository;
    /**
     * 회원가입
     */
    @Transactional
    public Long join(Member member){
        validationDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }
    private void validationDuplicateMember(Member member){ //중복회원 검증
        List<Member> findMembers=memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("존재하는 회원");
        }
    }
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }
    public Member findOne(Long memberId) {
        return memberRepository.findMember(memberId);
    }
}
