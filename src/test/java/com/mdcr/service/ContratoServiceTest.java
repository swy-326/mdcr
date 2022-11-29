package com.mdcr.service;

import com.mdcr.exception.ResourceNotFoundException;
import com.mdcr.model.dto.ContratoDTO;
import com.mdcr.model.entity.Contrato;
import com.mdcr.model.repository.ContratoRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ContratoServiceTest {

	@Mock
	private ContratoRepository repository;

	@InjectMocks
	private ContratoService contratoService;

	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void findAll_valid_returnsContratoDTOList(){
		Pageable pageable = PageRequest.of(0, 2);

		Page<Contrato> page = new PageImpl<>(Arrays.asList(
				new Contrato(null, "DOM BASÍLIO", "LAGO ARTIFICIAL, TANQUE, BARREIROS, CANAIS, RESERV.ÁGUA POTAVEL", "06", "2018", "0001", "5", "0502", "9", "5", BigDecimal.valueOf(7778.33), "6050", "34870", "1", "13", BigDecimal.valueOf(0)),
				new Contrato(null, "CAMPO GRANDE", "PRESTAÇÃO DE ASSESORIA TÉCNICA E EMPRESARIAL; CONSULTORIA E ELABORAÇÃO DE PROJETOS E TREINAMENTOS", "09", "2018", "0001", "2", "0502", "9", "2", BigDecimal.valueOf(318), "3120", "24172", "2", "90", BigDecimal.valueOf(0))
		), pageable, 2);

		when(repository.findAll(any(Pageable.class))).thenReturn(page);

		List<ContratoDTO> resultado = contratoService.findAll(pageable);

		Assert.assertEquals(2, resultado.size());
	}

	@Test
	public void findById_validId_returnsContratoDTOwithId(){
		ContratoDTO contratoDTO = new ContratoDTO(1L, "DOM BASÍLIO", "LAGO ARTIFICIAL, TANQUE, BARREIROS, CANAIS, RESERV.ÁGUA POTAVEL", "06", "2018", "0001", "5", "0502", "9", "5", BigDecimal.valueOf(7778.33), "6050", "34870", "1", "13", BigDecimal.valueOf(0));
		Optional<Contrato> contratoOptional = Optional.ofNullable(convertDtoToEntity(contratoDTO));

		when(repository.findById(any(Long.class))).thenReturn(contratoOptional);
		
		ContratoDTO resultado = contratoService.findById(1L);
		Assert.assertEquals(Optional.of(1L), Optional.of(resultado.getId()));
	}

	@Test
	public void findById_invalidId_throwsException(){
		when(repository.findById(any(Long.class))).thenThrow(new ResourceNotFoundException(1L));
		try {
			ContratoDTO resultado = contratoService.findById(1L);
		} catch (Exception e) {
			Assert.assertTrue(e instanceof ResourceNotFoundException);
		}
	}

	@Test
	public void insert_validContrato_returnsNewContratoDTO(){
		ContratoDTO contratoDTO = new ContratoDTO(null, "DOM BASÍLIO", "LAGO ARTIFICIAL, TANQUE, BARREIROS, CANAIS, RESERV.ÁGUA POTAVEL", "06", "2018", "0001", "5", "0502", "9", "5", BigDecimal.valueOf(7778.33), "6050", "34870", "1", "13", BigDecimal.valueOf(0));
		Contrato contrato = convertDtoToEntity(contratoDTO);

		when(repository.save(any(Contrato.class))).thenReturn(contrato);

		ContratoDTO resultado = contratoService.insert(contratoDTO);

		Assert.assertEquals("06", resultado.getMesEmissao());
	}

	@Test(expected = IllegalArgumentException.class)
	public void insert_newContratoWithId_throwsIllegalArgumentException(){
		ContratoDTO contratoDTO = new ContratoDTO(1L, "DOM BASÍLIO", "LAGO ARTIFICIAL, TANQUE, BARREIROS, CANAIS, RESERV.ÁGUA POTAVEL", "06", "2018", "0001", "5", "0502", "9", "5", BigDecimal.valueOf(7778.33), "6050", "34870", "1", "13", BigDecimal.valueOf(0));
		contratoService.insert(contratoDTO);
	}

	@Test
	public void delete_validId_returnsVoid(){
		ContratoDTO contratoDTO = new ContratoDTO(null, "DOM BASÍLIO", "LAGO ARTIFICIAL, TANQUE, BARREIROS, CANAIS, RESERV.ÁGUA POTAVEL", "06", "2018", "0001", "5", "0502", "9", "5", BigDecimal.valueOf(7778.33), "6050", "34870", "1", "13", BigDecimal.valueOf(0));
		Contrato contrato = convertDtoToEntity(contratoDTO);

		contratoService.delete(1L);
		verify(repository, times(1)).deleteById(1L);
	}

	@Test(expected = ResourceNotFoundException.class)
	public void delete_invalidId_throwsException(){
		doThrow(new EmptyResultDataAccessException(1)).when(repository).deleteById(1L);
		contratoService.delete(1L);
	}

	@Test
	public void update_validId_returnsUpdatedContratoDTO(){
		ContratoDTO contratoDTO = new ContratoDTO(null, "DOM BASÍLIO", "LAGO ARTIFICIAL, TANQUE, BARREIROS, CANAIS, RESERV.ÁGUA POTAVEL", "06", "2018", "0001", "5", "0502", "9", "5", BigDecimal.valueOf(7778.33), "6050", "34870", "1", "13", BigDecimal.valueOf(0));
		Contrato contrato = convertDtoToEntity(contratoDTO);

		when(repository.getOne(1L)).thenReturn(contrato);

		ContratoDTO resultado = contratoService.update(1L, contratoDTO);
		Assert.assertEquals(resultado, contratoDTO);
	}

	@Test
	public void update_invalidId_throwsException(){
		ContratoDTO contratoDTO = new ContratoDTO(null, "DOM BASÍLIO", "LAGO ARTIFICIAL, TANQUE, BARREIROS, CANAIS, RESERV.ÁGUA POTAVEL", "06", "2018", "0001", "5", "0502", "9", "5", BigDecimal.valueOf(7778.33), "6050", "34870", "1", "13", BigDecimal.valueOf(0));
		when(repository.getOne(1L)).thenThrow(new ResourceNotFoundException(1L));

		try {
			contratoService.update(1L, contratoDTO);
		} catch (Exception e) {
			Assert.assertTrue(e instanceof ResourceNotFoundException);
		}
	}

	@Test
	public void findByAno_validAno_returnsBigDecimal(){
		ContratoDTO contratoDTO = new ContratoDTO(null, "DOM BASÍLIO", "LAGO ARTIFICIAL, TANQUE, BARREIROS, CANAIS, RESERV.ÁGUA POTAVEL", "06", "2018", "0001", "5", "0502", "9", "5", BigDecimal.valueOf(7778.33), "6050", "34870", "1", "13", BigDecimal.valueOf(0));
		Contrato contrato = convertDtoToEntity(contratoDTO);

		when(repository.findByAno("2018")).thenReturn(contratoDTO.getVlCusteio());

		BigDecimal resultado = contratoService.findByAno("2018");
		Assert.assertEquals(BigDecimal.valueOf(7778.33), resultado);
	}

	@Test
	public void findByAno_invalidAno_returnsNull(){
		when(repository.findByAno("0000")).thenReturn(null);

		BigDecimal resultado = contratoService.findByAno("0000");
		Assert.assertEquals(null, resultado);
	}

	@Test
	public void findCustomFields_validParameters_returnsContratoDTOList(){
		List<Contrato> contratos = Arrays.asList(new Contrato(null, "DOM BASÍLIO", "LAGO ARTIFICIAL, TANQUE, BARREIROS, CANAIS, RESERV.ÁGUA POTAVEL", "06", "2018", "0001", "5", "0502", "9", "5", BigDecimal.valueOf(7778.33), "6050", "34870", "1", "13", BigDecimal.valueOf(0)));

		when(repository.findCustomFields(null, "DOM BASÍLIO", null, null, null, null, null, null, null, null, null, null, null, null, null))
				.thenReturn(contratos);

		List<ContratoDTO> resultado = contratoService.findCustomFields(null, "DOM BASÍLIO", null, null, null, null, null, null, null, null, null, null, null, null, null);
		Assert.assertEquals(1, resultado.size());
	}

	private Contrato convertDtoToEntity(ContratoDTO obj){
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(obj, Contrato.class);
	}
}