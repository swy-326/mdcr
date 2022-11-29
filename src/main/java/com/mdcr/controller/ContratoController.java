package com.mdcr.controller;

import com.mdcr.client.ContratoFeignClient;
import com.mdcr.model.dto.ContratoDTO;
import com.mdcr.model.dto.ResponseDTO;
import com.mdcr.service.ContratoService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping(value = "/api/contratos")
public class ContratoController {

	@Autowired
	private ContratoService contratoService;

	@Autowired
	private ContratoFeignClient contratoFeignClient;

	@GetMapping(value = "/onboarding")
	@ApiOperation(value = "Onboarding", notes = "Busca os dados na API de MDCR e salva no banco de dados")
	public ResponseEntity<List<ContratoDTO>> getResponse(){
		ResponseDTO responseDTO = contratoFeignClient.getResponse();

		List<ContratoDTO> list = responseDTO.getValue();
		for (ContratoDTO l : list){
			l.setNomeProduto(l.getNomeProduto().replace("\"", ""));
			contratoService.insert(l);
		}

		return ResponseEntity.ok().body(list);
	}

	@GetMapping
	@ApiOperation(value = "Busca todos os registros", notes = "Busca todos os registros de banco de dados, podendo ser consultado por paginação")
	public ResponseEntity<List<ContratoDTO>> findAll(@ApiParam(value = "Número da página a ser exibido") @RequestParam(value = "page", defaultValue = "0") Integer page,
													 @ApiParam(value = "Quantidade de registros a ser exibido em uma página") @RequestParam(value = "size", defaultValue = Integer.MAX_VALUE+"") Integer size) {
		List<ContratoDTO> list = contratoService.findAll(PageRequest.of(page, size));
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/id/{id}")
	@ApiOperation(value = "Busca por ID", notes = "Busca um registro por seu ID")
	public ResponseEntity<ContratoDTO> findById(@ApiParam(value = "id") @PathVariable Long id){
		ContratoDTO obj = contratoService.findById(id);
		return ResponseEntity.ok().body(obj);
	}

	@GetMapping(value = "/ano/{ano}")
	@ApiOperation(value = "Soma anual de custeios", notes = "Retorna a soma de investimentos dos produtos: ovinos, bovinos, caprinos, café e avicultura do ano especificado")
	public ResponseEntity<BigDecimal> findByAno(@ApiParam(value = "ano em yyyy") @PathVariable String ano){
		BigDecimal soma = contratoService.findByAno(ano);
		return ResponseEntity.ok().body(soma);
	}

	@GetMapping(value = "/search")
	@ApiOperation(value = "Busca por parâmetros", notes = "Busca todos os registros que satisfaçam os parâmetros")
	public ResponseEntity<List<ContratoDTO>> findCustomFields(
			@ApiParam(value = "Nome do município") @RequestParam(value = "municipio", required = false) String municipio,
			@ApiParam(value = "Nome de um produto") @RequestParam(value = "nomeProduto", required = false) String nomeProduto,
			@ApiParam(value = "Mês de emissão") @RequestParam(value = "mesEmissao", required = false) String mesEmissao,
			@ApiParam(value = "Ano de emissão da cédula de crédito") @RequestParam(value = "anoEmissao", required = false) String anoEmissao,
			@ApiParam(value = "Código do programa") @RequestParam(value = "cdPrograma", required = false) String cdPrograma,
			@ApiParam(value = "Código do subprograma") @RequestParam(value = "cdSubPrograma", required = false) String cdSubPrograma,
			@ApiParam(value = "Código da fonte de recurso de uma destinação do financiamento de crédito rural") @RequestParam(value = "cdFonteRecurso", required = false) String cdFonteRecurso,
			@ApiParam(value = "Código do tipo do seguro") @RequestParam(value = "cdTipoSeguro", required = false) String cdTipoSeguro,
			@ApiParam(value = "Código da unidade de federação") @RequestParam(value = "cdEstado", required = false) String cdEstado,
			@ApiParam(value = "Valor dos contratos de custeio") @RequestParam(value = "vlCusteio", required = false) BigDecimal vlCusteio,
			@ApiParam(value = "Código de um produto") @RequestParam(value = "cdProduto", required = false) String cdProduto,
			@ApiParam(value = "Código cadmu do município") @RequestParam(value = "cdMunicipio", required = false) String cdMunicipio,
			@ApiParam(value = "Atividade") @RequestParam(value = "atividade", required = false) String atividade,
			@ApiParam(value = "Código da modalidade") @RequestParam(value = "cdModalidade", required = false) String cdModalidade,
			@ApiParam(value = "Área de investimento") @RequestParam(value = "areInvest", required = false) BigDecimal areaInvest
	){
		if (municipio != null) municipio = municipio.toUpperCase().replace("+", " ");
		if (nomeProduto != null) nomeProduto = nomeProduto.toUpperCase().replace("+", " ");

		List<ContratoDTO> list = contratoService.findCustomFields(municipio, nomeProduto, mesEmissao, anoEmissao, cdPrograma, cdSubPrograma, cdFonteRecurso, cdTipoSeguro, cdEstado, vlCusteio, cdProduto, cdMunicipio, atividade, cdModalidade, areaInvest);
		return ResponseEntity.ok().body(list);
	}

	@PostMapping
	@ApiOperation(value = "Salva um novo registro", notes = "Salva um registro no banco de dados")
	public ResponseEntity<ContratoDTO> post(@ApiParam(value = "Objeto do tipo ContratoDTO") @RequestBody ContratoDTO obj){
		ContratoDTO c = contratoService.insert(obj);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/id/{id}").buildAndExpand(obj.getId()).toUri();

		return ResponseEntity.created(uri).body(c);
	}

	@DeleteMapping(value = "/id/{id}")
	@ApiOperation(value = "Remove o registro do ID fornecido", notes = "Remove um registro do banco de dados por seu ID")
	public ResponseEntity<Void> delete(@ApiParam(value = "id") @PathVariable Long id){
		contratoService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping(value = "/id/{id}")
	@ApiOperation(value = "Atualiza o registro do ID fornecido", notes = "Atualiza um registro do banco de dados por seu ID")
	public ResponseEntity<ContratoDTO> update (@ApiParam(value = "id") @PathVariable Long id,
											   @ApiParam(value = "Objeto do tipo ContratoDTO") @RequestBody ContratoDTO obj){
		ContratoDTO c = contratoService.update(id, obj);
		return ResponseEntity.ok().body(c);
	}
}