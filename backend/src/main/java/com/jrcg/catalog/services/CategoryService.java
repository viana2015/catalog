package com.jrcg.catalog.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jrcg.catalog.dto.CategoryDTO;
import com.jrcg.catalog.entities.Category;
import com.jrcg.catalog.repositories.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository repository;
	
	//@Transactional(readOnly = true)
	public List<CategoryDTO> findAll(){
		List<Category>list = repository.findAll();
		List<CategoryDTO>listDTO = list.stream().map(x -> new CategoryDTO(x)).collect(Collectors.toList());
		return listDTO;
	}
}
