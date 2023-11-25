package com.example.bilda_server.repository;

import com.example.bilda_server.domain.entity.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PageRepository extends JpaRepository<Page, Long> {

}
