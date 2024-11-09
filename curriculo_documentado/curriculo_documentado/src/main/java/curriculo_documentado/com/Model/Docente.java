package curriculo_documentado.com.Model;

import jakarta.persistence.*;

import java.util.Date;


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

    public Docente() {}

    public Docente(String nome, Date dataNascimento, String nomeInstituicao, String cpf, String sexo, String resumoCurriculo) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.nomeInstituicao = nomeInstituicao;
        this.cpf = cpf;
        this.sexo = sexo;
        this.resumoCurriculo = resumoCurriculo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public CurriculoDocumentado getCurriculoDocumentado() {
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
}
