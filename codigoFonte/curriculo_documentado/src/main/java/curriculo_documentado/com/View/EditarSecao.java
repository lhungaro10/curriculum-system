package curriculo_documentado.com.View;

import curriculo_documentado.com.Model.Secao;
import curriculo_documentado.com.Model.SIstemaCurriculo;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.util.List;

public class EditarSecao extends JDialog {
    private boolean sectionUpdated;
    private SIstemaCurriculo sistemaCurriculo;
    private JComboBox<Secao> sectionComboBox;
    private JPanel sectionsPanel;
    private RefreshListener refreshListener;
    private JTextField nameField;

    public EditarSecao(PainelCurriculo parent, SIstemaCurriculo sistemaCurriculo, JPanel sectionsPanel, RefreshListener refreshListener) {
        super(parent, "Editar Seção", true);
        this.sistemaCurriculo = sistemaCurriculo;

        setLayout(new BorderLayout(10, 10));
        setSize(500, 150);

        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        inputPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        // Primeira linha: ComboBox para escolher a seção
        inputPanel.add(new JLabel("Seção:"));
        sectionComboBox = new JComboBox<>(obterSecoes());
        inputPanel.add(sectionComboBox);

        // Segunda linha: Campo de texto para o nome da seção
        inputPanel.add(new JLabel("Nome da Seção:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        // Atualiza o campo de texto quando o usuário seleciona outra seção
        sectionComboBox.addActionListener(e -> atualizarCampoNome());

        // Botão Salvar
        JButton saveButton = new JButton("Salvar");
        saveButton.addActionListener(e -> salvarAlteracoes(refreshListener, sectionsPanel));

        add(inputPanel, BorderLayout.CENTER);
        add(saveButton, BorderLayout.SOUTH);
        setLocationRelativeTo(parent);

        // Inicializa o campo de texto com o nome da seção selecionada inicialmente
        atualizarCampoNome();
    }

    private Secao[] obterSecoes() {
        List<Secao> secoes = sistemaCurriculo.getControlador().obterSecoes();
        return secoes.toArray(new Secao[0]);
    }


    private void atualizarCampoNome() {
        Secao secaoSelecionada = (Secao) sectionComboBox.getSelectedItem();
        if (secaoSelecionada != null) {
            nameField.setText(secaoSelecionada.getNome()); // Preenche o nome da seção selecionada
        }
    }

    private void salvarAlteracoes(RefreshListener refreshListener, JPanel sectionsPanel) {
        Secao secaoSelecionada = (Secao) sectionComboBox.getSelectedItem();
        if (secaoSelecionada == null) {
            JOptionPane.showMessageDialog(this, "Nenhuma seção selecionada!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String novoNome = nameField.getText().trim();
        if (novoNome.isEmpty()) {
            JOptionPane.showMessageDialog(this, "O nome da seção não pode estar vazio!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Chama o método modificarSecao para salvar as alterações
        sistemaCurriculo.getControlador().modificarSecao(secaoSelecionada, novoNome);

        sectionUpdated = true;
        refreshListener.refreshSections(sectionsPanel); // Atualiza o painel de seções
        JOptionPane.showMessageDialog(this, "Seção atualizada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }

    public boolean isSectionUpdated() {
        return sectionUpdated;
    }
}
