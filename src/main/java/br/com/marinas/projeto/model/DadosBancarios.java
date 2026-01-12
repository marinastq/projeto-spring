package br.com.marinas.projeto.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "dados_bancarios")
public class DadosBancarios {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer agencia;

    @Column(nullable = false)
    private Integer conta;

    @Column(nullable = false)
    private Integer digito;

    @Column(nullable = false)
    private TipoContaBancaria tipoContaBancaria;

    //sinaliza o relacionamento das tabelas
    @OneToOne(mappedBy = "dadosBancarios") //nome do atributo na classe Estudante
    @JsonBackReference //sinaliza que nao tem os campos de Estudante no Json
    private Estudante estudante;

    public DadosBancarios() {};

    public DadosBancarios(Integer agencia, Integer conta, Integer digito,
                          TipoContaBancaria tipoContaBancaria, Estudante estudante) {
        this.agencia = agencia;
        this.conta = conta;
        this.digito = digito;
        this.tipoContaBancaria = tipoContaBancaria;
        this.estudante = estudante;
    }

    public Long getId() {
        return id;
    }

    public Integer getAgencia() {
        return agencia;
    }

    public Integer getConta() {
        return conta;
    }

    public Integer getDigito() {
        return digito;
    }

    public TipoContaBancaria getTipoContaBancaria() {
        return tipoContaBancaria;
    }

    public Estudante getEstudante() {
        return estudante;
    }
}
