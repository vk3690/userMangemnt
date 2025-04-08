package com.user.monitor.repo;

import com.user.monitor.entity.UsersInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailsRepo  extends JpaRepository<UsersInfo,Long> {

    UsersInfo findByUsername(String username);
}
