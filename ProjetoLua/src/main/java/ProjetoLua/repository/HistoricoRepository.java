package ProjetoLua.repository;

import ProjetoLua.model.Historico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface HistoricoRepository extends JpaRepository<Historico, Long> {
    Page<Historico> findByDataBetween(LocalDateTime dataInicial, LocalDateTime dataFinal, Pageable pageable);
}
