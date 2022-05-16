package br.com.wepdev.microservicopagamento.config;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import br.com.wepdev.microservicopagamento.data.vo.ProdutoVO;
import br.com.wepdev.microservicopagamento.entity.Produto;
import br.com.wepdev.microservicopagamento.repository.ProdutoRepository;

@Component
public class ProdutoReceiveMessage {
	
	
	private final ProdutoRepository produtoRepository;

	
	@Autowired
	public ProdutoReceiveMessage(ProdutoRepository produtoRepository) {
		this.produtoRepository = produtoRepository;
	}
	
	/**
	 * Metodo que escuta a fila RabbitMq
	 * @param produtoVO
	 */
	@RabbitListener(queues = {"${crud.rabbitmq.queue}"})
	public void receive(@Payload ProdutoVO produtoVO) {
		produtoRepository.save(Produto.converteProdutoVOParaProduto(produtoVO));
		
	}

}
