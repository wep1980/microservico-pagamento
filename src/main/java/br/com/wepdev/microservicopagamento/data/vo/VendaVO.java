package br.com.wepdev.microservicopagamento.data.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import br.com.wepdev.microservicopagamento.entity.ProdutoVenda;
import br.com.wepdev.microservicopagamento.entity.Venda;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@JsonPropertyOrder({"id", "data", "produtos", "valorTotal"}) // Define a ordem que os atributos vão retornar
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class VendaVO extends RepresentationModel<VendaVO> implements Serializable{
	private static final long serialVersionUID = 1L;
	

	@JsonProperty("id")
	private Long id;

	@JsonProperty("data")
	private Date data;

	@JsonProperty("produtos")
	private List<ProdutoVenda> produtos;

	@JsonProperty("valorTotal")
	private Double valorTotal;
	
	
	public static VendaVO converteVendaParaVendaVO(Venda venda) {
		return new ModelMapper().map(venda, VendaVO.class);
	}

}
