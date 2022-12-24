package com.elandt.secondary.config.configtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.elandt.secondary.config.configtest.domain.Demo;

public interface DemoRepository extends JpaRepository<Demo, Integer>{

}
