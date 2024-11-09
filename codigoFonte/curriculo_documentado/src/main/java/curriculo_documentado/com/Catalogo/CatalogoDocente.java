package curriculo_documentado.com.Catalogo;

import curriculo_documentado.com.InterfaceJpa.InterfaceJpa;
import curriculo_documentado.com.Model.Docente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@SuppressWarnings("all")
@Component
public class CatalogoDocente {
    private Optional<Docente> docente;
    private InterfaceJpa interfaceJpa;

    @Autowired
    public CatalogoDocente(InterfaceJpa interfaceJpa) {
        this.interfaceJpa = interfaceJpa;
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

}
