package service;

import model.Especialidade;
import model.Veterinario;

public class RemoverEspecialidadeService {
    public void removerEspecialidade(Veterinario vet, String nomeEspecialidade) {
        if (nomeEspecialidade == null || nomeEspecialidade.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome da especialidade não pode ser nulo ou vazio.");
        }

        Especialidade especialidadeParaRemover = vet.getEspecialidades().stream()
                .filter(esp -> esp.getNome().equalsIgnoreCase(nomeEspecialidade))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Especialidade não encontrada no conjunto."));

        vet.getEspecialidades().remove(especialidadeParaRemover);
    }
}
