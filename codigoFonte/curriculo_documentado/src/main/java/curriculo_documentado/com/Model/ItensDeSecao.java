package curriculo_documentado.com.Model;

import jakarta.persistence.*;

import java.sql.Date;
@Entity(name = "itens_de_secao")
public class ItensDeSecao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String anexo;
    String nome;
    String descricao;

    @ManyToOne
    @JoinColumn(name = "secao_id") // Especifica a coluna de junção na tabela itens_de_secao
    private Secao secao;

    public Secao getSecao() {
        return secao;
    }

    public void setSecao(Secao secao) {
        this.secao = secao;
    }

    public ItensDeSecao(long id, String anexo, String nome, String descricao) {
        this.id = id;
        this.anexo = anexo;
        this.nome = nome;
        this.descricao = descricao;
    }

    public ItensDeSecao(String anexo, String nome, String descricao) {
        this.anexo = anexo;
        this.nome = nome;
        this.descricao = descricao;
    }

    public ItensDeSecao() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAnexo() {
        return anexo;
    }

    public void setAnexo(String anexo) {
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
                ", anexo='" + anexo + '\'' +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}
