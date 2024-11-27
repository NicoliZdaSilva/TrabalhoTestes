package test;

import dao.VeterinarioDAO;
import model.Especialidade;
import model.Veterinario;
import org.junit.jupiter.api.Test;
import java.time.format.DateTimeFormatter;
import static org.testng.AssertJUnit.*;

public class CadastroVetIntegracaoTest {

    @Test
    public void testIntegracaoCadastroVet(){
     //CT52
        Especialidade esp = new Especialidade("Animais de Grande Porte");

        Veterinario vet1 = new Veterinario("Nicoli Zimmermann da Silva", 24, "123.456.789-10", "22/04/2020");
        vet1.addEspecialidade(esp);
        VeterinarioDAO dao = new VeterinarioDAO();

        DateTimeFormatter formatter =DateTimeFormatter.ofPattern("dd/MM/yyyy");
        dao.save(vet1);

        Veterinario vetFromDb = dao.readByCPF(vet1.getCpf());

        assertNotNull(vetFromDb);
        assertEquals("Nicoli Zimmermann da Silva", vetFromDb.getNome());
        assertEquals(24, vetFromDb.getIdade());
        assertEquals("123.456.789-10", vetFromDb.getCpf());
        assertEquals("22/04/2020", vetFromDb.getDataAdmissao().format(formatter));
        assertTrue(vetFromDb.getEspecialidades().contains(esp));

        System.out.println(vetFromDb.toString());
    }
}
