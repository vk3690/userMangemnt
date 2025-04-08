package com.user.management.repo;

import com.user.management.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailsRepo  extends JpaRepository<Users,Long> {

    Users findByUsername(String username);
}
