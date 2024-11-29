package service;

import model.Especialidade;
import model.Veterinario;

import java.util.Set;

public class AtualizarVeterinarioService {

    public void atualizarNome(Veterinario vet, String novoNome) {
        if (novoNome == null || novoNome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome não pode ser nulo ou vazio.");
        }
        vet.setNome(novoNome);
    }

    public void atualizarEspecialidades(Veterinario vet, Set<Especialidade> novasEspecialidades) {
        if (novasEspecialidades == null || novasEspecialidades.isEmpty()) {
            throw new IllegalArgumentException("O conjunto de especialidades não pode ser nulo ou vazio.");
        }
        vet.setEspecialidades(novasEspecialidades);
    }
}
