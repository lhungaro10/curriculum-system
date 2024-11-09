package curriculo_documentado.com.View;

import curriculo_documentado.com.Config.appConfig;
import curriculo_documentado.com.Config.jpaConfig;
import curriculo_documentado.com.Model.SIstemaCurriculo;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Tela extends JFrame {
    private static SIstemaCurriculo sistemaCurriculo;

    public Tela(ApplicationContext context) {
        // Configura o tamanho da janela
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Sistema de Currículo");

        // Inicializa o contexto de aplicação
        sistemaCurriculo = context.getBean(SIstemaCurriculo.class);

        // Cria o painel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Cria o botão de cadastro manual
        JButton btnCadastrarManual = new JButton("Cadastrar Manualmente");
        btnCadastrarManual.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CadastroManual cadastroManual = new CadastroManual(Tela.this, sistemaCurriculo);
                cadastroManual.setVisible(true);
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 10, 0);
        mainPanel.add(btnCadastrarManual, gbc);

        // Cria o botão de exportação
        JButton btnExportar = new JButton("Importar");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 0, 10, 0);
        mainPanel.add(btnExportar, gbc);

        // Adiciona o painel principal à janela
        add(mainPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        // Inicializa o contexto de configuração JPA
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(appConfig.class, jpaConfig.class);

        SwingUtilities.invokeLater(() -> {
            sistemaCurriculo = context.getBean(SIstemaCurriculo.class);
            var docente = sistemaCurriculo.getCatalogoDocente().getDocente();
            if (docente.isEmpty()) {
                new Tela(context).setVisible(true);
            } else {
                System.out.println("erro");
            }
        });

        // Adiciona um hook de encerramento para fechar o contexto ao finalizar o programa
        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            sistemaCurriculo.fecharSistema();
            context.close();
        }));
    }
}