package test;

import model.Especialidade;
import model.Veterinario;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class VeterinarioTest {

    @Test
    public void testCadastrarVeterinarioComTodosOsDados() {

        String nome = "Thaiane Bento de Almeida";
        int idade = 35;
        String cpf = "123.456.789-00";
        LocalDate dataInicio = LocalDate.of(2023, 10, 27);
        Especialidade especialidade = new Especialidade("Felinos");


        Veterinario veterinario = new Veterinario(nome, idade, cpf, dataInicio);
        veterinario.addEspecialidade(especialidade);


        assertEquals(nome, veterinario.getNome(), "O nome do veterinário está incorreto.");
        assertEquals(idade, veterinario.getIdade(), "A idade do veterinário está incorreta.");
        assertEquals(cpf, veterinario.getCpf(), "O CPF do veterinário está incorreto.");
        assertEquals(dataInicio, veterinario.getDataAdmissao(), "A data de início está incorreta.");
        assertTrue(veterinario.getEspecialidades().contains(especialidade), "A especialidade não foi adicionada corretamente.");

        System.out.println(veterinario.toString());
    }

    @Test
    public void testCadastrarVeterinarioComFaltaDeDados() {

        String nome = "";
        int idade = 35;
        String cpf = "123.456.789-00";
        LocalDate dataInicio = LocalDate.of(2024, 9, 1);
        Especialidade especialidade = new Especialidade("Felinos");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Veterinario(nome, idade, cpf, dataInicio);
        });

        assertEquals("É necessário informar o nome.", exception.getMessage());
        assertNull(null, "A instância do veterinário não deve ser criada.");
    }
}


