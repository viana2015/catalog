package com.jrcg.catalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jrcg.catalog.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
