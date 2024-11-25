package curriculo_documentado.com.Controller;

import curriculo_documentado.com.Catalogo.CatalogoDocente;
import curriculo_documentado.com.Model.Docente;
import curriculo_documentado.com.Model.ItemDeSecao;
import curriculo_documentado.com.Model.Secao;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Component
public class Controlador {
    private CatalogoDocente catalogoDocente;

    public Controlador(CatalogoDocente catalogoDocente) {
        this.catalogoDocente = catalogoDocente;
    }

    public void adicionarDocenteManualmente(String nome, Date dataNascimento, String nomeInstituicao, String cpf, String sexo, String resumoCurriculo) {
        this.catalogoDocente.adicionarDocenteManualmente(nome, dataNascimento, nomeInstituicao, cpf, sexo, resumoCurriculo);
    }

    public void cadastrarSecaoManualmente(String nome) {
        this.catalogoDocente.cadastrarSecaoManualmente(nome);
    }

    /**
     * Adiconar item em seção
     * TESTAR E IMPLEMENTAR A INTERFACE
     */
    public List<Secao> obterSecoes() {
        return this.catalogoDocente.obterSecoes();
    }

    public List<ItemDeSecao> obterItensDeSecao(Secao secao) {
        return this.catalogoDocente.obterItensSecao(secao);
    }

    public void adicionarItemEmSecao(String nome, String descricao, byte[] anexoPdf, Secao secao) {
        this.catalogoDocente.adicionarItemEmSecao(nome, descricao, anexoPdf, secao);
    }

    /**
     * Excluir item de Seção
     * TESTAR E IMPLEMENTAR A INTERFACE
     */
    public List<ItemDeSecao> obterItensSecao(Secao secao) {
        return this.catalogoDocente.obterItensSecao(secao);
    }
    public void excluirItemDeSecao(ItemDeSecao itemDeSecao, Secao secao) {
        this.catalogoDocente.excluirItemDeSecao(itemDeSecao, secao);
    }

    public Docente obterDocente() {
        return this.catalogoDocente.obterDocente();
    }

    public void modificarItemDeSecao(ItemDeSecao itemDeSecao, String nome, String novaDesc, byte[] novoAnexo) {
        this.catalogoDocente.modificarItemDeSecao(itemDeSecao, nome, novaDesc, novoAnexo);
    }

    public void modificarSecao (Secao secao, String nome) {
        this.catalogoDocente.modificarSecao(secao, nome);
    }

    public void gerarCurriculoDocumentado() throws IOException {
        this.catalogoDocente.gerarCurriculoDocumentado();
    }
}
