package com.mdcr.controller;

import com.mdcr.client.ContratoFeignClient;
import com.mdcr.model.dto.ContratoDTO;
import com.mdcr.model.dto.ResponseDTO;
import com.mdcr.service.ContratoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(ContratoController.class)
@AutoConfigureMockMvc
public class ContratoControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private ContratoService contratoService;

	@MockBean
	private ContratoFeignClient contratoFeignClient;

	@Test
	public void getResponse() throws Exception {
		ContratoDTO contratoDTO1 = new ContratoDTO(1L, "DOM BASÍLIO", "\"LAGO ARTIFICIAL, TANQUE, BARREIROS, CANAIS, RESERV.ÁGUA POTAVEL\"", "06", "2018", "0001", "5", "0502", "9", "5", BigDecimal.valueOf(7778.33), "6050", "34870", "1", "13", BigDecimal.valueOf(0));
		ContratoDTO contratoDTO2 = new ContratoDTO(1L, "DOM BASÍLIO", "\"LAGO ARTIFICIAL, TANQUE, BARREIROS, CANAIS, RESERV.ÁGUA POTAVEL\"", "06", "2018", "0001", "5", "0502", "9", "5", BigDecimal.valueOf(7778.33), "6050", "34870", "1", "13", BigDecimal.valueOf(0));
		ResponseDTO responseDTO = new ResponseDTO(1L, "string", Arrays.asList(contratoDTO1, contratoDTO2));

		when(contratoFeignClient.getResponse()).thenReturn(responseDTO);
		when(contratoService.insert(any(ContratoDTO.class))).thenReturn(new ContratoDTO());

		RequestBuilder request = MockMvcRequestBuilders.get("/api/contratos/onboarding")
				.accept(MediaType.APPLICATION_JSON);

		mvc.perform(request)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.*", hasSize(2)))
				.andExpect(jsonPath("$[0].Municipio").value("DOM BASÍLIO"));
	}

	@Test
	public void findAll() throws Exception {
		when(contratoService.findAll(any(Pageable.class))).thenReturn(Arrays.asList(new ContratoDTO(), new ContratoDTO(), new ContratoDTO()));

		RequestBuilder request = MockMvcRequestBuilders.get("/api/contratos")
				.accept(MediaType.APPLICATION_JSON)
				.param("size", "3")
				.param("page", "0");

		mvc.perform(request)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.*", hasSize(3)));
	}

	@Test
	public void findById() throws Exception {
		ContratoDTO contratoDTO = new ContratoDTO(1L, "DOM BASÍLIO", "LAGO ARTIFICIAL, TANQUE, BARREIROS, CANAIS, RESERV.ÁGUA POTAVEL", "06", "2018", "0001", "5", "0502", "9", "5", BigDecimal.valueOf(7778.33), "6050", "34870", "1", "13", BigDecimal.valueOf(0));
		when(contratoService.findById(any(Long.class))).thenReturn(contratoDTO);

		RequestBuilder request = MockMvcRequestBuilders.get("/api/contratos/id/{id}", 1L)
				.accept(MediaType.APPLICATION_JSON);

		mvc.perform(request)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value("1"))
				.andExpect(jsonPath("$.AnoEmissao").value("2018"));
	}

	@Test
	public void findByAno() throws Exception {
		when(contratoService.findByAno(any(String.class))).thenReturn(BigDecimal.valueOf(1000.00));

		RequestBuilder request = MockMvcRequestBuilders.get("/api/contratos/ano/{ano}", "2018")
				.accept(MediaType.APPLICATION_JSON);

		mvc.perform(request)
				.andExpect(status().isOk())
				.andExpect(content().string("1000.0"));
	}

	@Test
	public void findCustomFields() throws Exception {
		ContratoDTO contratoDTO = new ContratoDTO(1L, "DOM BASÍLIO", "LAGO ARTIFICIAL, TANQUE, BARREIROS, CANAIS, RESERV.ÁGUA POTAVEL", "06", "2018", "0001", "5", "0502", "9", "5", BigDecimal.valueOf(7778.33), "6050", "34870", "1", "13", BigDecimal.valueOf(0));

		when(contratoService.findCustomFields(null, null, null, "2018", null, null, null, null, null, null, null, null, null, null, null))
				.thenReturn(Arrays.asList(contratoDTO));

		RequestBuilder request = MockMvcRequestBuilders.get("/api/contratos/search")
				.param("anoEmissao", "2018")
				.accept(MediaType.APPLICATION_JSON);

		mvc.perform(request)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id").value("1"))
				.andExpect(jsonPath("$[0].nomeProduto").value("LAGO ARTIFICIAL, TANQUE, BARREIROS, CANAIS, RESERV.ÁGUA POTAVEL"))
				.andExpect(jsonPath("$[0].cdPrograma").value("0001"));
	}

	@Test
	public void post() throws Exception {
		String json = "{" +
				"\"Municipio\": \"DOM BASÍLIO\","+
				"\"nomeProduto\": \"LAGO ARTIFICIAL, TANQUE, BARREIROS, CANAIS, RESERV.ÁGUA POTAVEL\","+
				"\"MesEmissao\": \"06\","+
				"\"AnoEmissao\": \"2018\","+
				"\"cdPrograma\": \"0001\","+
				"\"cdSubPrograma\": \"5\","+
				"\"cdFonteRecurso\": \"0502\","+
				"\"cdTipoSeguro\": \"9\","+
				"\"cdEstado\": \"5\","+
				"\"VlCusteio\": 7778.33,"+
				"\"cdProduto\": \"6050\","+
				"\"cdMunicipio\": \"34870\","+
				"\"Atividade\": \"1\","+
				"\"cdModalidade\": \"13\","+
				"\"AreaInvest\": 0" +
				"}";

		ContratoDTO contratoDTO = new ContratoDTO(1L, "DOM BASÍLIO", "LAGO ARTIFICIAL, TANQUE, BARREIROS, CANAIS, RESERV.ÁGUA POTAVEL", "06", "2018", "0001", "5", "0502", "9", "5", BigDecimal.valueOf(7778.33), "6050", "34870", "1", "13", BigDecimal.valueOf(0));

		when(contratoService.insert(any(ContratoDTO.class))).thenReturn(contratoDTO);

		RequestBuilder request = MockMvcRequestBuilders.post("/api/contratos")
				.content(json)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		mvc.perform(request)
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.cdFonteRecurso").value("0502"));
	}

	@Test
	public void delete() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.delete("/api/contratos/id/{id}", 1L);

		mvc.perform(request)
				.andExpect(status().isNoContent());
	}

	@Test
	public void update() throws Exception {
		String json = "{" +
				"\"Municipio\": \"DOM BASÍLIO\","+
				"\"nomeProduto\": \"LAGO ARTIFICIAL, TANQUE, BARREIROS, CANAIS, RESERV.ÁGUA POTAVEL\","+
				"\"MesEmissao\": \"06\","+
				"\"AnoEmissao\": \"2018\","+
				"\"cdPrograma\": \"0001\","+
				"\"cdSubPrograma\": \"5\","+
				"\"cdFonteRecurso\": \"0502\","+
				"\"cdTipoSeguro\": \"9\","+
				"\"cdEstado\": \"5\","+
				"\"VlCusteio\": 7778.33,"+
				"\"cdProduto\": \"6050\","+
				"\"cdMunicipio\": \"34870\","+
				"\"Atividade\": \"1\","+
				"\"cdModalidade\": \"13\","+
				"\"AreaInvest\": 0" +
				"}";

		ContratoDTO contratoDTO = new ContratoDTO(1L, "DOM BASÍLIO", "LAGO ARTIFICIAL, TANQUE, BARREIROS, CANAIS, RESERV.ÁGUA POTAVEL", "06", "2018", "0001", "5", "0502", "9", "5", BigDecimal.valueOf(7778.33), "6050", "34870", "1", "13", BigDecimal.valueOf(0));

		when(contratoService.update(any(Long.class), any(ContratoDTO.class))).thenReturn(contratoDTO);

		RequestBuilder request = MockMvcRequestBuilders.put("/api/contratos/id/{id}", 1)
				.content(json)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		mvc.perform(request)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.cdProduto").value("6050"));
	}

}