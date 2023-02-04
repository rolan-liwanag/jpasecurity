package com.liwanag.jpasecurity.repository;

import com.liwanag.jpasecurity.model.Authority;
import com.liwanag.jpasecurity.model.AuthorityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, AuthorityId>  {

    @Query("select a from Authority a where a.username = :#{#username}")
    List<Authority> findAuthoritiesByUsername(@Param("username") String username);
}
