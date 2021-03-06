package br.com.wepdev.microservicopagamento.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.modelmapper.ModelMapper;

import br.com.wepdev.microservicopagamento.data.vo.ProdutoVendaVO;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "produto_venda")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ProdutoVenda implements Serializable{
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "id_produto", length = 10, nullable = false)
	private Long idProduto;
	
	@Column(name = "quantidade", length = 10, nullable = false)
	private Integer quantidade;
	
	@JoinColumn(name = "id_venda")
	@ManyToOne(fetch = FetchType.LAZY)
	private Venda venda;
	
	
	public static ProdutoVenda converteProdutoVendaVOParaProdutoVenda(ProdutoVendaVO produtoVendaVO) {
		return new ModelMapper().map(produtoVendaVO, ProdutoVenda.class);
				
		
	}



}
