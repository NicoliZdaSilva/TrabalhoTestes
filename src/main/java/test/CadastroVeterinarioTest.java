package test;

import dao.VeterinarioDAO;
import model.Especialidade;
import model.Veterinario;
import org.junit.jupiter.api.Test;

import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;


public class CadastroVeterinarioTest {

    @Test
    public void TestCadastroCompleto(){

        String nome = "Thaiane Almeida";
        int idade = 24;
        String cpf = "146.934.187-57";
        String dataAdmissao = "31/03/2021";

        Especialidade esp = new Especialidade("Felinos");

        Veterinario vet1 = new Veterinario(nome, idade, cpf, dataAdmissao);
        vet1.addEspecialidade(esp);
        VeterinarioDAO dao = new VeterinarioDAO();

        dao.save(vet1);

        DateTimeFormatter formatter =DateTimeFormatter.ofPattern("dd/MM/yyyy");

        assertEquals(nome, vet1.getNome());
        assertEquals(idade, vet1.getIdade());
        assertEquals(cpf, vet1.getCpf());
        assertEquals(dataAdmissao, vet1.getDataAdmissao().format(formatter));
        assertTrue(vet1.getEspecialidades().contains(esp));

        System.out.println(vet1.toString());

        System.out.println("-----------Teste com banco--------------");

        Veterinario vetFromDb = dao.readByCPF(vet1.getCpf());

        assertNotNull(vetFromDb);
        assertEquals(nome, vetFromDb.getNome());
        assertEquals(idade, vetFromDb.getIdade());
        assertEquals(cpf, vetFromDb.getCpf());
        assertEquals(dataAdmissao, vetFromDb.getDataAdmissao().format(formatter));
        assertTrue(vetFromDb.getEspecialidades().contains(esp));


        System.out.println("Veterinaro cadastrado no banco: "+vetFromDb.toString());
    }
}
