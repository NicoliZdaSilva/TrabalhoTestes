package app;

import model.Veterinario;

import java.time.LocalDate;

public class Main{
    public static void main(String[]args){
        
        Veterinario vet1 = new Veterinario("Thaiane Almeida", 20, "146.934.187-57", LocalDate.of(2023, 10, 31));

        System.out.println(vet1.toString());

    }
}