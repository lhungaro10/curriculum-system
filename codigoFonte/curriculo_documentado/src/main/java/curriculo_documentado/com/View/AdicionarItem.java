package curriculo_documentado.com.View;

import curriculo_documentado.com.Model.SIstemaCurriculo;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AdicionarItem extends JDialog {
    private boolean itemAdded;
    private SIstemaCurriculo sistemaCurriculo;

    public AdicionarItem(JFrame parent, SIstemaCurriculo sistemaCurriculo) {
        super(parent, "Novo Item", true);
        this.sistemaCurriculo = sistemaCurriculo;

        setLayout(new BorderLayout(10, 10));
        setSize(300, 150);

        JPanel inputPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        inputPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JTextField nameField = new JTextField();
        inputPanel.add(new JLabel("Nome da Seção:"));
        inputPanel.add(nameField);

        JButton saveButton = new JButton("Salvar");
        saveButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            if (!name.isEmpty()) {
//                Secao newSection = new Secao(0, name, new ArrayList<>());
                sistemaCurriculo.getControlador().cadastrarSecaoManualmente(name);
                itemAdded = true;
                dispose();
            }
        });

        add(inputPanel, BorderLayout.CENTER);
        add(saveButton, BorderLayout.SOUTH);
        setLocationRelativeTo(parent);
    }

    public boolean isItemAdded() {
        return itemAdded;
    }
}
