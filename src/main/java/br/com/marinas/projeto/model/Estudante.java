package br.com.marinas.projeto.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "estudante")
public class Estudante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;

    @Column(nullable = false, length = 100)
    private String endereco;

    @Column(nullable = false)
    private  Long meioPagamento;

    @Column(nullable = false)
    private String curso;

    @CreationTimestamp
    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @UpdateTimestamp
    @Column(name = "atualizado_em", nullable = false, updatable = true)
    private LocalDateTime atualizadoEm;

    @OneToOne(cascade = CascadeType.ALL) //o que faz em uma entidade faz na outra
    @JoinColumn(name = "dados_bancarios_id", referencedColumnName = "id")
    @JsonManagedReference //sinaliza que tem os campos de dadosBancarios no Json
    private  DadosBancarios dadosBancarios;

    public Long getId() {
        return id;
    }

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

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public LocalDateTime getAtualizadoEm() {
        return atualizadoEm;
    }

    public DadosBancarios getDadosBancarios() {
        return dadosBancarios;
    }

    public Estudante() {
    }

    public Estudante(Long id, String nome, String endereco, Long meioPagamento, String curso, DadosBancarios dadosBancarios) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.meioPagamento = meioPagamento;
        this.curso = curso;
        this.dadosBancarios = dadosBancarios;
    }

    public Estudante atualizar (String nome, String endereco, Long meioPagamento,
                                String curso, DadosBancarios dadosBancarios) {
        return  new Estudante(this.id, nome, endereco, meioPagamento, curso, dadosBancarios);
    }

}
