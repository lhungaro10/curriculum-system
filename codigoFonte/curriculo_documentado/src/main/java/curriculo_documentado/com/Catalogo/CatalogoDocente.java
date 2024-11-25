package curriculo_documentado.com.Catalogo;

import curriculo_documentado.com.InterfaceJpa.InterfaceJpa;
import curriculo_documentado.com.Model.Docente;
import curriculo_documentado.com.Model.ItemDeSecao;
import curriculo_documentado.com.Model.Secao;
import curriculo_documentado.com.Service.ServiceGeradorHtmlCurriculo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("all")
@Component
public class CatalogoDocente {
    private Optional<Docente> docente;
    private InterfaceJpa interfaceJpa;
    private final ServiceGeradorHtmlCurriculo serviceGeradorHtmlCurriculo;

    @Autowired
    public CatalogoDocente(InterfaceJpa interfaceJpa, ServiceGeradorHtmlCurriculo serviceGeradorHtmlCurriculo) {
        this.interfaceJpa = interfaceJpa;
        this.serviceGeradorHtmlCurriculo = serviceGeradorHtmlCurriculo;
        carregarDocente();
    }

    public Optional<Docente> getDocente() {
        return docente;
    }

    public void setDocente(Docente docente) {
        this.docente = Optional.ofNullable(docente);
    }

    public void carregarDocente() {
        this.docente= this.interfaceJpa.findFirst();
    }

    public void adicionarDocenteManualmente(String nome, Date dataNascimento, String nomeInstituicao, String cpf, String sexo, String resumoCurriculo) {
        this.docente = Optional.of(new Docente(nome, dataNascimento, nomeInstituicao, cpf, sexo, resumoCurriculo));
        System.out.println(this.docente.toString());
    }

    public void salvarDadosFechamento(){
        if(this.docente.isPresent()){
            this.interfaceJpa.save(this.docente.get());
        }
    }

    public void cadastrarSecaoManualmente(String nome) {
        var secao = new Secao(nome);
        System.out.println(secao.toString());
        System.out.println(docente.get().toString());
        docente.get().getCurriculoDocumentado().addSecao(secao);
        System.out.println(this.docente.get().getCurriculoDocumentado().toString());
    }

    public void adicionarItemEmSecao(String nome, String descricao, byte[] anexoPdf, Secao secao) {
        ItemDeSecao itemDeSecao = new ItemDeSecao(anexoPdf, nome, descricao);
        secao.addItemSecao(itemDeSecao);
    }

    public List<Secao> obterSecoes() {
        return this.docente.get().getSecoes();
    }

    public List<ItemDeSecao> obterItensSecao(Secao secao) {
        if (secao != null && secao.getItensDeSecao() != null) {
            return secao.getItensDeSecao();
        }
        return null;
    }

    public void excluirItemDeSecao(ItemDeSecao itemDeSecao, Secao secao) {
        if (secao != null && secao.getItensDeSecao().contains(itemDeSecao)) {
            secao.getItensDeSecao().remove(itemDeSecao);
        }
    }

     public void gerarCurriculoDocumentado() throws IOException {
        this.serviceGeradorHtmlCurriculo.gerarCurriculoHtmlPdf(this.docente.get().getCurriculoDocumentado());
     }

    public Docente obterDocente() {
        return this.docente.get();
    }

    public void modificarItemDeSecao(ItemDeSecao itemDeSecao, String nome, String novaDesc, byte[] novoAnexo) {
        itemDeSecao.setNome(nome);
        itemDeSecao.setDescricao(novaDesc);
        itemDeSecao.setAnexo(novoAnexo);
    }

    public void modificarSecao(Secao secao, String nome) {
        secao.setNome(nome);
    }
}
