//Thaiane
import model.Especialidade;
import model.Veterinario;
import org.junit.jupiter.api.Test;
import service.AtualizarVeterinarioService;
import service.RemoverEspecialidadeService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class TestAtualizacaoVetUnitarioRT11 {

    @Test // CT34
    public void testEditarVeterinarioDados() {
        String nome = "Thaiane Bento de Almeida";
        int idade = 24;
        String cpf = "123.456.789-00";
        String dataAdmissaoInicial = "25/09/2023";
        Especialidade esp1 = new Especialidade("Radiologia");

        Veterinario vet = new Veterinario(nome, idade, cpf, dataAdmissaoInicial, esp1);

        AtualizarVeterinarioService service = new AtualizarVeterinarioService();

        assertEquals(nome, vet.getNome());
        assertTrue(vet.getEspecialidades().contains(esp1));

        String novoNome = "Thaiane de Almeida";
        service.atualizarNome(vet, novoNome);

        Set<Especialidade> novasEspecialidades = Set.of(
                new Especialidade("Radiologia"),
                new Especialidade("Nutrição")
        );
        service.atualizarEspecialidades(vet, novasEspecialidades);

        assertEquals(novoNome, vet.getNome());
        Set<String> especialidadesAtualizadas = vet.getEspecialidades()
                .stream()
                .map(Especialidade::getNome)
                .collect(Collectors.toSet());
        assertTrue(especialidadesAtualizadas.contains("Radiologia"));
        assertTrue(especialidadesAtualizadas.contains("Nutrição"));
    }

    @Test // CT35
    public void testRemoverEspecialidade() {
        String nome = "Thaiane de Almeida";
        int idade = 24;
        String cpf = "123.456.789-00";
        String dataAdmissao = "25/09/2023";
        Especialidade esp1 = new Especialidade("Radiologia");
        Especialidade esp2 = new Especialidade("Nutrição");

        Veterinario vet = new Veterinario(nome, idade, cpf, dataAdmissao, esp1);
        vet.addEspecialidade(esp2);

        RemoverEspecialidadeService service = new RemoverEspecialidadeService();

        Set<String> especialidadesIniciais = vet.getEspecialidades()
                .stream()
                .map(Especialidade::getNome)
                .collect(Collectors.toSet());
        assertTrue(especialidadesIniciais.contains("Radiologia"));
        assertTrue(especialidadesIniciais.contains("Nutrição"));

        service.removerEspecialidade(vet, "Radiologia");

        Set<String> especialidadesAtuais = vet.getEspecialidades()
                .stream()
                .map(Especialidade::getNome)
                .collect(Collectors.toSet());
        assertFalse(especialidadesAtuais.contains("Radiologia"));
        assertTrue(especialidadesAtuais.contains("Nutrição"));
    }

    @Test // CT36
    public void testAlterarTodosOsDados() {
        String nome = "Thaiane Bento";
        int idade = 24;
        String cpf = "123.456.789-00";
        String dataAdmissaoInicial = "25/07/2024";
        Especialidade esp1 = new Especialidade("Oncologia");

        Veterinario vet = new Veterinario(nome, idade, cpf, dataAdmissaoInicial, esp1);

        assertEquals(nome, vet.getNome());
        assertTrue(vet.getEspecialidades().contains(esp1));
        assertEquals(dataAdmissaoInicial, vet.getDataAdmissao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        AtualizarVeterinarioService service = new AtualizarVeterinarioService();
        String novoNome = "Thaiane de Almeida";
        Set<Especialidade> novasEspecialidades = Set.of(new Especialidade("Nutrição"));
        service.atualizarNome(vet, novoNome);
        service.atualizarEspecialidades(vet, novasEspecialidades);

        assertEquals(novoNome, vet.getNome());
        Set<String> especialidadesAtualizadas = vet.getEspecialidades()
                .stream()
                .map(Especialidade::getNome)
                .collect(Collectors.toSet());
        assertTrue(especialidadesAtualizadas.contains("Nutrição"));
        assertFalse(especialidadesAtualizadas.contains("Oncologia"));

        String novaDataAdmissao = "01/01/2025";
        Exception exception = assertThrows(UnsupportedOperationException.class, () -> {
            vet.setDataAdmissao(LocalDate.parse(novaDataAdmissao, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        });

        assertEquals("A data de início na clínica não pode ser alterada", exception.getMessage());

        assertEquals(dataAdmissaoInicial, vet.getDataAdmissao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }

}
