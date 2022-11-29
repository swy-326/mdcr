package com.mdcr.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mdcr.model.entity.Contrato;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "tb_contrato")
public class ContratoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonProperty("Municipio")
	@ApiModelProperty(notes = "Nome do município")
	private String municipio;
	@ApiModelProperty(notes = "Nome de um produto")
	private String nomeProduto;
	@JsonProperty("MesEmissao")
	@ApiModelProperty(notes = "Mês de emissão")
	private String mesEmissao;
	@JsonProperty("AnoEmissao")
	@ApiModelProperty(notes = "Ano de emissão da cédula de crédito")
	private String anoEmissao;
	@ApiModelProperty(notes = "Código do programa")
	private String cdPrograma;
	@ApiModelProperty(notes = "Código do subprograma")
	private String cdSubPrograma;
	@ApiModelProperty(notes = "Código da fonte de recurso de uma destinação do financiamento de crédito rural")
	private String cdFonteRecurso;
	@ApiModelProperty(notes = "Código do tipo do seguro")
	private String cdTipoSeguro;
	@ApiModelProperty(notes = "Código da unidade de federação")
	private String cdEstado;
	@JsonProperty("VlCusteio")
	@ApiModelProperty(notes = "Valor dos contratos de custeio")
	private BigDecimal vlCusteio;
	@ApiModelProperty(notes = "Código de um produto")
	private String cdProduto;
	@ApiModelProperty(notes = "Código cadmu do município")
	private String cdMunicipio;
	@JsonProperty("Atividade")
	@ApiModelProperty(notes = "Atividade")
	private String atividade;
	@ApiModelProperty(notes = "Código da modalidade")
	private String cdModalidade;
	@JsonProperty("AreaInvest")
	@ApiModelProperty(notes = "Área de investimento")
	private BigDecimal areaInvest;

	public static ContratoDTO create (Contrato contrato){
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(contrato, ContratoDTO.class);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ContratoDTO that = (ContratoDTO) o;
		return Objects.equals(municipio, that.municipio) && Objects.equals(nomeProduto, that.nomeProduto) && Objects.equals(mesEmissao, that.mesEmissao) && Objects.equals(anoEmissao, that.anoEmissao) && Objects.equals(cdPrograma, that.cdPrograma) && Objects.equals(cdSubPrograma, that.cdSubPrograma) && Objects.equals(cdFonteRecurso, that.cdFonteRecurso) && Objects.equals(cdTipoSeguro, that.cdTipoSeguro) && Objects.equals(cdEstado, that.cdEstado) && Objects.equals(vlCusteio, that.vlCusteio) && Objects.equals(cdProduto, that.cdProduto) && Objects.equals(cdMunicipio, that.cdMunicipio) && Objects.equals(atividade, that.atividade) && Objects.equals(cdModalidade, that.cdModalidade) && Objects.equals(areaInvest, that.areaInvest);
	}
}
