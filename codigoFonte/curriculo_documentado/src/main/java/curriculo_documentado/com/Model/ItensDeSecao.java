package curriculo_documentado.com.Model;

import jakarta.persistence.*;

@Entity(name = "itens_de_secao")
public class ItensDeSecao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Lob // Indica que o campo será armazenado como um BLOB no banco de dados
    private byte[] anexo; // Altere de String para byte[]

    private String nome;
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "secao_id") // Especifica a coluna de junção na tabela itens_de_secao
    private Secao secao;

    public Secao getSecao() {
        return secao;
    }

    public void setSecao(Secao secao) {
        this.secao = secao;
    }

    // Construtores
    public ItensDeSecao(long id, byte[] anexo, String nome, String descricao) {
        this.id = id;
        this.anexo = anexo;
        this.nome = nome;
        this.descricao = descricao;
    }

    public ItensDeSecao(byte[] anexo, String nome, String descricao) {
        this.anexo = anexo;
        this.nome = nome;
        this.descricao = descricao;
    }

    public ItensDeSecao() {}

    // Getters e Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public byte[] getAnexo() {
        return anexo;
    }

    public void setAnexo(byte[] anexo) {
        this.anexo = anexo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "ItensDeSecao{" +
                "id=" + id +
                ", anexo=" + (anexo != null ? "[PDF armazenado]" : "null") +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}
