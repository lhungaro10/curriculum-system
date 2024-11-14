package curriculo_documentado.com.View;

import curriculo_documentado.com.Model.SIstemaCurriculo;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PainelCurriculo extends JFrame {
    private SIstemaCurriculo sistemaCurriculo;
    private JPanel mainPanel;
    private JPanel sectionsPanel;

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

        refreshSections();
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
//        editSecaoItem.addActionListener(e -> showEditSectionDialog());

        // Excluir Seção
        JMenuItem deleteSecaoItem = new JMenuItem("Excluir Seção");
//        deleteSecaoItem.addActionListener(e -> showDeleteSectionDialog());

        // Adicionar Item
        JMenuItem addItensItem = new JMenuItem("Adicionar Item");
        addItensItem.addActionListener(e -> ShowAddItemDialog());
        

        secaoMenu.add(addSecaoItem);
        secaoMenu.add(editSecaoItem);
        secaoMenu.add(deleteSecaoItem);

        menuBar.add(secaoMenu);
        return menuBar;
    }

    private void ShowAddItemDialog() {

    }

    private void showAddSectionDialog() {
        AdicionarSecao dialog = new AdicionarSecao(this, sistemaCurriculo);
        dialog.setVisible(true);
        if (dialog.isSectionAdded()) {
            refreshSections();
        }
    }

//    private void showEditSectionDialog() {
//        EditSectionDialog dialog = new EditSectionDialog(this, sistemaCurriculo);
//        dialog.setVisible(true);
//        if (dialog.isSectionUpdated()) {
//            refreshSections();
//        }
//    }
//
//    private void showDeleteSectionDialog() {
//        DeleteSectionDialog dialog = new DeleteSectionDialog(this, sistemaCurriculo);
//        dialog.setVisible(true);
//        if (dialog.isSectionDeleted()) {
//            refreshSections();
//        }
//    }

    private void refreshSections() {
//        sectionsPanel.removeAll();
//        var sections = sistemaCurriculo.getCatalogoDocente().getSecoes();
//
//        for (Secao section : sections) {
//            addSectionPanel(section);
//        }
//
//        sectionsPanel.revalidate();
//        sectionsPanel.repaint();
    }

//    private void addSectionPanel(Secao section) {
//        // Code to add a section panel goes here
//    }
}