package br.com.marinas.projeto;

import br.com.marinas.projeto.model.DadosBancarios;
import br.com.marinas.projeto.model.Estudante;
import br.com.marinas.projeto.model.TipoContaBancaria;
import br.com.marinas.projeto.repository.EstudanteRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
public class EstudanteRepositoryTest {

    //em classe de teste usar o @Autowired. Em classe de producao, injetar no constutor.
    @Autowired
    private EstudanteRepository estudanteRepository;

    public EstudanteRepositoryTest(){

    }

    @Test
    @DisplayName("sucesso - deve salvar estudante na base com sucesso")
    void DeveSalvarEstudanteNaBaseComSucesso(){
        Estudante estudanteSalvo = this.estudanteRepository.save(buildMockEstudante());

        assertNotNull(estudanteSalvo);
        assertEquals("java", estudanteSalvo.getCurso());
    }

    @Test
    @DisplayName("sucesso - deve listar todos os estudante com sucesso")
    void DeveListarEstudanteCadastradosComSucesso(){
        Estudante estudanteSalvo = this.estudanteRepository.save(buildMockEstudante());
        List<Estudante> estudantes = this.estudanteRepository.findAll();

        assertEquals(1, estudantes.size());
    }

    @Test
    @DisplayName("sucesso - deve excluir o estudante com sucesso")
    void DeveExcluirEstudanteComSucesso(){
        this.estudanteRepository.deleteById(1L);

        Optional<Estudante> estudante = this.estudanteRepository.findById(1L);

        //nao quero que exista
        assertFalse(estudante.isPresent());
    }

    @Test
    @DisplayName("sucesso - deve bsucar estudante pelo nome com sucesso")
    void DeveBuscarEstudantePeloNomeComSucesso(){
        Estudante estudanteSalvo = this.estudanteRepository.save(buildMockEstudante());

        Optional<Estudante> estudante = estudanteRepository.findByName("aluno");

        assertTrue(estudante.isPresent()); //assertTrue usa com o Optional
    }

    @Test
    @DisplayName("sucesso - deve bsucar estudante pelo nome e curso com sucesso")
    void DeveBuscarEstudantePeloNomeCursoComSucesso(){
        Estudante estudanteSalvo = this.estudanteRepository.save(buildMockEstudante());

        List<Estudante> estudantes = estudanteRepository.findByNomeStartingWithAndCurso("al", "java");

        assertEquals(1, estudantes.size());
    }


    @Test
    @DisplayName("sucesso - deve listar estudantes com id menor que 1 com sucesso")
    void DeveListarEstudantesComIdMenorQueUmComSucesso(){
        Estudante estudanteSalvo = this.estudanteRepository.save(buildMockEstudante());

        List<Estudante> estudantes = estudanteRepository.findByIdLessThanEqual(0L);
        //findByIdLessThanEqual faz um <=

        assertEquals(0, estudantes.size());
    }



    private Estudante buildMockEstudante(){
        DadosBancarios dadosBancarios = new DadosBancarios(
                1234, //agencia,
                12345,//conta,
                1,//digito,
                TipoContaBancaria.valueOf("CONTA_CORRENTE"), //tipoContaBancaria,
                null//estudante
        );

        return  new Estudante(
                1L,//id
                "aluno",//nome
                "Rua de alguem",//endereco,
                1L, //meioPagamento,
                "java",//curso,
                dadosBancarios);
    }
}
