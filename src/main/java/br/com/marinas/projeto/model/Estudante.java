package br.com.marinas.projeto.model;

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

    @Column(nullable = false, length = 10)
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

    public Estudante() {
    }

    public Estudante(Long id, String nome, String endereco, Long meioPagamento, String curso) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.meioPagamento = meioPagamento;
        this.curso = curso;
    }

    public Estudante atualizar (String nome, String endereco, Long meioPagamento, String curso) {
        return  new Estudante(this.id, nome, endereco, meioPagamento, curso);
    }

}
