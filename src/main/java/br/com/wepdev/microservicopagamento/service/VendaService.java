package br.com.wepdev.microservicopagamento.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.wepdev.microservicopagamento.data.vo.VendaVO;
import br.com.wepdev.microservicopagamento.entity.ProdutoVenda;
import br.com.wepdev.microservicopagamento.entity.Venda;
import br.com.wepdev.microservicopagamento.exception.ResourceNotFoundException;
import br.com.wepdev.microservicopagamento.repository.ProdutoVendaRepository;
import br.com.wepdev.microservicopagamento.repository.VendaRepository;

@Service
public class VendaService {
	

	private final VendaRepository vendaRepository;
	private final ProdutoVendaRepository produtoVendaRepository;
	
	

	@Autowired
	public VendaService(VendaRepository vendaRepository, ProdutoVendaRepository produtoVendaRepository) {
		this.vendaRepository = vendaRepository;
		this.produtoVendaRepository = produtoVendaRepository;
	}

	
	public VendaVO create(VendaVO vendaVO) {
		
		Venda venda = vendaRepository.save(Venda.converteVendaVOParaVenda(vendaVO)); // Salva a venda que gera um ID

		List<ProdutoVenda> produtosSalvos = new ArrayList<>(); // Criando a lista de produtoVenda 
		
		vendaVO.getProdutos().forEach(produto -> { // Para cada produto e criado um objeto ProdutoVenda que ja recebe esse produtoVendaVO convertido
			ProdutoVenda produtoVenda = ProdutoVenda.converteProdutoVendaVOParaProdutoVenda(produto);
			produtoVenda.setVenda(venda); // seta a venda que acabou de ser incluida
			produtosSalvos.add(produtoVendaRepository.save(produtoVenda)); // Adiciona o retorno do produto salvo na lista de ProdutoVenda
		});
		venda.setProdutos(produtosSalvos);
		return VendaVO.converteVendaParaVendaVO(venda);
	}

	
	public Page<VendaVO> findAll(Pageable pageable) {
		var page = vendaRepository.findAll(pageable);
		return page.map(this::convertToVendaVO);

	}

	
	private VendaVO convertToVendaVO(Venda venda) {
		return VendaVO.converteVendaParaVendaVO(venda);
	}
	
	
	public VendaVO findById(Long id) {
		var entity = vendaRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		
		return VendaVO.converteVendaParaVendaVO(entity);
	}

}
