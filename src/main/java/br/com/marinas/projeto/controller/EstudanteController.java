package br.com.marinas.projeto.controller;

import br.com.marinas.projeto.dto.AtualizarEstudanteDTO;
import br.com.marinas.projeto.exception.EstudanteNaoEncontradoException;
import br.com.marinas.projeto.mapper.EstudanteMapper;
import br.com.marinas.projeto.model.Estudante;
import br.com.marinas.projeto.service.EstudanteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/v1")
public class EstudanteController {

    private EstudanteService estudanteService;

    public EstudanteController(EstudanteService estudanteService) {
        this.estudanteService = estudanteService;
    }

    @PostMapping("/estudantes")
    @ResponseStatus(HttpStatus.CREATED)
    public Estudante CriarEstudante(@RequestBody Estudante estudante) throws Exception {
        return estudanteService.criarEstudante(estudante);
    }

    @GetMapping("/estudantes")
    @ResponseStatus(HttpStatus.OK)
    public List<Estudante> listarEstudantes(){
        return  estudanteService.listarEstudantes();
    }

    @GetMapping("/estudantes/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Estudante> getEstudante(@PathVariable Long id) throws EstudanteNaoEncontradoException {
        return ResponseEntity.ok(estudanteService.buscarEstudanteById(id));
    }

    @PutMapping("/estudantes/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Estudante> atualizarEstudanteById(@PathVariable Long id,
                                                            @RequestBody AtualizarEstudanteDTO estudante)
            throws EstudanteNaoEncontradoException {
        return ResponseEntity.ok(estudanteService.atualizarEstudanteById(id, estudante));
    }

    @DeleteMapping("/estudantes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public  ResponseEntity<Object> excluirEstudanteById(@PathVariable Long id){
        return estudanteService.excluirEstudanteById(id);
    }

    //http://localhost:8080/v1/estudantes/nome/xxxxxx  (xxxxxx = Nome do estudante cadastrado)
    @GetMapping("/estudantes/nome/{nome}")
    @ResponseStatus(HttpStatus.OK)
    public  ResponseEntity<Estudante> buscarEstudantePeloNome(@PathVariable String nome){
        return estudanteService.buscaEstudanteByNome(nome);
    }

    //http://localhost:8080/v1/estudantes/curso?nome_curso=curso 1   (curso 1 = curso cadastrado na base)
    @GetMapping("/estudantes/curso")
    @ResponseStatus(HttpStatus.OK)
    public  List<Estudante> buscarEstudantePeloCurso(@RequestParam String nome_curso){
        return estudanteService.buscaEstudanteByCurso(nome_curso);
    }

    //http://localhost:8080/v1/estudantes/nomecomeco?comeco_nome=xxxx
    @GetMapping("/estudantes/nomecomeco")
    @ResponseStatus(HttpStatus.OK)
    public  List<Estudante> buscarEstudantePeloComecoNomeDesafio(@RequestParam String comeco_nome){
        return estudanteService.buscaEstudanteByNomeComeco(comeco_nome);
    }

    @GetMapping("/estudantes/nome")
    @ResponseStatus(HttpStatus.OK)
    public  List<Estudante> buscarEstudantePeloComecoNome(@RequestParam String comeco_nome){
        return estudanteService.listaEstudantePeloInicioDoNome(comeco_nome);
    }

    //http://localhost:8080/v1/estudantes/nome-curso?comeco_nome=estudante 01&curso=curso 1
    @GetMapping("/estudantes/nome-curso")
    @ResponseStatus(HttpStatus.OK)
    public  List<Estudante> listaEstudantePeloInicioDoNomeECurso(
            @RequestParam String comeco_nome,
            @RequestParam String curso){
        return estudanteService.listaEstudantePeloInicioDoNomeECurso(comeco_nome, curso);
    }


    //http://localhost:8080/v1/estudantes/endereco?endereco=rua
    @GetMapping("/estudantes/endereco")
    @ResponseStatus(HttpStatus.OK)
    public  List<Estudante> listaEstudantePeloEnderecoOrdenadoDesc(
            @RequestParam String endereco){
        return estudanteService.listaEstudantePeloEnderecoOrdenadoDesc(endereco);
    }

    //http://localhost:8080/v1/estudantes/primeiros-estudantes?id=3
    @GetMapping("/estudantes/primeiros-estudantes")
    @ResponseStatus(HttpStatus.OK)
    public  List<Estudante> listaPrimeirosEstudante(
            @RequestParam Long id){
        return estudanteService.listaPrimeirosEstudante(id);
    }
}
