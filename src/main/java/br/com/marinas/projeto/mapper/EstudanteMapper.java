package br.com.marinas.projeto.mapper;

import br.com.marinas.projeto.dto.AtualizarEstudanteDTO;
import br.com.marinas.projeto.model.Estudante;
import org.springframework.stereotype.Component;

@Component
public class EstudanteMapper {
    public Estudante toUpdatedEntity(Estudante atual, AtualizarEstudanteDTO dto) {
        return atual.atualizar(dto.getNome(), dto.getEndereco(), dto.getMeioPagamento(), dto.getCurso(), dto.getDadosBancarios());
    }
}
