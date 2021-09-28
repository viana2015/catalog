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

import com.jrcg.catalog.dto.ProductDTO;
import com.jrcg.catalog.entities.Product;
import com.jrcg.catalog.repositories.ProductRepository;
import com.jrcg.catalog.services.exceptions.DatabaseException;
import com.jrcg.catalog.services.exceptions.ResourceNotFoundException;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;
	
	@Transactional(readOnly = true)
	public Page<ProductDTO> findAllPaged(PageRequest pageRequest){
		Page<Product>list = repository.findAll(pageRequest);
		return list.map(x -> new ProductDTO(x));
		
	}
	
	@Transactional(readOnly = true)
	public ProductDTO findById( Long id) {
		 Optional<Product> obj = repository.findById(id);
		 Product entity = obj.orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado para este id informado" ));
		 return new ProductDTO(entity, entity.getCategories()); 
		
	}
	@Transactional
	public ProductDTO created(ProductDTO objDTO) {
		Product entity = new Product();
		//entity.setName(objDTO.getName());
		entity = repository.save(entity);
		return new ProductDTO(entity);
		
	}

	@Transactional
	public ProductDTO update(Long id, ProductDTO objDTO) {
		try {
			Product entity = repository.getById(id);
			//entity.setName(objDTO.getName());
			entity = repository.save(entity);
			return new ProductDTO(entity);
			
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
