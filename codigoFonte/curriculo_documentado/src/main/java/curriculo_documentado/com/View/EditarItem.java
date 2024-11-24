package curriculo_documentado.com.View;

import curriculo_documentado.com.Model.ItensDeSecao;
import curriculo_documentado.com.Model.Secao;
import curriculo_documentado.com.Model.SIstemaCurriculo;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.util.List;

public class EditarItem extends JDialog {
    private SIstemaCurriculo sistemaCurriculo;
    private JComboBox<Secao> sectionComboBox;
    private JComboBox<ItensDeSecao> itemComboBox;
    private JTextField nameField;
    private JTextField descricaoField;
    private JTextField anexoField;
    private boolean itemUpdated;
    private JPanel sectionsPanel;
    private RefreshListener refreshListener;

    public EditarItem(PainelCurriculo parent, SIstemaCurriculo sistemaCurriculo, JPanel sectionsPanel, RefreshListener refreshListener) {
        super(parent, "Editar Item", true);
        this.sistemaCurriculo = sistemaCurriculo;
        this.sectionsPanel = sectionsPanel;
        this.refreshListener = refreshListener;

        setLayout(new BorderLayout(10, 10));
        setSize(700, 200);

        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        inputPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Seção (ComboBox)
        inputPanel.add(new JLabel("Seção:"));
        sectionComboBox = new JComboBox<>(obterSecoes());
        sectionComboBox.addActionListener(e -> atualizarItensDeSecao());
        inputPanel.add(sectionComboBox);

        // Item da Seção (ComboBox)
        inputPanel.add(new JLabel("Item da Seção:"));
        itemComboBox = new JComboBox<>();
        itemComboBox.addActionListener(e -> preencherCamposDoItem());
        inputPanel.add(itemComboBox);

        // Nome do Item
        inputPanel.add(new JLabel("Nome do Item:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        // Descrição do Item
        inputPanel.add(new JLabel("Descrição:"));
        descricaoField = new JTextField();
        inputPanel.add(descricaoField);

        // Anexo (Texto para mostrar o caminho do arquivo)
//        inputPanel.add(new JLabel("Anexo:"));
        anexoField = new JTextField();
        anexoField.setEditable(false);
        inputPanel.add(anexoField);

        // Botão de Escolher Arquivo
        JButton chooseFileButton = new JButton("Escolher Anexo");
        chooseFileButton.addActionListener(e -> openFileChooserDialog());
        inputPanel.add(chooseFileButton);

        // Botão de Salvar
        JButton saveButton = new JButton("Salvar");
        saveButton.addActionListener(e -> {
            Secao selectedSection = (Secao) sectionComboBox.getSelectedItem();
            ItensDeSecao selectedItem = (ItensDeSecao) itemComboBox.getSelectedItem();
            String name = nameField.getText().trim();
            String descricao = descricaoField.getText().trim();
            String anexo = anexoField.getText().trim();

            if (!name.isEmpty() && selectedSection != null && selectedItem != null) {
                sistemaCurriculo.getControlador().modificarItemDeSecao(selectedItem, name, descricao, anexo);
                refreshListener.refreshSections(sectionsPanel);
                JOptionPane.showMessageDialog(this, "Item atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                itemUpdated = true;
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

//        inputPanel.add(saveButton);
        add(inputPanel, BorderLayout.CENTER);
        add(saveButton, BorderLayout.SOUTH);
        setLocationRelativeTo(parent);

        // Atualizar a lista de itens com base na seção selecionada inicialmente
        atualizarItensDeSecao();
    }

    private void atualizarItensDeSecao() {
        Secao selectedSection = (Secao) sectionComboBox.getSelectedItem();
        if (selectedSection != null) {
            List<ItensDeSecao> itens = sistemaCurriculo.getControlador().obterItensDeSecao(selectedSection);
            itemComboBox.setModel(new DefaultComboBoxModel<>(itens.toArray(new ItensDeSecao[0])));
            preencherCamposDoItem();
        }
    }

    private void preencherCamposDoItem() {
        ItensDeSecao selectedItem = (ItensDeSecao) itemComboBox.getSelectedItem();
        if (selectedItem != null) {
            nameField.setText(selectedItem.getNome());
            descricaoField.setText(selectedItem.getDescricao());
            anexoField.setText(selectedItem.getAnexo());
        } else {
            nameField.setText("");
            descricaoField.setText("");
            anexoField.setText("");
        }
    }

    private void openFileChooserDialog() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Arquivos PDF", "pdf"));

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            anexoField.setText(selectedFile.getAbsolutePath());
        }
    }

    private Secao[] obterSecoes() {
        return sistemaCurriculo.getControlador().obterSecoes().toArray(new Secao[0]);
    }

    public boolean isItemUpdated() {
        return itemUpdated;
    }
}
