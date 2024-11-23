package Model;

public abstract class Pessoa {
    private String nome;
    private String idade;
    private String cpf;

    public Pessoa(String nome, String idade, String cpf) {
        if (nome == null || nome.isEmpty() ||
                idade == null || idade.isEmpty() ||
                cpf == null || cpf.isEmpty()) {
            throw new IllegalArgumentException("Todos os dados devem ser informados.");
        }
        this.nome = nome;
        this.idade = idade;
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("O nome não pode ser vazio.");
        }
        this.nome = nome;
    }

    public String getIdade() {
        return idade;
    }

    public void setIdade(String idade) {
        if (idade == null || idade.isEmpty()) {
            throw new IllegalArgumentException("A idade não pode ser vazia.");
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
}
