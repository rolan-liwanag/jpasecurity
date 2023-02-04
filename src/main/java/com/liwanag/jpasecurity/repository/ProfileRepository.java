package com.liwanag.jpasecurity.repository;

import com.liwanag.jpasecurity.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, String> {
}
