package test;

import dao.VeterinarioDAO;
import model.Veterinario;
import org.testng.annotations.Test;

public class CadastroVeterinarioTest {

    @Test
    public void TestCadastroCompleto(){
        Veterinario vet1 = new Veterinario("Thaiane Almeida", 24, "146.934.187-57", "31/03/2021");
        VeterinarioDAO dao = new VeterinarioDAO();


    }
}
