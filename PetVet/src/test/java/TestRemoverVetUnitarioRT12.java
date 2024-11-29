//Thaiane
import model.Especialidade;
import model.Veterinario;
import org.junit.jupiter.api.Test;
import service.RemoverVeterinarioService;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class TestRemoverVetUnitarioRT12 {

    @Test // CT37
    public void testApagarVeterinario() {
        String nome = "Thaiane Bento";
        int idade = 24;
        String cpf = "123.456.789-00";
        String dataAdmissao = "25/07/2024";
        Especialidade esp1 = new Especialidade("Oncologia");

        Veterinario vet = new Veterinario(nome, idade, cpf, dataAdmissao, esp1);
        Set<Veterinario> quadroDeVeterinarios = new HashSet<>();
        quadroDeVeterinarios.add(vet);

        assertTrue(quadroDeVeterinarios.contains(vet));
        assertEquals(1, quadroDeVeterinarios.size());

        RemoverVeterinarioService service = new RemoverVeterinarioService();

        service.removerVeterinario(quadroDeVeterinarios, vet);

        assertFalse(quadroDeVeterinarios.contains(vet));
        assertEquals(0, quadroDeVeterinarios.size());

    }

    @Test // Fora do escopo inicial
    public void testRemoverVeterinarioByCpfValido() {
        String nome = "Thaiane Bento";
        int idade = 24;
        String cpf = "123.456.789-00";
        String dataAdmissao = "25/07/2024";
        Especialidade esp1 = new Especialidade("Oncologia");

        Veterinario vet = new Veterinario(nome, idade, cpf, dataAdmissao, esp1);
        Set<Veterinario> quadroDeVeterinarios = new HashSet<>();
        quadroDeVeterinarios.add(vet);

        RemoverVeterinarioService service = new RemoverVeterinarioService();

        assertTrue(quadroDeVeterinarios.contains(vet));

        service.removerVeterinarioByCpf(quadroDeVeterinarios, cpf);

        assertFalse(quadroDeVeterinarios.contains(vet));
        assertEquals(0, quadroDeVeterinarios.size());
    }

    @Test // CT38
    public void testApagarVeterinarioNaoCadastrado() {
        String nome = "João Parro";
        int idade = 35;
        String cpf = "987.654.321-00";
        String dataAdmissao = "29/11/2023";
        Especialidade esp1 = new Especialidade("Oncologia");

        Veterinario vetNaoCadastrado = new Veterinario(nome, idade, cpf, dataAdmissao, esp1);

        Set<Veterinario> quadroDeVeterinarios = new HashSet<>();
        RemoverVeterinarioService service = new RemoverVeterinarioService();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            service.removerVeterinarioByCpf(quadroDeVeterinarios, vetNaoCadastrado.getCpf());
        });

        assertEquals("Veterinário com o CPF fornecido não encontrado.", exception.getMessage());
    }
}
