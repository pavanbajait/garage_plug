package com.garageplug.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.garageplug.entities.CurrentUserSession;

@Repository
public interface CurrentUserSessionDAO extends JpaRepository<CurrentUserSession, Integer>{

	public Optional<CurrentUserSession>findByuuid(String uuid);
	
}
