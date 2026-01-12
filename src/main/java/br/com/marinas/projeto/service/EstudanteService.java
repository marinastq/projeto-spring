package br.com.marinas.projeto.service;

import br.com.marinas.projeto.dto.AtualizarEstudanteDTO;
import br.com.marinas.projeto.mapper.EstudanteMapper;
import br.com.marinas.projeto.model.Estudante;
import br.com.marinas.projeto.repository.EstudanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Service
public class EstudanteService {

    private EstudanteRepository estudanteRepository;
    private EstudanteMapper estudanteMapper;

    public EstudanteService(EstudanteRepository repository,
                            EstudanteMapper mapper) {
        this.estudanteRepository = repository;
        this.estudanteMapper = mapper;
    }

    public Estudante criarEstudante(Estudante estudante){
        return  estudanteRepository.save(estudante);
    }

    public List<Estudante> listarEstudantes(){
        return estudanteRepository.findAll();
    }

    public ResponseEntity<Estudante> buscarEstudanteById(Long id){
        return estudanteRepository.findById(id)
                //public <U> Optional<U> map(Function<? super T, ? extends U> mapper)
                .map(estudante -> ResponseEntity.ok().body(estudante))
                .orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<Estudante> atualizarEstudanteById(Long id, AtualizarEstudanteDTO dto){
        return estudanteRepository.findById(id)
                .map(estudanteToUpdate ->{
                    Estudante atualizado = estudanteMapper.toUpdatedEntity(estudanteToUpdate, dto);

                    atualizado = estudanteRepository.save(atualizado);
                    return ResponseEntity.ok().body(atualizado);

                }).orElse(ResponseEntity.notFound().build());
    }

    /*
    public ResponseEntity<Estudante> atualizarEstudanteById(Estudante estudante, Long id){
        return  estudanteRepository.findById(id)
                .map(estudanteToUpdate -> {
                    Estudante atualizado = estudanteToUpdate.atualizar(
                            estudanteToUpdate.getNome(),
                            estudanteToUpdate.getEndereco(),
                            estudanteToUpdate.getMeioPagamento(),
                            estudanteToUpdate.getCurso()
                            );

                    atualizado = estudanteRepository.save(atualizado);

                    return ResponseEntity.ok().body(atualizado);
                }).orElse(ResponseEntity.notFound().build());

    }
    */

    public ResponseEntity<Object> excluirEstudanteById(Long id){
        return estudanteRepository.findById(id)
                .map(estudanteToDelete -> {
                    estudanteRepository.deleteById(id);
                    return ResponseEntity.noContent().build();
                }).orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<Estudante> buscaEstudanteByNome(String nome){
        return  estudanteRepository.findByName(nome)
                .map(estudante -> ResponseEntity.ok().body(estudante))
                .orElse(ResponseEntity.notFound().build());
    }

    public List<Estudante> buscaEstudanteByCurso(String curso){
        return  estudanteRepository.findByCurso(curso);
    }

    public List<Estudante> buscaEstudanteByNomeComeco(String comecoNome){
        return  estudanteRepository.findByNomeComeco(comecoNome);
    }

    public List<Estudante> listaEstudantePeloInicioDoNome(String comecoNome){
        return estudanteRepository.findByNomeStartingWith(comecoNome);
    }

    public List<Estudante> listaEstudantePeloInicioDoNomeECurso(String comecoNome, String curso){
        return estudanteRepository.findByNomeStartingWithAndCurso(comecoNome, curso);
    }

    public List<Estudante> listaEstudantePeloEnderecoOrdenadoDesc(String endereco){
        return estudanteRepository.findByEnderecoStartingWithOrderByEnderecoDesc(endereco);
    }

    public List<Estudante> listaPrimeirosEstudante(Long id){
        return estudanteRepository.findByIdLessThanEqual(id);
    }
}
