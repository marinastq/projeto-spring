package br.com.marinas.projeto.repository;

import br.com.marinas.projeto.model.Estudante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EstudanteRepository extends JpaRepository<Estudante, Long> {

    @Query("SELECT c FROM Estudante c WHERE c.nome = :nome")
    Optional<Estudante> findByName(String nome);

    @Query("SELECT c FROM Estudante c WHERE c.curso = :curso")
    List<Estudante> findByCurso(String curso);

    @Query("SELECT c FROM Estudante c WHERE c.nome LIKE :nome%")
    List<Estudante> findByNomeComeco(String nome);

    @Query("SELECT c FROM Estudante c WHERE c.curso = :curso%")
    List<Estudante> findByCursoComeco(String curso);

    List<Estudante> findByNomeStartingWith(String nome);

    List<Estudante> findByNomeStartingWithAndCurso(String nome, String curso);

    List<Estudante> findByEnderecoStartingWithOrderByEnderecoDesc(String endereco);

    List<Estudante> findByIdLessThanEqual(Long id);

    //
    List<Estudante> findByIdLessThanEqualAndCurso(Long id, String curso);

    List<Estudante> findByIdGreaterThanEqualAndCurso(Long id, String curso);

    List<Estudante> findByIdGreaterThanEqualAndMeioPagamento(Long id, Long meioPagamento);

    boolean existsByNome(String nome);
}
