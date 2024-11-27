//Thaiane
package test;

import dao.EspecialidadeDAO;
import model.Especialidade;
import model.Veterinario;
import org.junit.jupiter.api.Test;

import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AtualizacaoVetUnitarioTest{

    @Test
    public void testCorreto(){
      //CT34
        String nome = "Thaiane Bento de Almeida";
        int idade = 24;
        String cpf = "1146.934.187-57";
        String dataAdmissao = "25/09/2023";
        Especialidade esp = new Especialidade("Radiologia");
        EspecialidadeDAO especialidadeDAO = new EspecialidadeDAO();

        Veterinario vet1 = new Veterinario(nome, idade, cpf, dataAdmissao);
        vet1.addEspecialidade(esp);
        DateTimeFormatter formatter =DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println(vet1.toString());

        assertEquals(nome, vet1.getNome());
        assertEquals(idade, vet1.getIdade());
        assertEquals(cpf, vet1.getCpf());
        assertEquals(dataAdmissao, vet1.getDataAdmissao().format(formatter));
        assertTrue(vet1.getEspecialidades().contains(esp));

        nome = "Thaiane de Almeida";
        vet1.setNome(nome);
        vet1.setEspecialidades(
                new HashSet<>(List.of(
                        new Especialidade("Cardiologia"),
                        new Especialidade("Nutrição")
                )),
                especialidadeDAO
        );

        assertEquals(nome, vet1.getNome());
        assertEquals(idade, vet1.getIdade());
        assertEquals(cpf, vet1.getCpf());
        assertEquals(dataAdmissao, vet1.getDataAdmissao().format(formatter));
        List<String> especialidadesEsperadas = List.of("Cardiologia", "Nutrição");
        List<String> especialidadesAtuais = vet1.getEspecialidades()
                .stream()
                .map(Especialidade::getNome)
                .toList();

        assertTrue(especialidadesAtuais.containsAll(especialidadesEsperadas),
                "As especialidades esperadas não estão presentes");

        System.out.println("\nAtualização de veterinário: \n"+vet1.toString());

    }

    @Test
    public void testRemoveEspecialidade(){
        //CT35
    }

    @Test
    public void testAlteraTodosOsDados(){
        //CT36

    }

}
