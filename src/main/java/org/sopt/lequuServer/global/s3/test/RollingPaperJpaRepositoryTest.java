package org.sopt.lequuServer.global.s3.test;

import org.sopt.lequuServer.domain.rollingpaper.model.RollingPaper;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RollingPaperJpaRepositoryTest extends JpaRepository<RollingPaper, Long> {

}