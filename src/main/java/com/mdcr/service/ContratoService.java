package com.mdcr.service;

import com.mdcr.exception.ResourceNotFoundException;
import com.mdcr.model.dto.ContratoDTO;
import com.mdcr.model.entity.Contrato;
import com.mdcr.model.repository.ContratoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContratoService {

	@Autowired
	private ContratoRepository repository;

	public List<ContratoDTO> findAll(Pageable pageable){
		return repository.findAll(pageable).stream().map(ContratoDTO::create).collect(Collectors.toList());
	}

	public ContratoDTO findById(Long id){
		Optional<Contrato> obj = repository.findById(id);
		return obj.map(ContratoDTO::create).orElseThrow(() -> new ResourceNotFoundException(id));
	}

	public BigDecimal findByAno(String ano){
		return repository.findByAno(ano);
	}

	public List<ContratoDTO> findCustomFields(String municipio, String nomeProduto, String mesEmissao, String anoEmissao, String cdPrograma, String cdSubPrograma, String cdFonteRecurso, String cdTipoSeguro, String cdEstado, BigDecimal vlCusteio, String cdProduto, String cdMunicipio, String atividade, String cdModalidade, BigDecimal areaInvest){
		return repository.findCustomFields(municipio, nomeProduto, mesEmissao, anoEmissao, cdPrograma, cdSubPrograma, cdFonteRecurso, cdTipoSeguro, cdEstado, vlCusteio, cdProduto, cdMunicipio, atividade, cdModalidade, areaInvest)
				.stream().map(ContratoDTO::create).collect(Collectors.toList());
	}

	public ContratoDTO insert(ContratoDTO obj) {
		if (obj.getId() != null) throw new IllegalArgumentException("ID nao pode ser atribuido manualmente");
		return ContratoDTO.create(repository.save(convertDtoToEntity(obj)));
	}

	public void delete (Long id) {
		try {
			repository.deleteById(id);
		}
		catch (EmptyResultDataAccessException e){
			throw new ResourceNotFoundException(id);
		}
	}

	public ContratoDTO update(Long id, ContratoDTO obj){
		try {
			Contrato entity = repository.getOne(id);

			ModelMapper modelMapper = new ModelMapper();
			modelMapper.map(obj, entity);
			entity.setId(id); // ignora mudanca de id
			repository.save(entity);

			return ContratoDTO.create(entity);
		} catch (RuntimeException e){
			throw new ResourceNotFoundException(id);
		}
	}

	private Contrato convertDtoToEntity(ContratoDTO obj){
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(obj, Contrato.class);
	}
}