package cn.rmfield.website.repository;

import cn.rmfield.website.entity.RfUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<RfUser,Integer> {
    RfUser findByUsername(String username);
}
