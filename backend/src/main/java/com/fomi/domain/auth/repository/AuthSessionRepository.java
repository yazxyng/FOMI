package com.fomi.domain.auth.repository;
import com.fomi.domain.auth.entity.AuthSession;
import java.util.*;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
public interface AuthSessionRepository extends JpaRepository<AuthSession,UUID> {
 Optional<AuthSession> findByAccessHash(String hash);
 @Lock(LockModeType.PESSIMISTIC_WRITE)
 @Query("select s from AuthSession s where s.refreshHash = :hash")
 Optional<AuthSession> lockByRefreshHash(@Param("hash") String hash);
}
