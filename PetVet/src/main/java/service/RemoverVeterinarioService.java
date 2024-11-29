//Thaiane
package service;

import model.Veterinario;

import java.util.Set;

public class RemoverVeterinarioService {
    public void removerVeterinario(Set<Veterinario> quadroDeVeterinarios, Veterinario vet) {
        if (vet == null) {
            throw new IllegalArgumentException("O veterinário não pode ser nulo.");
        }
        if (!quadroDeVeterinarios.contains(vet)) {
            throw new IllegalArgumentException("Veterinário não encontrado no quadro de veterinários.");
        }

        quadroDeVeterinarios.remove(vet);
    }

    public void removerVeterinarioByCpf(Set<Veterinario> quadroDeVeterinarios, String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new IllegalArgumentException("O CPF não pode ser nulo ou vazio.");
        }

        Veterinario vetParaRemover = quadroDeVeterinarios.stream()
                .filter(vet -> vet.getCpf().equals(cpf))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Veterinário com o CPF fornecido não encontrado."));

        quadroDeVeterinarios.remove(vetParaRemover);
    }
}
