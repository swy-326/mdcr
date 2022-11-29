package com.mdcr.client;

import com.mdcr.model.dto.ResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;


@Component
@FeignClient(name = "mdcr", url = "https://olinda.bcb.gov.br/olinda/servico/SICOR/versao/v2/odata/InvestMunicipioProduto?$top=100&$format=json&$select=Municipio,nomeProduto,MesEmissao,AnoEmissao,cdPrograma,cdSubPrograma,cdFonteRecurso,cdTipoSeguro,cdEstado,VlCusteio,cdProduto,cdMunicipio,Atividade,cdModalidade,AreaInvest")
public interface ContratoFeignClient {

	@GetMapping(value = "")
	ResponseDTO getResponse();
}