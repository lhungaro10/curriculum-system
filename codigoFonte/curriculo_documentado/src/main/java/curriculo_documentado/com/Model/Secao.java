package curriculo_documentado.com.Model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "secao")
public class Secao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @Column(nullable = false)
    String nome;

    /**
     * @OneToMany em Secao: Define a lista de itens (itensDeSecao) e mapeia-a com mappedBy = "secao", que se refere à propriedade secao de ItensDeSecao.
     */
    @OneToMany(mappedBy = "secao", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    List<ItemDeSecao> itensDeSecao;

    public Secao(long id, String nome, List<ItemDeSecao> itensDeSecao) {
        this.id = id;
        this.nome = nome;
        this.itensDeSecao = itensDeSecao;
    }

    public Secao() {
        this.itensDeSecao = new ArrayList<>();
    }

    public Secao(String nome) {
        this.nome = nome;
        this.itensDeSecao = new ArrayList<>();
    }

    public void addItemSecao(ItemDeSecao itemDeSecao) {
        itemDeSecao.setSecao(this);
        this.itensDeSecao.add(itemDeSecao);
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

    public List<ItemDeSecao> getItensDeSecao() {
        return itensDeSecao;
    }

    public void setItensDeSecao(List<ItemDeSecao> itemDeSecao) {
        this.itensDeSecao = itemDeSecao;
    }

    @Override
    public String toString() {
        return "Secao{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", itensDeSecao=" + itensDeSecao +
                '}';
    }
}
