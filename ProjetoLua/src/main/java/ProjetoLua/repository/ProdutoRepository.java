package ProjetoLua.repository;

import ProjetoLua.model.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    Page<Produto> findByValorLessThan(double valor, Pageable pageable);

    Page<Produto> findByValorGreaterThan(double valor, Pageable pageable);
}
