package curriculo_documentado.com.View;

import curriculo_documentado.com.Model.SIstemaCurriculo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GerarCurriculoDocumentado extends JFrame {

    public GerarCurriculoDocumentado(SIstemaCurriculo sistemaCurriculo) {
        // Configurações da janela
        setTitle("Gerar Currículo Documentado");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Centraliza a janela na tela
        setResizable(false);

        // Criação do botão "Gerar"
        JButton btnGerar = new JButton("Gerar");
        btnGerar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Chama o método do controlador
                    sistemaCurriculo.getControlador().gerarCurriculoDocumentado();
                    JOptionPane.showMessageDialog(GerarCurriculoDocumentado.this, "Currículo gerado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(GerarCurriculoDocumentado.this, "Erro ao gerar currículo: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        add(btnGerar);

        // Torna a janela visível
        setVisible(true);
    }
}
