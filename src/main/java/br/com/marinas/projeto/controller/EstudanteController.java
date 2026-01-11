package br.com.marinas.projeto.controller;

import br.com.marinas.projeto.dto.AtualizarEstudanteDTO;
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
    public Estudante CriarEstudante(@RequestBody Estudante estudante){
        return estudanteService.criarEstudante(estudante);
    }

    @GetMapping("/estudantes")
    @ResponseStatus(HttpStatus.OK)
    public List<Estudante> listarEstudantes(){
        return  estudanteService.listarEstudantes();
    }

    @GetMapping("/estudantes/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Estudante> getEstudante(@PathVariable Long id){
        return estudanteService.buscarEstudanteById(id);
    }

    @PutMapping("/estudantes/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Estudante> atualizarEstudanteById(@PathVariable Long id,
                                                            @RequestBody AtualizarEstudanteDTO estudante){
        return estudanteService.atualizarEstudanteById(id, estudante);
    }

    @DeleteMapping("/estudantes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public  ResponseEntity<Object> excluirEstudanteById(@PathVariable Long id){
        return estudanteService.excluirEstudanteById(id);
    }

    @GetMapping("/estudantes/nome/{nome}")
    @ResponseStatus(HttpStatus.OK)
    public  ResponseEntity<Estudante> buscarEstudantePeloNome(@PathVariable String nome){
        return estudanteService.buscaEstudanteByNome(nome);
    }

    @GetMapping("/estudantes/curso")
    @ResponseStatus(HttpStatus.OK)
    public  List<Estudante> buscarEstudantePeloCurso(@RequestParam String nome_curso){
        return estudanteService.buscaEstudanteByCurso(nome_curso);
    }
}
