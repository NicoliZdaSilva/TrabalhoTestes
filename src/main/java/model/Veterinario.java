package model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "veterinarios")
public class Veterinario extends Pessoa {

    @ManyToMany
    @JoinTable(
            name = "veterinario_especialidades",
            joinColumns = @JoinColumn(name = "veterinario_cpf"),
            inverseJoinColumns = @JoinColumn(name = "especialidade_id")
    )
    private Set<Especialidade> especialidades = new HashSet<>();

    @Column(name = "data_admissao", nullable = false)
    private LocalDate dataAdmissao;

    public Veterinario() {
        super();
    }

    public Veterinario(String nome, String idade, String cpf, LocalDate dataAdmissao) {
        super();
        if (dataAdmissao == null) {
            throw new IllegalArgumentException("A data de admissão deve ser informada.");
        }
        this.dataAdmissao = dataAdmissao;
    }

    public Set<Especialidade> getEspecialidades() {
        return especialidades;
    }

    public void addEspecialidade(Especialidade especialidade) {
        if (especialidade == null) {
            throw new IllegalArgumentException("A especialidade não pode ser nula.");
        }
        especialidades.add(especialidade);
    }

    public void removerEspecialidade(Especialidade especialidade) {
        if (especialidade == null || !especialidades.contains(especialidade)) {
            throw new IllegalArgumentException("A especialidade não está associada a este veterinário.");
        }
        especialidades.remove(especialidade);
    }

    public LocalDate getDataAdmissao() {
        return dataAdmissao;
    }

    public void setDataAdmissao(LocalDate dataAdmissao) {
        if (dataAdmissao == null) {
            throw new IllegalArgumentException("A data de admissão não pode ser nula.");
        }

        if (dataAdmissao.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("A data de admissão não pode ser no futuro.");
        }

        if (dataAdmissao.isBefore(LocalDate.of(1900, 1, 1))) {
            throw new IllegalArgumentException("A data de admissão não pode ser anterior a 01/01/1900.");
        }

        this.dataAdmissao = dataAdmissao;
    }
}
