package curriculo_documentado.com.Controller;

import curriculo_documentado.com.Catalogo.CatalogoDocente;
import org.springframework.stereotype.Component;

import java.util.Date;

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
}
