package model;

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

        public Veterinario(String nome, int idade, String cpf, String dataAdmissao, Especialidade esp) {
            super(nome, idade, cpf);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                try {
                    if (dataAdmissao == null || dataAdmissao.trim().isEmpty()) {
                        throw new IllegalArgumentException("É necessário informar a data de admissão.");
                    }
                    this.dataAdmissao = LocalDate.parse(dataAdmissao, formatter);
                } catch (DateTimeParseException e) {
                    throw new IllegalArgumentException("Formato de data inválido.");
                }

            if (esp == null || esp.getNome() == null || esp.getNome().trim().isEmpty()) {
                throw new IllegalArgumentException("Especialidade obrigatória, cadastre um profissional válido.");
            }
            this.especialidades.add(esp);
        }
        
        public LocalDate getDataAdmissao() {
            return dataAdmissao;
        }

        public void setDataAdmissao(LocalDate dataAdmissao) {
            throw new UnsupportedOperationException("A data de início na clínica não pode ser alterada");
        }

        public void addEspecialidade(Especialidade especialidade) {
            if (this.especialidades.contains(especialidade)) {
                throw new IllegalArgumentException("Especialidade duplicada.");
            }

            this.especialidades.add(especialidade);
        }

        public Set<Especialidade> getEspecialidades() {
            return especialidades;
        }

        public void setEspecialidades(Set<Especialidade> novasEspecialidades) {
            if (novasEspecialidades == null || novasEspecialidades.isEmpty()) {
                throw new IllegalArgumentException("O conjunto de especialidades não pode ser nulo ou vazio.");
            }

            for (Especialidade esp : novasEspecialidades) {
                if (esp == null || esp.getNome() == null || esp.getNome().trim().isEmpty()) {
                    throw new IllegalArgumentException("Especialidades não podem ser nulas ou ter nomes vazios.");
                }
            }

            this.especialidades = new HashSet<>(novasEspecialidades);
        }



        @Override
        public String toString() {
            return super.toString() +
                    "\nData de admissão: " + this.dataAdmissao +
                    "\nEspecialidades: " + this.especialidades;
        }


    }
