package presentation.Token;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.List;
import javax.swing.*;
import businesslogic.BusinessLogicInterface;

public class TokenForm extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    private JTextField rootInput, updateOldInput, updateNewInput, deleteInput;
    private JButton saveButton, updateButton, deleteButton, refreshButton;
    private JList<String> rootsList;
    private BusinessLogicInterface rootService;
    private DefaultListModel<String> listModel;

    public TokenForm(BusinessLogicInterface rootService) {
        this.rootService = rootService;

        setTitle("Root Management");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(520, 500);
        setLocationRelativeTo(null); // Center the frame

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Input Fields Section
        JPanel inputPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Root Operations"));

        rootInput = new JTextField(20);
        updateOldInput = new JTextField(20);
        updateNewInput = new JTextField(20);
        deleteInput = new JTextField(20);

        inputPanel.add(new JLabel("Enter Root:"));
        inputPanel.add(rootInput);
        inputPanel.add(new JLabel("Old Root:"));
        inputPanel.add(updateOldInput);
        inputPanel.add(new JLabel("New Root:"));
        inputPanel.add(updateNewInput);
        inputPanel.add(new JLabel("Delete Root:"));
        inputPanel.add(deleteInput);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(inputPanel, gbc);

        // Buttons Section
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        saveButton = createButton("Save Root");
        updateButton = createButton("Update Root");
        deleteButton = createButton("Delete Root");
        refreshButton = createButton("Refresh List");

        buttonPanel.add(saveButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);

        gbc.gridy = 1;
        panel.add(buttonPanel, gbc);

        // List Display Section
        listModel = new DefaultListModel<>();
        rootsList = new JList<>(listModel);
        rootsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(rootsList);
        scrollPane.setPreferredSize(new Dimension(400, 200));
        scrollPane.setBorder(BorderFactory.createTitledBorder("List of Roots"));

        gbc.gridy = 2;
        gbc.gridwidth = 1;
        panel.add(scrollPane, gbc);

        add(panel);
        setVisible(true);
        try {
			refreshRootsList();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // Initial loading of roots

        // Adding listeners
        saveButton.addActionListener(this);
        updateButton.addActionListener(this);
        deleteButton.addActionListener(this);
        refreshButton.addActionListener(this);
    }

    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        return btn;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == saveButton) {
                rootService.addRoot(rootInput.getText());
                rootInput.setText("");
            } else if (e.getSource() == updateButton) {
                rootService.updateRoot(updateOldInput.getText(), updateNewInput.getText());
                updateOldInput.setText("");
                updateNewInput.setText("");
            } else if (e.getSource() == deleteButton) {
                rootService.deleteRoot(deleteInput.getText());
                deleteInput.setText("");
            } else if (e.getSource() == refreshButton) {
                refreshRootsList();
            }
            showMessageDialog("Operation successful!");
        } catch (SQLException ex) {
            ex.printStackTrace();
            showMessageDialog("Error in operation: " + ex.getMessage());
        }
    }

    private void showMessageDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "Message", JOptionPane.INFORMATION_MESSAGE);
    }
    // Method to refresh the roots list
    private void refreshRootsList() throws SQLException {
        List<String> roots = rootService.getAllRoots();
		listModel.clear();
		roots.forEach(listModel::addElement);
    }

    // Main method - Replace with your database connection code
//    public static void main(String[] args) throws SQLException {
//        Connection connection = null; // Replace this with actual connection code
//        SwingUtilities.invokeLater(() -> {
//			new TokenForm(connection);
//		});
//    }
}
