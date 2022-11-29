package com.mdcr.model.repository;

import com.mdcr.model.entity.Contrato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ContratoRepository extends JpaRepository <Contrato, Long> {

	// busca por ano dos produtos 5580, 1300, 1920, 1580 e 1080
	@Query(value = "SELECT SUM(vlCusteio) FROM tb_contrato " +
			"WHERE anoEmissao = :ano AND " +
			"cdProduto IN ('5580', '1300', '1920', '1580', '1080')", nativeQuery = true)
	BigDecimal findByAno(@Param("ano") String anoEmissao);

	// busca customizada
	@Query(value = "SELECT * FROM tb_contrato " +
			"WHERE (:municipio IS NULL OR municipio = :municipio) AND (:nomeProduto IS NULL OR  nomeProduto = :nomeProduto) AND (:mesEmissao IS NULL OR mesEmissao = :mesEmissao) AND (:anoEmissao IS NULL OR anoEmissao = :anoEmissao) AND (:cdPrograma IS NULL OR cdPrograma = :cdPrograma) AND (:cdSubPrograma IS NULL OR cdSubPrograma = :cdSubPrograma) AND (:cdTipoSeguro IS NULL OR cdTipoSeguro = :cdTipoSeguro) AND (:cdFonteRecurso IS NULL OR cdFonteRecurso = :cdFonteRecurso)" +
			"AND (:cdEstado IS NULL OR cdEstado = :cdEstado) AND (:vlCusteio IS NULL OR vlCusteio = :vlCusteio) AND (:cdProduto IS NULL OR cdProduto = :cdProduto) AND (:cdMunicipio IS NULL OR cdMunicipio = :cdMunicipio) AND (:atividade IS NULL OR atividade = :atividade) AND (:cdModalidade IS NULL OR cdModalidade = :cdModalidade) AND (:areaInvest IS NULL OR areaInvest = :areaInvest)",
			nativeQuery = true)
	List<Contrato> findCustomFields(@Param("municipio") String municipio,
									   @Param("nomeProduto") String nomeProduto,
									   @Param("mesEmissao") String mesEmissao,
									   @Param("anoEmissao") String anoEmissao,
									   @Param("cdPrograma") String cdPrograma,
									   @Param("cdSubPrograma") String cdSubPrograma,
									   @Param("cdFonteRecurso") String cdFonteRecurso,
									   @Param("cdTipoSeguro") String cdTipoSeguro,
									   @Param("cdEstado") String cdEstado,
									   @Param("vlCusteio") BigDecimal vlCusteio,
									   @Param("cdProduto") String cdProduto,
									   @Param("cdMunicipio") String cdMunicipio,
									   @Param("atividade") String atividade,
									   @Param("cdModalidade") String cdModalidade,
									   @Param("areaInvest") BigDecimal areaInvest);
}