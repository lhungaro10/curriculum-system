package curriculo_documentado.com.Service;

import curriculo_documentado.com.Model.Docente;
import org.springframework.stereotype.Service;


@Service
public class ServiceLeitorLattes {

    public Docente criarDocenteUsandoXmlDoLattes(String xmlPath) {
        return new Docente();
    }

}
