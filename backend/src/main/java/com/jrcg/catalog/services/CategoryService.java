package com.jrcg.catalog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jrcg.catalog.dto.CategoryDTO;
import com.jrcg.catalog.entities.Category;
import com.jrcg.catalog.repositories.CategoryRepository;
import com.jrcg.catalog.services.exceptions.EntityNotFoundException;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository repository;
	
	@Transactional(readOnly = true)
	public List<CategoryDTO> findAll(){
		List<Category>list = repository.findAll();
		List<CategoryDTO>listDTO = list.stream().map(x -> new CategoryDTO(x)).collect(Collectors.toList());
		return listDTO;
	}
	
	@Transactional(readOnly = true)
	public CategoryDTO findById( Long id) {
		 Optional<Category> obj = repository.findById(id);
		 Category entity = obj.orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada para este id informado" ));
		 return new CategoryDTO(entity);
		 
		 
		 
		
	}
}
