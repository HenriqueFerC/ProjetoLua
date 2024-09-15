package ProjetoLua.dto.historico;

import ProjetoLua.dto.pagamento.DetalhesPagamentoDto;
import ProjetoLua.model.Historico;

import java.time.LocalDateTime;

public record DetalhesHistoricoDto(Double valorTotal, LocalDateTime data, DetalhesPagamentoDto detalhesPagamentoDto) {
    public DetalhesHistoricoDto(Historico historico){
        this(historico.getValorTotal(), historico.getData(), new DetalhesPagamentoDto(historico.getPagamento()));
    }
}
