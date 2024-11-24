package curriculo_documentado.com.View;

import curriculo_documentado.com.Model.Docente;
import curriculo_documentado.com.Model.ItensDeSecao;
import curriculo_documentado.com.Model.SIstemaCurriculo;
import curriculo_documentado.com.Model.Secao;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class PainelCurriculo extends JFrame implements RefreshListener{
    private SIstemaCurriculo sistemaCurriculo;
    private JPanel mainPanel;
    private JPanel sectionsPanel;

    public PainelCurriculo() {}

    public PainelCurriculo(SIstemaCurriculo sistemaCurriculo) {
        this.sistemaCurriculo = sistemaCurriculo;

        setTitle("Currículo");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create menu bar
        setJMenuBar(createMenuBar());

        // Main panel with BorderLayout
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Scrollable panel for sections
        sectionsPanel = new JPanel();
        sectionsPanel.setLayout(new BoxLayout(sectionsPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(sectionsPanel);

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);

        refreshSections(sectionsPanel);
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu secaoMenu = new JMenu("Seção");
        JMenu itensMenu = new JMenu("Itens");

        // Adicionar Seção
        JMenuItem addSecaoItem = new JMenuItem("Adicionar Seção");
        addSecaoItem.addActionListener(e -> showAddSectionDialog());

        // Alterar Seção
        JMenuItem editSecaoItem = new JMenuItem("Alterar Seção");
        editSecaoItem.addActionListener(e -> showEditSectionDialog());

        // Excluir Seção
        JMenuItem deleteSecaoItem = new JMenuItem("Excluir Seção");
//        deleteSecaoItem.addActionListener(e -> showDeleteSectionDialog());

        // Adicionar Item
        JMenuItem addItensItem = new JMenuItem("Adicionar Item");
        addItensItem.addActionListener(e -> ShowAddItemDialog());

        JMenuItem editItensItem = new JMenuItem("Alterar Item");
        editItensItem.addActionListener(e -> showEditItemDialog());

        itensMenu.add(addItensItem);
        itensMenu.add(editItensItem);

        secaoMenu.add(addSecaoItem);
        secaoMenu.add(editSecaoItem);
        secaoMenu.add(deleteSecaoItem);

        menuBar.add(secaoMenu);
        menuBar.add(itensMenu);
        return menuBar;
    }

    private void ShowAddItemDialog() {
        AdicionarItem dialog = new AdicionarItem(this, sistemaCurriculo, sectionsPanel, this);
        dialog.setVisible(true);
        if (dialog.isItemAdded()) {
            refreshSections(sectionsPanel);
        }
    }

    private void showAddSectionDialog() {
        AdicionarSecao dialog = new AdicionarSecao(this, sistemaCurriculo, sectionsPanel, this);
        dialog.setVisible(true);
        if (dialog.isSectionAdded()) {
            refreshSections(sectionsPanel);
        }
    }

    private void showEditSectionDialog() {
        EditarSecao dialog = new EditarSecao(this, sistemaCurriculo, sectionsPanel, this);
        dialog.setVisible(true);
        if (dialog.isSectionUpdated()) {
            refreshSections(sectionsPanel);
        }
    }

    private void showEditItemDialog() {
        EditarItem dialog = new EditarItem(this, sistemaCurriculo, sectionsPanel, this);
        dialog.setVisible(true);
        if (dialog.isItemUpdated()) {
            refreshSections(sectionsPanel);
        }
    }
//
//    private void showDeleteSectionDialog() {
//        DeleteSectionDialog dialog = new DeleteSectionDialog(this, sistemaCurriculo);
//        dialog.setVisible(true);
//        if (dialog.isSectionDeleted()) {
//            refreshSections();
//        }
//    }

    @Override
    public void refreshSections(JPanel sectionsPanel) {
        // Limpa o conteúdo atual do sectionsPanel
        sectionsPanel.removeAll();

        // Obtém as seções do sistema
        List<Secao> sections = sistemaCurriculo.getControlador().obterSecoes();
        Docente docente = sistemaCurriculo.getControlador().obterDocente();

        // Verifica se a lista de seções está vazia
        if (sections.isEmpty()) {
            // Se não houver seções, exibe a imagem centralizada
            addImagePanel();
        } else {
            // Inicializa o HTML consolidado para o documento com estilos CSS embutidos
            StringBuilder htmlContent = new StringBuilder("<html><body style='margin: 0; padding: 0;'>");

            // Define a estrutura do documento com largura máxima e quebras automáticas
            htmlContent.append("<div style='max-width: 600px; margin: 0 auto; padding-right: 20px; word-wrap: break-word;'>");

            htmlContent.append("<h1 style='margin: 5px 0;'>").append(docente.getNome()).append("</h1>");
            htmlContent.append("<h2 style='margin: 5px 0;'>").append(docente.getNomeInstituicao()).append("</h2>");
            htmlContent.append("<hr style='border: 1px solid #000; margin: 10px 0;'>");
            // Adiciona cada seção e seus itens ao HTML
            for (Secao section : sections) {
                htmlContent.append("<h2 style='margin: 5px 0;'>").append(section.getNome()).append("</h2>");
                htmlContent.append("<ul style='margin: 10px; padding-left: 20px;'>");

                for (ItensDeSecao item : section.getItensDeSecao()) {
                    htmlContent.append("<li style='margin-bottom: 5px;'><b>")
                            .append(item.getNome()).append("</b>: ")
                            .append(item.getDescricao()).append("</li>");
                }

                htmlContent.append("</ul>");
            }

            // Fecha o contêiner e o HTML consolidado
            htmlContent.append("</div></body></html>");

            // Configura o JEditorPane para exibir o conteúdo HTML consolidado
            JEditorPane editorPane = new JEditorPane();
            editorPane.setContentType("text/html");
            editorPane.setText(htmlContent.toString());
            editorPane.setBorder(new EmptyBorder(10, 10, 10, 10));
            editorPane.setEditable(false);

            // Adiciona o JEditorPane em um JScrollPane para rolagem
            JScrollPane scrollPane = new JScrollPane(editorPane);
            scrollPane.setPreferredSize(new Dimension(600, 400)); // Define um tamanho fixo para o JScrollPane, se necessário

            sectionsPanel.setLayout(new BorderLayout()); // Define o layout do sectionsPanel
            sectionsPanel.add(scrollPane, BorderLayout.CENTER);
        }

        // Atualiza a interface
        sectionsPanel.revalidate();
        sectionsPanel.repaint();
    }


    private void addImagePanel() {
        // Criar o painel para exibir a imagem, ocupando todo o espaço do sectionsPanel
        JPanel imagePanel = new JPanel(new GridBagLayout()); // GridBagLayout centraliza o conteúdo
        imagePanel.setPreferredSize(sectionsPanel.getSize()); // Garante que o painel ocupe o espaço do sectionsPanel

        // Carregar a imagem da pasta src/main/resources
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/sem_resultado.png"));

        // Redimensionar a imagem para 200x200 pixels
        Image scaledImage = originalIcon.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(scaledImage);

        // Criar um JLabel para exibir a imagem redimensionada
        JLabel imageLabel = new JLabel(resizedIcon);

        // Adicionar o JLabel ao painel de imagem
        imagePanel.add(imageLabel, new GridBagConstraints()); // Centraliza o JLabel no painel

        // Adicionar o painel de imagem ao painel principal de seções
        sectionsPanel.add(imagePanel);
    }

    private void addSectionPanel(Secao section) {
        // Cria o conteúdo HTML para a seção e seus itens
        StringBuilder htmlContent = new StringBuilder("<html>");

        // Adiciona o título da seção (nome)
        htmlContent.append("<h2>").append(section.getNome()).append("</h2>");

        // Adiciona cada item da seção em uma lista
        htmlContent.append("<ul>");
        for (ItensDeSecao item : section.getItensDeSecao()) {
            htmlContent.append("<li><b>").append(item.getNome()).append("</b>: ")
                    .append(item.getDescricao()).append("</li>");
        }
        htmlContent.append("</ul>");
        htmlContent.append("</html>");

        // Cria o JEditorPane para exibir o conteúdo HTML
        JEditorPane sectionEditorPane = new JEditorPane();
        sectionEditorPane.setContentType("text/html");
        sectionEditorPane.setText(htmlContent.toString());
        sectionEditorPane.setEditable(false); // Torna o campo de texto somente leitura

        // Adiciona um JScrollPane para rolagem, se necessário
        JScrollPane scrollPane = new JScrollPane(sectionEditorPane);
        scrollPane.setPreferredSize(new Dimension(300, 100)); // Ajuste conforme necessário

        // Adiciona o JScrollPane ao painel principal de seções
        sectionsPanel.add(scrollPane);
    }

}