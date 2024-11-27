package test;

import model.Especialidade;
import model.Veterinario;
import org.junit.jupiter.api.Test;

import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;


public class CadastroVetUnitarioTest {

    @Test
    public void testCompleto(){
    //CT28
        String nome = "Maria Fernanda da Silva";
        int idade = 24;
        String cpf = "187.954.147-33";
        String dataAdmissao = "31/03/2021";

        Especialidade esp = new Especialidade("Cirurgia");

        Veterinario vet1 = new Veterinario(nome, idade, cpf, dataAdmissao);
        vet1.addEspecialidade(esp);


        DateTimeFormatter formatter =DateTimeFormatter.ofPattern("dd/MM/yyyy");

        assertEquals(nome, vet1.getNome());
        assertEquals(idade, vet1.getIdade());
        assertEquals(cpf, vet1.getCpf());
        assertEquals(dataAdmissao, vet1.getDataAdmissao().format(formatter));
        assertTrue(vet1.getEspecialidades().contains(esp));

        System.out.println(vet1.toString());
    }

    @Test
    public void testFaltaDados(){
        //CT29
    }

    @Test
    public void testDataInvalida(){
        //CT30
    }

    @Test
    public void testMaisDeUmaEspecialidade(){
        //CT31
    }

    @Test
    public void testSemEspecialidade(){
        //CT32
    }

    @Test
    public void testEspecialidadeDuplicada(){
        //CT33
    }

}
