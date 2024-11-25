package curriculo_documentado.com.View;

import curriculo_documentado.com.Model.Secao;
import curriculo_documentado.com.Model.SIstemaCurriculo;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class AdicionarItem extends JDialog {
    private boolean itemAdded;
    private SIstemaCurriculo sistemaCurriculo;
    private JComboBox<Secao> sectionComboBox;
    private JTextField nameField;
    private JTextField descricaoField;
    private byte[] anexoField; // Alterado para byte[] para armazenar o conteúdo do PDF
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

        // Botão de Salvar
        JButton saveButton = new JButton("Salvar");
        saveButton.addActionListener(e -> {
            Secao selectedSection = (Secao) sectionComboBox.getSelectedItem();
            String name = nameField.getText().trim();
            String descricao = descricaoField.getText().trim();

            if (!name.isEmpty() && selectedSection != null && anexoField != null && anexoField.length > 0) {
                // Adiciona o item à seção selecionada
                sistemaCurriculo.getControlador().adicionarItemEmSecao(name, descricao, anexoField, selectedSection);
                itemAdded = true;
                refreshListener.refreshSections(sectionsPanel);
                nameField.setText("");
                descricaoField.setText("");
                anexoField = null;
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
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Arquivos PDF", "pdf"));

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            if (selectedFile != null) {
                try {
                    // Lê o conteúdo do arquivo PDF como byte[]
                    anexoField = Files.readAllBytes(selectedFile.toPath());
                    JOptionPane.showMessageDialog(this, "Arquivo selecionado: " + selectedFile.getName(), "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao ler o arquivo.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private Secao[] obterSecoes() {
        List<Secao> secoes = sistemaCurriculo.getControlador().obterSecoes();
        return secoes.toArray(new Secao[0]);
    }

    public boolean isItemAdded() {
        return itemAdded;
    }
}
