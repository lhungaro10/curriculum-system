package curriculo_documentado.com.View;

import curriculo_documentado.com.Model.Docente;
import curriculo_documentado.com.Model.SIstemaCurriculo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.text.SimpleDateFormat;

public class CadastroManual extends JDialog {
    private JTextField nomeField;
    private JTextField dataNascimentoField;
    private JTextField nomeInstituicaoField;
    private JTextField cpfField;
    private JTextField sexoField;
    private JTextArea resumoCurriculoArea;
    private SIstemaCurriculo sistemaCurriculo;

    public CadastroManual(JFrame parent, SIstemaCurriculo sistemaCurriculo) {
        super(parent, "Cadastro Manual", true); // true define como modal
        setSize(400, 400);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        this.sistemaCurriculo = sistemaCurriculo;

        // Painel principal
        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10));

        // Campos de entrada
        panel.add(new JLabel("Nome:"));
        nomeField = new JTextField();
        panel.add(nomeField);

        panel.add(new JLabel("Data de Nascimento (YYYY-MM-DD):"));
        dataNascimentoField = new JTextField();
        panel.add(dataNascimentoField);

        panel.add(new JLabel("Nome da Instituição:"));
        nomeInstituicaoField = new JTextField();
        panel.add(nomeInstituicaoField);

        panel.add(new JLabel("CPF:"));
        cpfField = new JTextField();
        panel.add(cpfField);

        panel.add(new JLabel("Sexo:"));
        sexoField = new JTextField();
        panel.add(sexoField);

        panel.add(new JLabel("Resumo do Currículo:"));
        resumoCurriculoArea = new JTextArea(5, 20);
        JScrollPane scrollPane = new JScrollPane(resumoCurriculoArea);
        panel.add(scrollPane);

        add(panel, BorderLayout.CENTER);

        // Botão de salvar
        JButton saveButton = new JButton("Salvar");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarDocente();
                parent.dispose();
                PainelCurriculo painelCurriculo = new PainelCurriculo(sistemaCurriculo);
                painelCurriculo.setVisible(true);

            }
        });
        add(saveButton, BorderLayout.SOUTH);
    }

    private void salvarDocente() {
        try {

            var nome = nomeField.getText();
            var dataNascimento = dataNascimentoField.getText();
            var nomeInstituicao = nomeInstituicaoField.getText();
            var cpf = cpfField.getText();
            var sexo = sexoField.getText();
            var resumoCurriculo = resumoCurriculoArea.getText();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date formatDate = sdf.parse(dataNascimento);

            var controlador = sistemaCurriculo.getControlador();
            controlador.adicionarDocenteManualmente(nome, formatDate, nomeInstituicao, cpf, sexo, resumoCurriculo);

            // Aqui você pode adicionar o código para salvar o docente no banco de dados,
            // por exemplo, usando o sistemaCurriculo ou qualquer outra lógica de persistência.

            JOptionPane.showMessageDialog(this, "Docente salvo com sucesso!");
            dispose(); // Fecha o diálogo após salvar
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar docente: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
