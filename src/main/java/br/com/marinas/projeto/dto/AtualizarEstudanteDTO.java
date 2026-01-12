package br.com.marinas.projeto.dto;

import br.com.marinas.projeto.model.DadosBancarios;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class AtualizarEstudanteDTO {
    private String nome;
    private String endereco;
    private  Long meioPagamento;
    private String curso;
    private DadosBancarios dadosBancarios;

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public Long getMeioPagamento() {
        return meioPagamento;
    }

    public String getCurso() {
        return curso;
    }

    public DadosBancarios getDadosBancarios() {
        return dadosBancarios;
    }
}
