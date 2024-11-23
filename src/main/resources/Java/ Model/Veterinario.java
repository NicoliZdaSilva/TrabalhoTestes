public class Veterinario extends Pessoa{
    private ArrayList<String> especialidades;
    private LocalDate dataAdmissao;

public Veterinario(String nome, String idade, String cpf, ArrayList<String> especialidades, LocalDate dataAdmissao) {
        super(nome, idade, cpf);
        if (especialidades == null || especialidades.isEmpty()) {
            throw new IllegalArgumentException("As especialidades devem ser informadas.");
        }
        if (data == null) {
            throw new IllegalArgumentException("A data de admissão deve ser informada.");
        }
        this.especialidades = new ArrayList<>();
        this.dataAdmissao = dataAdmissao;
}

    public ArrayList<String> getEspecialidades() {
        return especialidades;
    }

    public void setEspecialidades(List<String> especialidades) {
    if (especialidades == null || especialidades.isEmpty()) {
        throw new IllegalArgumentException("A lista de especialidades não pode ser nula ou vazia.");
    }

    for (String especialidade : especialidades) {
        if (especialidade == null || especialidade.trim().isEmpty()) {
            throw new IllegalArgumentException("A especialidade não pode ser nula ou vazia.");
        }
    }

    if (especialidades.size() != especialidades.stream().distinct().count()) {
        throw new IllegalArgumentException("A lista contém especialidades duplicadas.");
    }

    this.especialidades = new ArrayList<>(especialidades);
    System.out.println("Especialidades definidas com sucesso: " + String.join(", ", especialidades));
}

    public void atualizarEspecialidade(String antigaEspecialidade, String novaEspecialidade) {
        if (antigaEspecialidade == null || antigaEspecialidade.isEmpty()) {
            throw new IllegalArgumentException("A especialidade antiga não pode ser nula ou vazia.");
        }

        if (novaEspecialidade == null || novaEspecialidade.isEmpty()) {
            throw new IllegalArgumentException("A nova especialidade não pode ser nula ou vazia.");
        }

        int index = especialidades.indexOf(antigaEspecialidade);
        if (index == -1) {
            throw new IllegalArgumentException("A especialidade antiga não foi encontrada.");
        }

        if (especialidades.contains(novaEspecialidade)) {
            throw new IllegalArgumentException("A nova especialidade já está cadastrada.");
        }

        especialidades.set(index, novaEspecialidade);
        System.out.println("Especialidade atualizada com sucesso: " + antigaEspecialidade + " -> " + novaEspecialidade);
    }

    public void addEspecialidade(){
        if (especialidade == null || especialidade.isEmpty()) {
            throw new IllegalArgumentException("A especialidade não pode ser nula ou vazia.");
        }
        if (especialidades.contains(especialidade)) {
            throw new IllegalArgumentException("Essa especialidade já está cadastrada.");
        }
        especialidades.add(especialidade);
        System.out.println("Especialidade adicionada com sucesso: " + especialidade);
    }

    public void removerEspecialidade(String especialidade) {
        if (!especialidades.contains(especialidade)) {
            throw new IllegalArgumentException("Essa especialidade não está cadastrada.");
        }
        especialidades.remove(especialidade);
        System.out.println("Especialidade removida com sucesso: " + especialidade);
    }

    public List<String> listarEspecialidades() {
        return new ArrayList<>(especialidades);
    }

    public boolean possuiEspecialidade(String especialidade) {
        return especialidades.contains(especialidade);
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