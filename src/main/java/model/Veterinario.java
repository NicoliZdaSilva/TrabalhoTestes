package model;

import dao.EspecialidadeDAO;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "veterinarios")
public class Veterinario extends Pessoa {

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
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

    public Veterinario(String nome, int idade, String cpf, String dataAdmissao) {
        super(nome, idade, cpf);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            this.dataAdmissao = LocalDate.parse(dataAdmissao, formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato de data inválido.");
        }
    }

    public Set<Especialidade> getEspecialidades() {
        return especialidades;
    }

    public void addEspecialidade(Especialidade especialidade) {
        if (!this.especialidades.contains(especialidade)) {
            this.especialidades.add(especialidade);
        }
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

    public void setEspecialidades(Set<Especialidade> especialidades, EspecialidadeDAO especialidadeDAO) {
        if (this.especialidades == null) {
            this.especialidades = new HashSet<>();
        }

        Set<String> nomesExistentes = especialidadeDAO.findAllNames();

        Set<Especialidade> especialidadesAtualizadas = new HashSet<>();
        for (Especialidade especialidade : especialidades) {
            if (nomesExistentes.contains(especialidade.getNome())) {
                Especialidade existente = especialidadeDAO.findByName(especialidade.getNome());
                especialidadesAtualizadas.add(existente);
            } else {
                especialidadesAtualizadas.add(especialidade);
            }
        }

        this.especialidades = especialidadesAtualizadas;
    }


    @Override
    public String toString() {
        return super.toString() +
                "\nData de admissão: " + this.dataAdmissao +
                "\nEspecialidades: " + this.especialidades;
    }


}
