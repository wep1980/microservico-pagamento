package br.com.wepdev.microservicopagamento.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.modelmapper.ModelMapper;
import org.springframework.format.annotation.DateTimeFormat;

import br.com.wepdev.microservicopagamento.data.vo.VendaVO;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "venda")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Venda implements Serializable{
	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	@Column(name = "data", nullable = false)
	@DateTimeFormat(pattern = "MM/dd/yyyy")
	private Date data;
	
	/*
	 * Uma venda pode ter varios produtos. 
	 * fetch = FetchType.LAZY -> carregamento so quando for chamado. 
	 * cascade = CascadeType.REFRESH -> quando houver atualização no banco de dados na Entidade ProdutoVenda, todas as entidades venda associadas serão recarregadas do banco de dados
	 */
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "venda", cascade = CascadeType.REFRESH) // 
	private List<ProdutoVenda> produtos;
	
	
	@Column(name = "valor_total", length = 10, nullable = false)
	private Double valorTotal;
	
	
	public static Venda converteVendaVOParaVenda(VendaVO vendaVO) {
		return new ModelMapper().map(vendaVO, Venda.class);
	}
}
