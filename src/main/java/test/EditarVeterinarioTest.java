package test;

import dao.VeterinarioDAO;
import model.Especialidade;
import model.Veterinario;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EditarVeterinarioTest {
    @Test
    public void testUpdateVeterinario() {
        String nomeOriginal = "Thaiane Bento de Almeida";
        int idade = 35;
        String cpf = "123.456.789-00";
        String dataInicio = "25/09/2023";
        Especialidade especialidadeOriginal = new Especialidade("Felinos");

        Veterinario veterinario = new Veterinario(nomeOriginal, idade, cpf, dataInicio);
        veterinario.addEspecialidade(especialidadeOriginal);

        VeterinarioDAO dao = new VeterinarioDAO();
        dao.save(veterinario);

        String novoNome = "Thaiane de Almeida";
        LocalDate novaDataAdmissao = LocalDate.of(2023, 9, 25);
        List<Especialidade> novasEspecialidades = Arrays.asList(
                new Especialidade("Nutrição"),
                new Especialidade("Felinos")
        );

        veterinario.setNome(novoNome);
        veterinario.setDataAdmissao(novaDataAdmissao);
        for (Especialidade especialidade : novasEspecialidades) {
            veterinario.addEspecialidade(especialidade);
        }

        dao.update(veterinario);

        Veterinario veterinarioAtualizado = dao.readByCPF(cpf);
        assertEquals(novoNome, veterinarioAtualizado.getNome(), "O nome do veterinário não foi atualizado corretamente.");
        assertEquals(novaDataAdmissao, veterinarioAtualizado.getDataAdmissao(), "A data de admissão não foi atualizada corretamente.");
        assertTrue(veterinarioAtualizado.getEspecialidades().containsAll(novasEspecialidades), "As especialidades não foram atualizadas corretamente.");
        assertEquals(2, veterinarioAtualizado.getEspecialidades().size(), "A quantidade de especialidades está incorreta.");

        System.out.println("Veterinário atualizado com sucesso: " + veterinarioAtualizado);
    }

}
