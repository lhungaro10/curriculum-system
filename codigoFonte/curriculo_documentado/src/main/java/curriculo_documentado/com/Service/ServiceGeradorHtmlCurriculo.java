package curriculo_documentado.com.Service;

import curriculo_documentado.com.Model.CurriculoDocumentado;
import curriculo_documentado.com.Model.ItemDeSecao;
import curriculo_documentado.com.Model.Secao;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class ServiceGeradorHtmlCurriculo {



    public void gerarCurriculoHtmlPdf(CurriculoDocumentado curriculo) throws IOException {
        var dowloads_dir = System.getProperty("user.home") + "/Downloads/";
        File downloadDir = new File(dowloads_dir);
        if (!downloadDir.exists()) {
            downloadDir.mkdirs();
        }


        File htmlFile = new File(downloadDir, "curriculo_" + curriculo.getDocente().getCpf() + ".html");

        String htmlContent = gerarHtml(curriculo, downloadDir);
        try (FileOutputStream fos = new FileOutputStream(htmlFile)) {
            fos.write(htmlContent.getBytes());
        }

        System.out.println("HTML gerado em: " + htmlFile.getAbsolutePath());
    }

    private String gerarHtml(CurriculoDocumentado curriculo, File downloadDir) throws IOException {
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>");
        html.append("<html lang='pt-BR'>");
        html.append("<head>");
        html.append("<meta charset='UTF-8'>");
        html.append("<title>Currículo Documentado</title>");
        html.append("<style>");
        html.append("body { font-family: Arial, sans-serif; line-height: 1.4; margin: 20px; }");
        html.append("h1, h2 { color: #333; margin-bottom: 5px; }");
        html.append("h3 { color: #555; margin: 5px 0; }");
        html.append(".item-info { display: flex; justify-content: space-between; margin: 5px 0; }");
        html.append(".item-info div { flex: 1; margin-right: 10px; word-wrap: break-word; overflow-wrap: break-word; }");
        html.append(".item-info div:last-child { margin-right: 0; text-align: right; }");
        html.append(".descricao { max-width: 50%; }"); // Limita a largura da descrição
        html.append("hr { border: 0; border-top: 1px solid #ccc; margin: 10px 0; }");
        html.append("a { color: #007BFF; text-decoration: none; }");
        html.append("a:hover { text-decoration: underline; }");
        html.append("</style>");
        html.append("</head>");
        html.append("<body>");
        html.append("<h1>Currículo de ").append(curriculo.getDocente().getNome()).append("</h1>");
        html.append("<p><strong>CPF:</strong> ").append(curriculo.getDocente().getCpf()).append("</p>");
        html.append("<p><strong>Resumo Curricular:</strong> ").append(curriculo.getDocente().getResumoCurriculo()).append("</p>");

        // Adicionar as seções
        for (Secao secao : curriculo.getSecoes()) {
            html.append("<hr>"); // Linha de separação entre as seções
            html.append("<h2>").append(secao.getNome()).append("</h2>");

            for (ItemDeSecao item : secao.getItensDeSecao()) {
                // Gerar o PDF para o item
                String pdfFileName = item.getNome().replaceAll("\\s+", "_") + ".pdf";
                File pdfFile = new File(downloadDir, pdfFileName);
                gerarPdf(item.getAnexo(), pdfFile);

                // Adicionar informações do item na mesma linha
                html.append("<div class='item-info'>");
                html.append("<div><strong>Item:</strong> ").append(item.getNome()).append("</div>");
                html.append("<div class='descricao'><strong>Descrição:</strong> ").append(item.getDescricao()).append("</div>");
                html.append("<div><a href='./").append(pdfFileName).append("' target='_blank'>Abrir PDF</a></div>");
                html.append("</div>");
            }
        }

        html.append("</body>");
        html.append("</html>");
        return html.toString();
    }



    private void gerarPdf(byte[] comprovante, File pdfFile) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(pdfFile)) {
            fos.write(comprovante);
        }
    }
}
