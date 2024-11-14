package curriculo_documentado.com.View;

import curriculo_documentado.com.Model.Secao;
import curriculo_documentado.com.Model.SIstemaCurriculo;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class AdicionarItem extends JDialog {
    private boolean itemAdded;
    private SIstemaCurriculo sistemaCurriculo;
    private JComboBox<Secao> sectionComboBox;
    private JTextField nameField;
    private JTextField descricaoField;
    private JTextField anexoField;

    public AdicionarItem(JFrame parent, SIstemaCurriculo sistemaCurriculo) {
        super(parent, "Novo Item", true);
        this.sistemaCurriculo = sistemaCurriculo;

        setLayout(new BorderLayout(10, 10));
        setSize(400, 200);

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        inputPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        inputPanel.add(new JLabel("Seção:"));
        sectionComboBox = new JComboBox<>(obterSecoes());
        inputPanel.add(sectionComboBox);

        nameField = new JTextField();
        inputPanel.add(new JLabel("Nome do Item:"));
        inputPanel.add(nameField);

        descricaoField = new JTextField();
        inputPanel.add(new JLabel("Descrição:"));
        inputPanel.add(descricaoField);

        anexoField = new JTextField();
        inputPanel.add(new JLabel("Anexo:"));
        inputPanel.add(anexoField);

        JButton saveButton = new JButton("Salvar");
        saveButton.addActionListener(e -> {
            Secao selectedSection = (Secao) sectionComboBox.getSelectedItem();
            String name = nameField.getText().trim();
            String descricao = descricaoField.getText().trim();
            String anexo = anexoField.getText().trim();

            if (!name.isEmpty() && selectedSection != null) {
                sistemaCurriculo.getControlador().adicionarItemEmSecao(name, descricao, anexo, selectedSection);
                itemAdded = true;
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        add(inputPanel, BorderLayout.CENTER);
        add(saveButton, BorderLayout.SOUTH);
        setLocationRelativeTo(parent);
    }

    private Secao[] obterSecoes() {
        List<Secao> secoes = sistemaCurriculo.getControlador().obterSecoes();
        return secoes.toArray(new Secao[0]);
    }

    public boolean isItemAdded() {
        return itemAdded;
    }
}