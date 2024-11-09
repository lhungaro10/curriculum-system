package curriculo_documentado.com.Model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "secao")
public class Secao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String nome;
    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true)
    List<ItensDeSecao> itensDeSecao;

    public Secao(long id, String nome, List<ItensDeSecao> itensDeSecao) {
        this.id = id;
        this.nome = nome;
        this.itensDeSecao = itensDeSecao;
    }

    public Secao() {
        this.itensDeSecao = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<ItensDeSecao> getItensDeSecao() {
        return itensDeSecao;
    }

    public void setItensDeSecao(List<ItensDeSecao> itensDeSecao) {
        this.itensDeSecao = itensDeSecao;
    }
}
