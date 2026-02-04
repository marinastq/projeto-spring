package br.com.marinas.projeto.service;

import br.com.marinas.projeto.dto.AtualizarEstudanteDTO;
import br.com.marinas.projeto.exception.EstudanteDuplicadoException;
import br.com.marinas.projeto.exception.EstudanteNaoEncontradoException;
import br.com.marinas.projeto.mapper.EstudanteMapper;
import br.com.marinas.projeto.model.Estudante;
import br.com.marinas.projeto.repository.EstudanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Optional;

@Service
public class EstudanteService {

    private EstudanteRepository estudanteRepository;
    private EstudanteMapper estudanteMapper;

    public EstudanteService(EstudanteRepository repository,
                            EstudanteMapper mapper) {
        this.estudanteRepository = repository;
        this.estudanteMapper = mapper;
    }

    //metodo que lanca a exception com a trava do banco de dados (model Estudante)
    public Estudante criarEstudante(Estudante estudante) throws EstudanteDuplicadoException {
        try{
            return  estudanteRepository.save(estudante);
        }catch (DataIntegrityViolationException e){
            throw new EstudanteDuplicadoException("Estudante com o nome ja cadastrado");
        }
    }

    public Estudante criarEstudanteComValidacao(Estudante estudante) throws EstudanteDuplicadoException {
        if(estudanteRepository.existsByNome(estudante.getNome())){
            throw new EstudanteDuplicadoException("Estudante com o nome ja cadastrado");
        }

        return  estudanteRepository.save(estudante);
    }

    public List<Estudante> listarEstudantes(){
        return estudanteRepository.findAll();
    }

    public Estudante buscarEstudanteById(Long id)
        throws EstudanteNaoEncontradoException {

        return estudanteRepository.findById(id)
                .orElseThrow(() -> new EstudanteNaoEncontradoException(id));
    }

//    public ResponseEntity<Estudante> buscarEstudanteById(Long id){
//        return estudanteRepository.findById(id)
//                //public <U> Optional<U> map(Function<? super T, ? extends U> mapper)
//                .map(estudante -> ResponseEntity.ok().body(estudante))
//                .orElse(ResponseEntity.notFound().build());
//    }

    public Estudante atualizarEstudanteById(Long id, AtualizarEstudanteDTO dto) throws EstudanteNaoEncontradoException {
        return estudanteRepository.findById(id)
                .map(estudanteToUpdate ->{
                    Estudante atualizado = estudanteMapper.toUpdatedEntity(estudanteToUpdate, dto);

                    atualizado = estudanteRepository.save(atualizado);
                    return atualizado;

                }).orElseThrow(() -> new EstudanteNaoEncontradoException(id));
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
