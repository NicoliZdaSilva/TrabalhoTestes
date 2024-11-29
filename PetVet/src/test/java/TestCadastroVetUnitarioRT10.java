//Thaiane
import model.Especialidade;
import model.Veterinario;
import org.junit.jupiter.api.Test;
import java.time.format.DateTimeFormatter;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class TestCadastroVetUnitarioRT10 {

    @Test //CT28
    public void testCompleto(){
        String nome = "Maria Fernanda da Silva";
        int idade = 24;
        String cpf = "187.954.147-33";
        String dataAdmissao = "31/03/2021";

        Especialidade esp = new Especialidade("Cirurgia");
        Veterinario vet1 = new Veterinario(nome, idade, cpf, dataAdmissao, esp);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        assertEquals(nome, vet1.getNome());
        assertEquals(idade, vet1.getIdade());
        assertEquals(cpf, vet1.getCpf());
        assertEquals(dataAdmissao, vet1.getDataAdmissao().format(formatter));
        assertTrue(vet1.getEspecialidades().contains(esp));

        System.out.println(vet1.toString());
    }

    @Test //CT29
    public void testFaltaDados(){
        String nome = "";
        int idade = 24;
        String cpf = "187.954.147-38";
        String dataAdmissao = "01/09/2024";

        Especialidade esp = new Especialidade("Felinos");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Veterinario vet1 = new Veterinario(nome, idade, cpf, dataAdmissao,esp);
        });

        assertEquals("É necessário informar o nome.", exception.getMessage());

    }

    @Test //CT30
    public void testDataInvalida(){
        String nome = "Henrique Knaul";
        int idade = 30;
        String cpf = "123.456.789-00";
        String dataAdmissao = "09-25-2023";
        Especialidade esp = new Especialidade("Felinos");


        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Veterinario vet = new Veterinario(nome, idade, cpf, dataAdmissao, esp);
        });

        assertEquals("Formato de data inválido.", exception.getMessage());

    }

    @Test //CT31
    public void testAdicionarMaisDeUmaEspecialidade() {

        String nome = "Thaiane Bento de Almeida";
        int idade = 20;
        String cpf = "123.456.789-00";
        String dataAdmissao = "25/09/2023";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        Especialidade esp1 = new Especialidade("Felinos");
        Especialidade esp2 = new Especialidade("Nutrição");

        Veterinario vet = new Veterinario(nome, idade, cpf, dataAdmissao,esp1);
        vet.addEspecialidade(esp2);

        assertEquals(nome, vet.getNome());
        assertEquals(idade, vet.getIdade());
        assertEquals(cpf, vet.getCpf());
        assertEquals(dataAdmissao, vet.getDataAdmissao().format(formatter));

        Set<String> especialidadesEsperadas = Set.of("Felinos", "Nutrição");
        Set<String> especialidadesAtuais = vet.getEspecialidades()
                .stream()
                .map(Especialidade::getNome)
                .collect(java.util.stream.Collectors.toSet());

        assertTrue(especialidadesAtuais.containsAll(especialidadesEsperadas),
                "As especialidades esperadas não estão presentes");
        }

    @Test // CT32
    public void testCadastrarVeterinarioSemEspecialidade() {
        String nome = "Joana Ferreira Dias";
        int idade = 35;
        String cpf = "123.456.789-00";
        String dataAdmissao = "08/02/2024";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Veterinario(nome, idade, cpf, dataAdmissao, null);
        });

        assertEquals("Especialidade obrigatória, cadastre um profissional válido.", exception.getMessage());
    }

    @Test // CT33
    public void testAdicionarEspecialidadeDuplicada() {
        String nome = "Joana Ferreira Dias";
        int idade = 35;
        String cpf = "123.456.789-00";
        String dataAdmissao = "08/02/2024";

        Especialidade esp = new Especialidade("Anestesia");

        Veterinario vet = new Veterinario(nome, idade, cpf, dataAdmissao, esp);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            vet.addEspecialidade(esp);
        });

        assertEquals("Especialidade duplicada.", exception.getMessage());
    }


}
