//Nicoli e Thaiane
package model;

import jakarta.persistence.*;

@MappedSuperclass
public abstract class Pessoa {
    @Column(nullable = false)
    protected String nome;

    @Column(nullable = false)
    protected int idade;

    @Id
    @Column(nullable = false, unique = true)
    protected String cpf;

    public Pessoa(String nome, int idade, String cpf) {
        if (nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("É necessário informar o nome.");
        }
        if (idade <= 0 || idade >= 120) {
            throw new IllegalArgumentException("A idade deve ser maior que 0 e menor que 120.");
        }
        if (cpf == null || cpf.isEmpty()) {
            throw new IllegalArgumentException("O CPF não pode ser nulo ou vazio.");
        }
        this.nome = nome;
        this.idade = idade;
        this.cpf = cpf;
    }

    public Pessoa() {

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("É necessário informar o nome.");
        }
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        if (idade <= 0 || idade >= 120) {
            throw new IllegalArgumentException("A idade não pode ser negativa ou maior que 120.");
        }
        this.idade = idade;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        if (cpf == null || cpf.isEmpty()) {
            throw new IllegalArgumentException("O CPF não pode ser vazio.");
        }
        this.cpf = cpf;
    }

    @Override
    public String toString() {
        return "Nome : " + this.nome +
                "\nidade : " + this.idade +
                "\ncpf : " + this.cpf;
    }
}
