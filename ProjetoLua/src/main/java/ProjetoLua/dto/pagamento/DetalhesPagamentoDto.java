package ProjetoLua.dto.pagamento;

import ProjetoLua.model.Pagamento;

public record DetalhesPagamentoDto(Long numeroCartao, int senha, String validade) {
    public DetalhesPagamentoDto(Pagamento pagamento){
        this(pagamento.getNumeroCartao(), pagamento.getSenha(), pagamento.getValidade());
    }
}
