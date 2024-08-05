package presentation.Token;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import businesslogic.BusinessLogicInterface;
import dtos.PoemDTO;
import dtos.VerseDTO;

public class DisplayRootsForm extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    private JList<String> rootsList;
    private JList<String> versesList;
    private DefaultListModel<String> rootsListModel;
    private DefaultListModel<String> versesListModel;
    private JTextArea poemTextArea;
    private BusinessLogicInterface bllobj;
    JScrollPane rootsScrollPane;
    JScrollPane versesScrollPane;
    List<Integer> poemIdList = new ArrayList<>();

    public DisplayRootsForm(BusinessLogicInterface bll) {
        this.bllobj = bll;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 400);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        JLabel titleLabel = new JLabel("موسوعة الشعر العربية في العصر الجاهلية");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
        titleLabel.setForeground(Color.LIGHT_GRAY);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Customize the panel
        panel.setBorder(new EmptyBorder(new Insets(15, 15, 15, 15)));
        panel.setBackground(Color.DARK_GRAY);

        JPanel rootsPanel = new JPanel(new BorderLayout());
        rootsListModel = new DefaultListModel<>();
        rootsList = new JList<>(rootsListModel);
        rootsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        rootsScrollPane = new JScrollPane(rootsList);
        rootsScrollPane.setBorder(BorderFactory.createTitledBorder("List of Roots"));

        versesListModel = new DefaultListModel<>();
        versesList = new JList<>(versesListModel);
        versesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        versesScrollPane = new JScrollPane(versesList);
        //code for resizing the versesScrollPane
        Dimension preferredSize = versesScrollPane.getPreferredSize();
        preferredSize.width = 300; // Adjust this width as needed
        versesScrollPane.setPreferredSize(preferredSize);

        versesScrollPane.setBorder(BorderFactory.createTitledBorder("List of Verses"));

        poemTextArea = new JTextArea();
        poemTextArea.setEditable(false);
        poemTextArea.setFont(new Font("Arial", Font.PLAIN, 16)); // Set font size to 16
        JScrollPane poemScrollPane = new JScrollPane(poemTextArea);
        poemScrollPane.setBorder(BorderFactory.createTitledBorder("Poem of the Selected Verse : "));
       

        rootsList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) { // Single-click
                    try {
                        displayVerses();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        versesList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) { // Single-click
                    displayPoem();
                }
            }
        });

        rootsPanel.add(rootsScrollPane, BorderLayout.NORTH);
        rootsPanel.add(versesScrollPane, BorderLayout.CENTER);

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(rootsPanel, BorderLayout.WEST);
        panel.add(poemScrollPane, BorderLayout.CENTER);

        add(panel);
        setVisible(true);

        // Populate roots list
        List<String> roots = bllobj.getAllRoots();
        rootsListModel.clear();
        roots.forEach(rootsListModel::addElement);
    }

    public void displayVerses() throws SQLException {
        String selectedRoot = rootsList.getSelectedValue();

        if (selectedRoot != null) {
            List<VerseDTO> verses = bllobj.getVerseFromRoot(selectedRoot);
            versesListModel.clear();

            verses.forEach(verse -> {
                int poemID = verse.getPoemID();
                poemIdList.add(poemID);

                versesListModel.addElement(verse.getVerse1() + "        " + verse.getVerse2());
                poemIdList.add(verse.getPoemID());
            });

        }
    }

    public void displayPoem() {
        int selectedPoemID = poemIdList.get(versesList.getSelectedIndex());

        if (selectedPoemID != -1) {
            PoemDTO poem = bllobj.getPoemByID(selectedPoemID);

            if (poem != null) {
                List<VerseDTO> verses = bllobj.getVersesByPoemID(poem.getPoemID());

                // Set the poem title in the poemTextArea
                poemTextArea.setText("Poem Title:\n" + poem.getPoemTitle() + "\n\n");

                // Append each verse to the poemTextArea
                poemTextArea.append("Verses:\n");
                for (VerseDTO verse : verses) {
                    poemTextArea.append(verse.getVerse1() + "\n" + verse.getVerse2() + "\n\n");
                }
            } else {
                System.out.println("Poem not found in the database");
                poemTextArea.setText("Poem not found in the database");
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
    }

}
