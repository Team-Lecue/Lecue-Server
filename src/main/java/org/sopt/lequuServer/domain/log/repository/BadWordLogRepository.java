package org.sopt.lequuServer.domain.log.repository;

import org.sopt.lequuServer.domain.log.model.BadWordLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BadWordLogRepository extends JpaRepository<BadWordLog, Long> {
}
