package curriculo_documentado.com.Model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "curriculo_documentado")
public class CurriculoDocumentado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "docente_id", foreignKey = @ForeignKey(name = "fk_docente_curriculo"), nullable = true)
    private Docente docente;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Secao> secoes;

    public CurriculoDocumentado() {
        this.secoes = new ArrayList<>();
    }

    public List<Secao> getSecoes() {
        return secoes;
    }

    public void setSecoes(List<Secao> secoes) {
        this.secoes = secoes;
    }

    public Docente getDocente() {
        return docente;
    }

    public void setDocente(Docente docente) {
        this.docente = docente;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void addSecao(Secao secao) {
        if (secao == null) {
            throw new IllegalArgumentException("A seção não pode ser nula");
        }
        this.secoes.add(secao);
    }

    @Override
    public String toString() {
        return "CurriculoDocumentado{" +
                "id=" + id +
                ", secoes=" + secoes +
                '}';
    }
}
