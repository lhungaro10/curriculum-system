package curriculo_documentado.com.View;

import curriculo_documentado.com.Model.Secao;
import curriculo_documentado.com.Model.SIstemaCurriculo;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.util.List;

public class AdicionarItem extends JDialog {
    private boolean itemAdded;
    private SIstemaCurriculo sistemaCurriculo;
    private JComboBox<Secao> sectionComboBox;
    private JTextField nameField;
    private JTextField descricaoField;
    private JTextField anexoField;
    private JPanel sectionsPanel;
    private RefreshListener refreshListener;

    public AdicionarItem(PainelCurriculo parent, SIstemaCurriculo sistemaCurriculo, JPanel sectionsPanel, RefreshListener refreshListener) {
        super(parent, "Novo Item", true);
        this.sistemaCurriculo = sistemaCurriculo;
        this.sectionsPanel = sectionsPanel;
        this.refreshListener = refreshListener;

        setLayout(new BorderLayout(10, 10));
        setSize(700, 200);

        JPanel inputPanel = new JPanel(new GridLayout(4, 3, 5, 5));
        inputPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Seção (ComboBox)
        inputPanel.add(new JLabel("Seção:"));
        sectionComboBox = new JComboBox<>(obterSecoes());
        inputPanel.add(sectionComboBox);

        // Nome do Item
        nameField = new JTextField();
        inputPanel.add(new JLabel("Nome do Item:"));
        inputPanel.add(nameField);

        // Descrição do Item
        descricaoField = new JTextField();
        inputPanel.add(new JLabel("Descrição:"));
        inputPanel.add(descricaoField);

        // Anexo (Texto para mostrar o caminho do arquivo)
//        inputPanel.add(new JLabel("Anexo:"));
        anexoField = new JTextField();
        anexoField.setEditable(false); // Apenas para visualização do caminho do arquivo
        inputPanel.add(anexoField);

        // Botão de Salvar
        JButton saveButton = new JButton("Salvar");
        saveButton.addActionListener(e -> {
            Secao selectedSection = (Secao) sectionComboBox.getSelectedItem();
            String name = nameField.getText().trim();
            String descricao = descricaoField.getText().trim();
//            String anexo = anexoField.getText().trim();
            String anexo = "Teste Teste";
            System.out.println(anexo);

            if (!name.isEmpty() && selectedSection != null && !anexo.isEmpty()) {
                // Adiciona o item à seção selecionada
                sistemaCurriculo.getControlador().adicionarItemEmSecao(name, descricao, anexo, selectedSection);
                itemAdded = true;
//                dispose();
                refreshListener.refreshSections(sectionsPanel);
                nameField.setText("");
                descricaoField.setText("");
                anexoField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos e selecione um arquivo PDF.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Botão de escolher arquivo
        JButton chooseFileButton = new JButton("Escolher Anexo");
        chooseFileButton.addActionListener(e -> {
            // Chama o método para abrir o modal do JFileChooser
            openFileChooserDialog();
        });

        inputPanel.add(chooseFileButton); // Adiciona o botão ao painel
        add(inputPanel, BorderLayout.CENTER);
        add(saveButton, BorderLayout.SOUTH);
        setLocationRelativeTo(parent);
    }

    private void openFileChooserDialog() {
        // Criar um JDialog separado para o JFileChooser
        JDialog fileChooserDialog = new JDialog(this, "Escolher Arquivo", true);
        fileChooserDialog.setSize(700, 400);
        fileChooserDialog.setLayout(new BorderLayout());

        // Cria um JFileChooser
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Arquivos PDF", "pdf"));

        // Painel de exibição do JFileChooser
        JPanel panel = new JPanel();
        panel.add(fileChooser);
        fileChooserDialog.add(panel, BorderLayout.CENTER);

        // Botões de ação
        JPanel buttonPanel = new JPanel();
        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            File selectedFile = fileChooser.getSelectedFile();
            if (selectedFile != null) {
                // Atualiza o campo de anexo com o caminho do arquivo
                anexoField.setText(selectedFile.getAbsolutePath());
            }
            fileChooserDialog.dispose(); // Fecha o dialog após a seleção
        });

        JButton cancelButton = new JButton("Cancelar");
        cancelButton.addActionListener(e -> fileChooserDialog.dispose()); // Fecha o dialog sem fazer nada

        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        fileChooserDialog.add(buttonPanel, BorderLayout.SOUTH);

        // Exibe o JDialog
        fileChooserDialog.setLocationRelativeTo(this);
        fileChooserDialog.setVisible(true);
    }

    private Secao[] obterSecoes() {
        List<Secao> secoes = sistemaCurriculo.getControlador().obterSecoes();
        return secoes.toArray(new Secao[0]);
    }

    public boolean isItemAdded() {
        return itemAdded;
    }
}
