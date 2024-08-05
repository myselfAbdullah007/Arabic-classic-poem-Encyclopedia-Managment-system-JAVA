package presentation.Verse;

import javax.swing.*;
import businesslogic.BusinessLogicInterface;
import dtos.PoemDTO;
import presentation.Poem.PoemForm;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ManualVerseInsertion {

    private JFrame frame;
    private JComboBox<String> poemDropdown;
    private JButton addButton;
    private JButton submitButton;
    private JPanel versesPanel;
    private BusinessLogicInterface bLL;
    private ArrayList<JTextArea> verseTextAreas;
    private List<PoemDTO> poems;
    PoemForm parentForm;

    public ManualVerseInsertion(BusinessLogicInterface bLL2) {
        this.bLL = bLL2;
        verseTextAreas = new ArrayList<>();
        parentForm=new PoemForm(bLL2);
    }

    public void showGUI() throws SQLException {
        frame = new JFrame("Manual Verse Insertion");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Fetch available poems
        poems = bLL.getAllPoems();

        // Create an array of strings from the poems' titles
        String[] poemTitles = poems.stream()
                .map(PoemDTO::getPoemTitle)
                .toArray(String[]::new);
        poemDropdown = new JComboBox<>(poemTitles);

        versesPanel = new JPanel();
        versesPanel.setLayout(new BoxLayout(versesPanel, BoxLayout.Y_AXIS));

        JTextArea firstVerseArea = new JTextArea(3, 20);
        verseTextAreas.add(firstVerseArea);
        versesPanel.add(firstVerseArea);

        addButton = new JButton("Add Verse");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create two new JTextArea instances
                JTextArea newVerseArea1 = new JTextArea(3, 20);
                JTextArea newVerseArea2 = new JTextArea(3, 20);

                // Add the new JTextArea instances to the verseTextAreas list
                verseTextAreas.add(newVerseArea1);
                verseTextAreas.add(newVerseArea2);

                // Add the new JTextArea instances to the versesPanel
                versesPanel.add(newVerseArea1);
                versesPanel.add(newVerseArea2);

                // Pack the frame to adjust its size
                frame.pack();
            }
        });

        submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Fetch the selected poem title from the dropdown
                String selectedTitle = (String) poemDropdown.getSelectedItem();

                // Search for the corresponding PoemDTO from the poems list
                PoemDTO selectedPoem = poems.stream()
                        .filter(p -> p.getPoemTitle().equals(selectedTitle))
                        .findFirst()
                        .orElse(null);

                // Handle the error case if no PoemDTO matches
                if (selectedPoem == null) {
                    JOptionPane.showMessageDialog(frame, "Error: Poem not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Iterate over verseTextAreas and create verses
                for (int i = 0; i < verseTextAreas.size() - 1; i += 2) {
                    try {
                        // Get the values of verse1 and verse2 from the JTextArea instances
                        String verse1Value = verseTextAreas.get(i).getText();
                        String verse2Value = verseTextAreas.get(i + 1).getText();

                        // Change the line below to include verse1 and verse2 as parameters
                        int verseID = bLL.createVerse(selectedPoem.getPoemID(), verse1Value, verse2Value);

                        // Assign tokens, roots, and POS to the verses
                        bLL.assignTokensRootsPOS(verse1Value, verse2Value, selectedPoem.getPoemID(),verseID);
                        parentForm.refreshVersesList();


                    } catch (Exception e1) {
                        e1.printStackTrace();
                        JOptionPane.showMessageDialog(frame, "Error inserting verse: " + e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        panel.add(poemDropdown, BorderLayout.NORTH);
        panel.add(versesPanel, BorderLayout.CENTER);
        panel.add(addButton, BorderLayout.WEST);
        panel.add(submitButton, BorderLayout.SOUTH);
        frame.add(panel);
        frame.setVisible(true);
    }
}
