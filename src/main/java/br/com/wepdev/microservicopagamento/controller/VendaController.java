package br.com.wepdev.microservicopagamento.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.wepdev.microservicopagamento.data.vo.VendaVO;
import br.com.wepdev.microservicopagamento.service.VendaService;

@RestController
@RequestMapping("/venda")
public class VendaController {
	
	
	private final VendaService vendaService;
	private final PagedResourcesAssembler<VendaVO> assembler;
	
	
	@Autowired
	public VendaController(VendaService vendaService, PagedResourcesAssembler<VendaVO> assembler) {
		this.vendaService = vendaService;
		this.assembler = assembler;
	}
	
	
	@GetMapping(value = "/{id}", produces = {"application/json", "application/xml", "application/x-yaml"})
	public VendaVO findById(@PathVariable("id") Long id) {
		
		VendaVO vendaVO = vendaService.findById(id);
		vendaVO.add(linkTo(methodOn(VendaController.class).findById(id)).withSelfRel()); // Link para o recurso, aparece na representação
		
		return vendaVO;
	}
	
	
	/**
	 * @RequestParam -> não é obrigatorio passar o parametro na requisição.
	 *
	 */
	@GetMapping(produces = {"application/json", "application/xml", "application/x-yaml"})
	public ResponseEntity<?> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
			                         @RequestParam(value = "limit", defaultValue = "12") int limit,
			                         @RequestParam(value = "direction", defaultValue = "asc") String direction) {
		
		var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC; // if ternario, se o que veio por parametro for igual a desc, ordem decrescente, senão ordem normal
		
		Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "nome")); // define o numero da pagina, tamanho de elementos por pagina, e a direção de ordenação que é feita por nome
		
		Page<VendaVO> vendas = vendaService.findAll(pageable); // Buscando os produtos
		
		vendas.stream().forEach(p -> p.add(linkTo(methodOn(VendaController.class).findById(p.getId())).withSelfRel())); // links de recursos para cada produto
		
		PagedModel<EntityModel<VendaVO>> pagedModel = assembler.toModel(vendas); // retorna o link de cada pagina com todas as informações
		
		return new ResponseEntity<>(pagedModel, HttpStatus.OK);
	}
	
	
	@PostMapping(produces = {"application/json", "application/xml", "application/x-yaml"}, 
			consumes = {"application/json", "application/xml", "application/x-yaml"})
	public VendaVO create(@RequestBody VendaVO vendaVO) {
		VendaVO vendaVo = vendaService.create(vendaVO);
		vendaVo.add(linkTo(methodOn(VendaController.class).findById(vendaVo.getId())).withSelfRel()); // Link para o recurso, aparece na representação
		return vendaVo;
	}

}
