package com.mdcr.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "tb_contrato")
public class Contrato implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonProperty("Municipio")
	@ApiParam(value = "Nome do município")
	private String municipio;
	@ApiParam(value = "Nome de um produto")
	private String nomeProduto;
	@JsonProperty("MesEmissao")
	@ApiParam(value = "Mês de emissão")
	private String mesEmissao;
	@JsonProperty("AnoEmissao")
	@ApiParam(value = "Ano de emissão da cédula de crédito")
	private String anoEmissao;
	@ApiParam(value = "Código do programa")
	private String cdPrograma;
	@ApiParam(value = "Código do subprograma")
	private String cdSubPrograma;
	@ApiParam(value = "Código da fonte de recurso de uma destinação do financiamento de crédito rural")
	private String cdFonteRecurso;
	@ApiParam(value = "Código do tipo do seguro")
	private String cdTipoSeguro;
	@ApiParam(value = "Código da unidade de federação")
	private String cdEstado;
	@JsonProperty("VlCusteio")
	@ApiParam(value = "Valor dos contratos de custeio")
	private BigDecimal vlCusteio;
	@ApiParam(value = "Código de um produto")
	private String cdProduto;
	@ApiParam(value = "Código cadmu do município")
	private String cdMunicipio;
	@JsonProperty("Atividade")
	@ApiParam(value = "Atividade")
	private String atividade;
	@ApiParam(value = "Código da modalidade")
	private String cdModalidade;
	@JsonProperty("AreaInvest")
	@ApiParam(value = "Área de investimento")
	private BigDecimal areaInvest;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Contrato contrato = (Contrato) o;
		return Objects.equals(id, contrato.id) && Objects.equals(municipio, contrato.municipio) && Objects.equals(nomeProduto, contrato.nomeProduto) && Objects.equals(mesEmissao, contrato.mesEmissao) && Objects.equals(anoEmissao, contrato.anoEmissao) && Objects.equals(cdPrograma, contrato.cdPrograma) && Objects.equals(cdSubPrograma, contrato.cdSubPrograma) && Objects.equals(cdFonteRecurso, contrato.cdFonteRecurso) && Objects.equals(cdTipoSeguro, contrato.cdTipoSeguro) && Objects.equals(cdEstado, contrato.cdEstado) && Objects.equals(vlCusteio, contrato.vlCusteio) && Objects.equals(cdProduto, contrato.cdProduto) && Objects.equals(cdMunicipio, contrato.cdMunicipio) && Objects.equals(atividade, contrato.atividade) && Objects.equals(cdModalidade, contrato.cdModalidade) && Objects.equals(areaInvest, contrato.areaInvest);
	}
}
