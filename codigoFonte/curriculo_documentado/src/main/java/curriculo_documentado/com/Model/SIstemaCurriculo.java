package curriculo_documentado.com.Model;

import curriculo_documentado.com.Catalogo.CatalogoDocente;
import curriculo_documentado.com.Controller.Controlador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SIstemaCurriculo {
    private Controlador controlador;
    private CatalogoDocente catalogoDocente;

    @Autowired
    public SIstemaCurriculo(Controlador controlador, CatalogoDocente catalogoDocente){
        this.controlador = controlador;
        this.catalogoDocente = catalogoDocente;
    }

    public SIstemaCurriculo() {
    }

    public Controlador getControlador() {
        return controlador;
    }

    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
    }

    public CatalogoDocente getCatalogoDocente() {
        return catalogoDocente;
    }

    public void setCatalogoDocente(CatalogoDocente catalogoDocente) {
        this.catalogoDocente = catalogoDocente;
    }

    public void fecharSistema() {
        this.catalogoDocente.salvarDadosFechamento();
    }

    @Override
    public String toString() {
        return "SIstemaCurriculo{" +
                "controlador=" + controlador +
                ", catalogoDocente=" + catalogoDocente +
                '}';
    }
}
