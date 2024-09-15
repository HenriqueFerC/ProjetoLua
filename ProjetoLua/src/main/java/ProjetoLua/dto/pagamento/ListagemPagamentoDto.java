package ProjetoLua.dto.pagamento;

import ProjetoLua.model.Pagamento;

public record ListagemPagamentoDto(Long numeroCartao, int senha, String validade) {
    public ListagemPagamentoDto(Pagamento pagamento){
        this(pagamento.getNumeroCartao(), pagamento.getSenha(), pagamento.getValidade());
    }
}
