package curriculo_documentado.com.Service;

import curriculo_documentado.com.Model.Docente;
import curriculo_documentado.com.Model.Secao;
import curriculo_documentado.com.Model.ItemDeSecao;
import org.springframework.stereotype.Service;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ServiceLeitorLattes {

    // Mapeamento das sessões e itens esperados
    private static final Map<String, Map<String, String>> SESSOES_POSSIVEIS = Map.of(
            "DADOS-GERAIS", Map.of(
                    "RESUMO-CV", "TEXTO-RESUMO-CV-RH"
            ),
            "FORMACAO-ACADEMICA-TITULACAO", Map.of(
                    "GRADUACAO", "NOME-CURSO",
                    "MESTRADO", "TITULO-DA-DISSERTACAO-TESE",
                    "DOUTORADO", "TITULO-DA-DISSERTACAO-TESE",
                    "POS-DOUTORADO", "TITULO-DO-TRABALHO",
                    "LIVRE-DOCENCIA", "TITULO-DO-TRABALHO",
                    "ESPECIALIZACAO", "NOME-CURSO"
            ),
            "ATUACOES-PROFISSIONAIS", Map.of(
                    "ATUACAO-PROFISSIONAL", "NOME-INSTITUICAO"
            ),
            "ATIVIDADES-DE-ENSINO", Map.of(
                    "ENSINO", "NOME-CURSO" // Descrição está dentro da tag <DISCIPLINA>
            ),
            "ATIVIDADES-DE-TREINAMENTO-MINISTRADO", Map.of(
                    "TREINAMENTO-MINISTRADO", "TREINAMENTO" // Indica que a descrição está na subtag <TREINAMENTO>
            ),
            "ATIVIDADES-DE-EXTENSAO-UNIVERSITARIA", Map.of(
                    "EXTENSAO-UNIVERSITARIA", "ATIVIDADE-DE-EXTENSAO-REALIZADA"
            ),
            "ATIVIDADES-DE-PARTICIPACAO-EM-PROJETO", Map.of(
                    "PROJETO-DE-PESQUISA", "NOME-DO-PROJETO"
            ),
            "ATIVIDADES-DE-CONSELHO-COMISSAO-E-CONSULTORIA", Map.of(
                    "CONSELHO-COMISSAO-E-CONSULTORIA", "ESPECIFICACAO"
            ),
            "OUTRAS-ATIVIDADES-TECNICO-CIENTIFICA", Map.of(
                    "OUTRA-ATIVIDADE-TECNICO-CIENTIFICA", "ATIVIDADE-REALIZADA"
            ),
            "PRODUCOES-CT-DO-PROJETO", Map.of(
                    "PRODUCAO-CT-DO-PROJETO", "TITULO-DA-PRODUCAO-CT"
            )
    );

    public Docente criarDocenteUsandoXmlDoLattes(String xmlPath) {
        try {
            // Carregar o XML
            File xmlFile = new File(xmlPath);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            // Extração dos dados gerais do docente
            Element dadosGerais = (Element) doc.getElementsByTagName("DADOS-GERAIS").item(0);
            String nome = dadosGerais.getAttribute("NOME-COMPLETO");
            String cpf = dadosGerais.getAttribute("CPF");
            String sexo = dadosGerais.getAttribute("SEXO");
            String nomeInstituicao = dadosGerais.getAttribute("NOME-INSTITUICAO-EMPRESA");
            Date dataNascimento = null; // Foda-se
            String resumo = dadosGerais.getElementsByTagName("TEXTO-RESUMO-CV-RH").item(0).getTextContent();

            // Criação do objeto Docente
            Docente docente = new Docente(nome, dataNascimento, nomeInstituicao, cpf, sexo, resumo);

            // Iteração pelas sessões possíveis
            for (Map.Entry<String, Map<String, String>> entrySecao : SESSOES_POSSIVEIS.entrySet()) {
                String nomeSecao = entrySecao.getKey();
                Map<String, String> itensSecao = entrySecao.getValue();
                NodeList secaoNodes = doc.getElementsByTagName(nomeSecao);

                for (int i = 0; i < secaoNodes.getLength(); i++) {
                    Element secaoElement = (Element) secaoNodes.item(i);
                    Secao secao = new Secao(nomeSecao);

                    for (Map.Entry<String, String> entryItem : itensSecao.entrySet()) {
                        String nomeItem = entryItem.getKey();
                        String subElementoOuAtributo = entryItem.getValue(); // Subtag ou atributo
                        NodeList itemNodes = secaoElement.getElementsByTagName(nomeItem);

                        for (int j = 0; j < itemNodes.getLength(); j++) {
                            Element itemElement = (Element) itemNodes.item(j);

                            String descricao;
                            if (subElementoOuAtributo != null) {
                                // Caso subElementoOuAtributo seja uma subtag
                                NodeList subElementos = itemElement.getElementsByTagName(subElementoOuAtributo);
                                if (subElementos.getLength() > 0) {
                                    descricao = subElementos.item(0).getTextContent().trim();
                                } else {
                                    // Caso subElementoOuAtributo seja um atributo
                                    descricao = itemElement.getAttribute(subElementoOuAtributo).trim();
                                }
                            } else {
                                // Conteúdo direto da tag principal
                                descricao = itemElement.getTextContent().trim();
                            }

                            // Criar e adicionar o item de seção
                            ItemDeSecao itemDeSecao = new ItemDeSecao(null, nomeItem, descricao);
                            secao.addItemSecao(itemDeSecao);
                        }
                    }

                    // Adicionar a seção ao docente
                    docente.addSecao(secao);
                }
            }


            return docente;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao processar o XML do Lattes", e);
        }
    }
}
