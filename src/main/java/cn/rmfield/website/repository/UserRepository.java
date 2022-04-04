package cn.rmfield.website.repository;

import cn.rmfield.website.entity.RfUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<RfUser,Integer> {
    RfUser findByUsername(String username);
    List<RfUser> findByRealname(String realname);
}
