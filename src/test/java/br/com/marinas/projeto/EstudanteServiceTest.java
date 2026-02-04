package br.com.marinas.projeto;

import br.com.marinas.projeto.dto.AtualizarEstudanteDTO;
import br.com.marinas.projeto.exception.EstudanteDuplicadoException;
import br.com.marinas.projeto.exception.EstudanteNaoEncontradoException;
import br.com.marinas.projeto.mapper.EstudanteMapper;
import br.com.marinas.projeto.model.DadosBancarios;
import br.com.marinas.projeto.model.Estudante;
import br.com.marinas.projeto.model.TipoContaBancaria;
import br.com.marinas.projeto.repository.EstudanteRepository;
import br.com.marinas.projeto.service.EstudanteService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class EstudanteServiceTest {

    //classe auxiliar no teste
    @Mock
    private EstudanteRepository estudanteRepository;
    @Mock
    private EstudanteMapper estudanteMapper;

    //classe que efetivamente esta testando
    @InjectMocks
    private EstudanteService estudanteService;

    @Test
    @DisplayName("sucesso - deve salvar estudante na base com sucesso")
    void deveSalvarEstudanteComSucesso() throws EstudanteDuplicadoException {
        Estudante estudante = buildMockEstudante();
        this.estudanteService.criarEstudante(estudante);

        //verifica interacao, se é chamado o metodo save, nao grava no banco
        verify(this.estudanteRepository).save(estudante);
    }

    @Test
    @DisplayName("sucesso - deve salvar estudante na base com sucesso sem lancar execptions")
    void deveSalvarEstudanteComSucessoSemLancarException() throws EstudanteDuplicadoException {
        Estudante estudante = buildMockEstudante();
        this.estudanteService.criarEstudante(estudante);

        //verifica interacao, se é chamado o metodo save, nao grava no banco
        assertDoesNotThrow(() -> estudanteService.criarEstudante(estudante));
    }

    @Test
    @DisplayName("sucesso - deve lancar excecao de estudante duplicado com sucesso")
    void deveLancarExcecaoEstudanteDuplicadoComSucesso() throws EstudanteDuplicadoException {
        Estudante estudante = buildMockEstudante();

        when(estudanteRepository.existsByNome(estudante.getNome()))
                .thenReturn(true);

        assertThrows(EstudanteDuplicadoException.class,() -> estudanteService.criarEstudanteComValidacao(estudante));
    }

    @Test
    @DisplayName("sucesso - deve listar os estudante com sucesso")
    void deveListarEstudantesComSucesso() {
        Estudante estudante = buildMockEstudante();

        when(estudanteRepository.findAll()).thenReturn(List.of(estudante));

        List<Estudante> listaEstudantes = this.estudanteService.listarEstudantes();
        assertEquals(1, listaEstudantes.size());
        verify(this.estudanteRepository).findAll();
    }


    @Test
    @DisplayName("sucesso - deve buscar estudante pelo ID com sucesso")
    void deveListarEstudantePeloIdComSucesso() throws EstudanteNaoEncontradoException {
        Estudante estudante = buildMockEstudante();


        when(estudanteRepository.findById(1L)).thenReturn(Optional.of(estudante));

        Estudante estudanteEncontrado = this.estudanteService.buscarEstudanteById(1L);
        assertEquals("aluno", estudanteEncontrado.getNome());
        verify(this.estudanteRepository).findById(estudante.getId());
    }

    @Test
    @DisplayName("sucesso - deve lancar excecao ao buscar estudante pelo ID")
    void deveLancarExcecaoAoBuscarEstudantePeloId() throws EstudanteNaoEncontradoException {
        Estudante estudante = buildMockEstudante();

        when(estudanteRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(EstudanteNaoEncontradoException.class,() -> estudanteService.buscarEstudanteById(2L));

        verify(this.estudanteRepository).findById(2L);
    }

    @Test
    @DisplayName("sucesso - deve lancar excecao e ter a mensagem ao buscar estudante pelo ID")
    void deveLancarExcecaoPegaMensagemAoBuscarEstudantePeloId() throws EstudanteNaoEncontradoException {
        Estudante estudante = buildMockEstudante();

        when(estudanteRepository.findById(2L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EstudanteNaoEncontradoException.class,
                () -> estudanteService.buscarEstudanteById(2L));

        assertEquals("Estudante com o id " + 2L + "nao cadastrado.", exception.getMessage());


        verify(this.estudanteRepository).findById(2L);
    }

    @Test
    @DisplayName("sucesso - deve atualizar o estudante com sucesso")
    void deveAtualizarEstudanteComSucesso() throws EstudanteNaoEncontradoException {
        Estudante estudanteExistente = buildMockEstudante();

        AtualizarEstudanteDTO dto = new AtualizarEstudanteDTO(
                estudanteExistente.getNome(),
                "novo endereco",
                estudanteExistente.getMeioPagamento(),
                "novo curso",
                estudanteExistente.getDadosBancarios()
        );

        when(estudanteRepository.findById(1L))
                .thenReturn(Optional.of(estudanteExistente));

        when(estudanteMapper.toUpdatedEntity(any(), any()))
                .thenAnswer(i -> {
                    Estudante atual = i.getArgument(0);
                    AtualizarEstudanteDTO dtoArg = i.getArgument(1);

                    return atual.atualizar(
                            dtoArg.getNome(),
                            dtoArg.getEndereco(),
                            dtoArg.getMeioPagamento(),
                            dtoArg.getCurso(),
                            dtoArg.getDadosBancarios()
                    );
                });

        when(estudanteRepository.save(any(Estudante.class)))
                .thenAnswer(i -> i.getArgument(0));

        Estudante estudanteAtualizado = estudanteService.atualizarEstudanteById(1L, dto);

        assertNotEquals("java", estudanteAtualizado.getCurso());

        verify(this.estudanteRepository).save(estudanteAtualizado);
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
