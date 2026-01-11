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
}
