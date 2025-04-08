package com.user.monitor.repo;

import com.user.monitor.entity.UserMonitor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMonitorRepo extends JpaRepository<UserMonitor,Long> {
}
