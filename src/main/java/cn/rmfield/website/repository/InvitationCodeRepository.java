package cn.rmfield.website.repository;

import cn.rmfield.website.entity.InvitationCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvitationCodeRepository extends JpaRepository<InvitationCode,Integer> {
    InvitationCode findByCode(String code);
}
