package org.sopt.lequuServer.global.auth.redis;

import org.springframework.data.repository.CrudRepository;

public interface TokenRepository extends CrudRepository<RefreshToken, String> {
}