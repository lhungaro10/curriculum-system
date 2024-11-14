package curriculo_documentado.com.Controller;

import curriculo_documentado.com.Catalogo.CatalogoDocente;
import curriculo_documentado.com.Model.ItensDeSecao;
import curriculo_documentado.com.Model.Secao;
import org.springframework.stereotype.Component;

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
    public void adicionarItemEmSecao(String nome, String descricao, String anexoPdf, Secao secao) {
        this.catalogoDocente.adicionarItemEmSecao(nome, descricao, anexoPdf, secao);
    }

    /**
     * Excluir item de Seção
     * TESTAR E IMPLEMENTAR A INTERFACE
     */
    public List<ItensDeSecao> obterItensSecao(Secao secao) {
        return this.catalogoDocente.obterItensSecao(secao);
    }
    public void excluirItemDeSecao(ItensDeSecao itensDeSecao, Secao secao) {
        this.catalogoDocente.excluirItemDeSecao(itensDeSecao, secao);
    }
}
