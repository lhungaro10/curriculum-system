package curriculo_documentado.com.View;

import curriculo_documentado.com.Model.SIstemaCurriculo;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class TelaImportacao extends JDialog {

    private final SIstemaCurriculo sistemaCurriculo;

    public TelaImportacao(JFrame parent, SIstemaCurriculo sistemaCurriculo) {
        super(parent, "Importar Currículo", true);
        this.sistemaCurriculo = sistemaCurriculo;

        setSize(500, 200);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JButton btnEscolherArquivo = new JButton("Escolher XML");
        JButton btnImportar = new JButton("Importar");

        JTextField txtArquivo = new JTextField(30);
        txtArquivo.setEditable(false);

        panel.add(btnEscolherArquivo);
        panel.add(txtArquivo);
        panel.add(btnImportar);

        add(panel, BorderLayout.CENTER);

        btnEscolherArquivo.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("XML files", "xml"));
            int returnValue = fileChooser.showOpenDialog(this);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                txtArquivo.setText(selectedFile.getAbsolutePath());
            }
        });

        btnImportar.addActionListener(e -> {
            String filePath = txtArquivo.getText();
            if (!filePath.isEmpty()) {
                sistemaCurriculo.getControlador().adicionarDocenteComLattes(filePath);
                JOptionPane.showMessageDialog(this, "Importação concluída", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                parent.dispose();
                PainelCurriculo painelCurriculo = new PainelCurriculo(sistemaCurriculo);
                painelCurriculo.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, escolha um arquivo XML.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

}
