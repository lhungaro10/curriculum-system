package curriculo_documentado.com.Model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


@Entity(name = "docente")
public class Docente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nome;
    private Date dataNascimento;
    private String nomeInstituicao;
    private String cpf;
    private String sexo;
    private String resumoCurriculo;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "docente")
    private CurriculoDocumentado curriculoDocumentado;

    public Docente() {
        this.curriculoDocumentado = new CurriculoDocumentado();
        this.curriculoDocumentado.setDocente(this);
    }

    public Docente(String nome, Date dataNascimento, String nomeInstituicao, String cpf, String sexo, String resumoCurriculo) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.nomeInstituicao = nomeInstituicao;
        this.cpf = cpf;
        this.sexo = sexo;
        this.resumoCurriculo = resumoCurriculo;
        this.curriculoDocumentado = new CurriculoDocumentado();
        this.curriculoDocumentado.setDocente(this);
    }

    public Docente(String nome, Date dataNascimento, String nomeInstituicao, String resumo, String sexo) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.nomeInstituicao = nomeInstituicao;
        this.resumoCurriculo = resumo;
        this.sexo = sexo;
    }

    public void addSecao(Secao secao) {
        this.curriculoDocumentado.addSecao(secao);
    }

    public List<Secao> getSecoes() {
        return this.curriculoDocumentado.getSecoes();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public CurriculoDocumentado getCurriculoDocumentado() {
        if (this.curriculoDocumentado == null) {
            this.curriculoDocumentado = new CurriculoDocumentado();
            this.curriculoDocumentado.setDocente(this);
        }
        return curriculoDocumentado;
    }

    public void setCurriculoDocumentado(CurriculoDocumentado curriculoDocumentado) {
        this.curriculoDocumentado = curriculoDocumentado;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getNomeInstituicao() {
        return nomeInstituicao;
    }

    public void setNomeInstituicao(String nomeInstituicao) {
        this.nomeInstituicao = nomeInstituicao;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getResumoCurriculo() {
        return resumoCurriculo;
    }

    public void setResumoCurriculo(String resumoCurriculo) {
        this.resumoCurriculo = resumoCurriculo;
    }

    @Override
    public String toString() {
        return "Docente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", dataNascimento=" + dataNascimento +
                ", nomeInstituicao='" + nomeInstituicao + '\'' +
                ", cpf='" + cpf + '\'' +
                ", sexo='" + sexo + '\'' +
                ", resumoCurriculo='" + resumoCurriculo + '\'' +
                ", curriculoDocumentado=" + curriculoDocumentado +
                '}';
    }
}
