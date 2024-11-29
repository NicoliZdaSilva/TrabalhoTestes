package model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "especialidades")
public class Especialidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;

    @ManyToMany(mappedBy = "especialidades")
    private Set<Veterinario> veterinarios = new HashSet<>();

    public Especialidade() {
    }

    public Especialidade(String nome) {
        if (nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("O nome da especialidade não pode ser nulo ou vazio.");
        }
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("O nome da especialidade não pode ser nulo ou vazio.");
        }
        this.nome = nome;
    }

    public Set<Veterinario> getVeterinarios() {
        return veterinarios;
    }

    @Override
    public String toString() {
        return "Especialidade: " + nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Especialidade that = (Especialidade) o;
        return nome.equals(that.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome);
    }

}
