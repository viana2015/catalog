package com.jrcg.catalog.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jrcg.catalog.dto.CategoryDTO;
import com.jrcg.catalog.entities.Category;
import com.jrcg.catalog.repositories.CategoryRepository;
import com.jrcg.catalog.services.exceptions.DatabaseException;
import com.jrcg.catalog.services.exceptions.ResourceNotFoundException;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository repository;
	
	@Transactional(readOnly = true)
	public Page<CategoryDTO> findAllPaged(PageRequest pageRequest){
		Page<Category>list = repository.findAll(pageRequest);
		return list.map(x -> new CategoryDTO(x));
		
	}
	
	@Transactional(readOnly = true)
	public CategoryDTO findById( Long id) {
		 Optional<Category> obj = repository.findById(id);
		 Category entity = obj.orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada para este id informado" ));
		 return new CategoryDTO(entity); 
		
	}
	@Transactional
	public CategoryDTO created(CategoryDTO objDTO) {
		Category entity = new Category();
		entity.setName(objDTO.getName());
		entity = repository.save(entity);
		return new CategoryDTO(entity);
		
	}

	@Transactional
	public CategoryDTO update(Long id, CategoryDTO objDTO) {
		try {
			Category entity = repository.getById(id);
			entity.setName(objDTO.getName());
			entity = repository.save(entity);
			return new CategoryDTO(entity);
			
		} catch (EntityNotFoundException ex) {
			 throw new ResourceNotFoundException("Id informádo não consta no banco de dados " + id);
		}
		
	}

	public void delete(Long id) {
		
		try {
			repository.deleteById(id);
			
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id informádo não consta no banco de dados" + id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Não foi possivél deletear pois já existe produto associados a esta categoria!");
		}
	}
}
