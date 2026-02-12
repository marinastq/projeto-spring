package br.com.marinas.projeto;

import br.com.marinas.projeto.model.DadosBancarios;
import br.com.marinas.projeto.model.Estudante;
import br.com.marinas.projeto.model.TipoContaBancaria;
import br.com.marinas.projeto.repository.EstudanteRepository;
import br.com.marinas.projeto.service.EstudanteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(outputDir = "target/snippets")
public class EstudanteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    EstudanteRepository estudanteRepository;

    @MockBean
    EstudanteService estudanteService;

    public static String asJsonString(final Object obj){
        try{
            return new ObjectMapper().writeValueAsString(obj);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @DisplayName("deve criar um Estudante com sucesso")
    @Test
    public void deveCriarEstudanteComSucesso() throws Exception{

        Estudante estudanteMock = buildMockEstudante();

        when(estudanteService.criarEstudante(any(Estudante.class)))
                .thenReturn(estudanteMock);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/estudantes")
                        .content(asJsonString(estudanteMock))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(document("estudantes-criar",

                        requestFields(
                                fieldWithPath("id")
                                        .description("ID gerado do estudante")
                                        .optional(),
                                fieldWithPath("nome")
                                        .description("Nome do estudante"),
                                fieldWithPath("endereco")
                                        .description("Endereço do estudante"),
                                fieldWithPath("meioPagamento")
                                        .description("Código do meio de pagamento"),
                                fieldWithPath("curso")
                                        .description("Curso matriculado"),
                                fieldWithPath("criadoEm")
                                        .description("Data de criação do registro")
                                        .optional(),
                                fieldWithPath("atualizadoEm")
                                        .description("Data da última atualização")
                                        .optional(),
                                fieldWithPath("dadosBancarios")
                                        .description("Dados bancários do estudante"),
                                fieldWithPath("dadosBancarios.id")
                                        .description("Id do dados bancarios")
                                        .optional(),
                                fieldWithPath("dadosBancarios.agencia")
                                        .description("Número da agência"),
                                fieldWithPath("dadosBancarios.conta")
                                        .description("Número da conta"),
                                fieldWithPath("dadosBancarios.digito")
                                        .description("Dígito da conta"),
                                fieldWithPath("dadosBancarios.tipoContaBancaria")
                                        .description("Tipo da conta bancária (CORRENTE ou POUPANCA)")
                        ),

                        responseFields(
                                fieldWithPath("id")
                                        .description("ID gerado do estudante")
                                        .optional(),
                                fieldWithPath("nome")
                                        .description("Nome do estudante"),
                                fieldWithPath("endereco")
                                        .description("Endereço do estudante"),
                                fieldWithPath("meioPagamento")
                                        .description("Código do meio de pagamento"),
                                fieldWithPath("curso")
                                        .description("Curso matriculado"),
                                fieldWithPath("criadoEm")
                                        .description("Data de criação do registro")
                                        .optional(),
                                fieldWithPath("atualizadoEm")
                                        .description("Data da última atualização")
                                        .optional(),
                                fieldWithPath("dadosBancarios")
                                        .description("Dados bancários do estudante"),
                                fieldWithPath("dadosBancarios.id")
                                        .description("ID gerado dos dados bancários"),
                                fieldWithPath("dadosBancarios.agencia")
                                        .description("Número da agência"),
                                fieldWithPath("dadosBancarios.conta")
                                        .description("Número da conta"),
                                fieldWithPath("dadosBancarios.digito")
                                        .description("Dígito da conta"),
                                fieldWithPath("dadosBancarios.tipoContaBancaria")
                                        .description("Tipo da conta bancária")
                        )
                ))

        ;
    }

    @DisplayName("deve buscar estudante pelo ID com sucesso")
    @Test
    public void deveBuscarEstudantePeloIdComSucesso() throws  Exception{
        Estudante estudante = buildMockEstudante();

        when(estudanteRepository.findById(1L))
                .thenReturn(Optional.of(estudante));

        this.mockMvc.perform(get("/v1/estudantes/{id}", "1L"))
                .andDo(print())
                .andExpect(status().isOk());
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
