package presentation.Token;

import java.awt.BorderLayout;
import java.awt.Font;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.spi.LoggerAdapter;

import businesslogic.BusinessLogicInterface;
import dtos.BookDTO;
import dtos.PoemDTO;
import dtos.RootDTO;
import dtos.VerseDTO;

public class ManualAssignForm {

	static BusinessLogicInterface bll;
    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(LoggerAdapter.class.getName());

	@SuppressWarnings("static-access")
	public ManualAssignForm(BusinessLogicInterface bll) {
		this.bll = bll;
		 logger.info("Showing all books");

		    JFrame bookWindow = new JFrame("All Books");
		    bookWindow.setSize(800, 400);
		    bookWindow.setLayout(new BorderLayout());
		    bookWindow.setLocationRelativeTo(null);
		    // Styling similar to the main screen

		    List<BookDTO> books = bll.getAllBooks();

		    // Define column names
		    String[] columnNames = {"Book Title"};

		    // Create a 2D array to hold the data for the table
		    Object[][] data = new Object[books.size()][columnNames.length];

		    // Populate the data array
		    for (int i = 0; i < books.size(); i++) {
		        data[i][0] = books.get(i).getBookTitle();
		    }

		    // Create the table with the data and column names
		    JTable bookTable = new JTable(data, columnNames);

		    // Set up table cell renderer for center alignment
		    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		    centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		    bookTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		    // Set row height to accommodate larger font size
		    bookTable.setRowHeight(40); // Adjust the height as needed
		    // Set font size for the entire table
		    bookTable.setFont(new Font("Arial", Font.BOLD, 20));
		    bookWindow.add(new JScrollPane(bookTable), BorderLayout.CENTER);

		    bookTable.getSelectionModel().addListSelectionListener(e -> {
		        if (!e.getValueIsAdjusting()) {
		            int selectedRow = bookTable.getSelectedRow();
		            if (selectedRow != -1) {
		                BookDTO selectedBook = books.get(selectedRow);
		                displayPoemsOfBook(selectedBook);
		            }
		        }
		    });

		    bookWindow.setVisible(true);
	}

	private static void displayPoemsOfBook(BookDTO book) {
	    logger.info("Displaying poems for book: " + book.getBookTitle());
	    JFrame poemWindow = new JFrame("Poems of " + book.getBookTitle());
	    poemWindow.setSize(800, 400);
	    poemWindow.setLocationRelativeTo(null);
	    poemWindow.setLayout(new BorderLayout());

	    try {
	        List<PoemDTO> poems = bll.getPoemsForBook(book.getBookID());

	        // Define column names for the poem table
	        String[] columnNames = {"Poem Title"};

	        // Create a 2D array to hold the data for the poem table
	        Object[][] data = new Object[poems.size()][columnNames.length];

	        // Populate the data array
	        for (int i = 0; i < poems.size(); i++) {
	            data[i][0] = poems.get(i).getPoemTitle();
	        }

	        // Create the table with the data and column names
	        JTable poemTable = new JTable(data, columnNames);
	        poemTable.setFont(new Font("Arial", Font.BOLD, 25));

	        // Set up table cell renderer for center alignment
	        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
	        poemTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);

	        // Set preferred width for the "Poem Title" column
	        poemTable.getColumnModel().getColumn(0).setPreferredWidth(400); // Adjust the width as needed

	        // Set row height to accommodate larger font size
	        poemTable.setRowHeight(40); // Adjust the height as needed

	        poemWindow.add(new JScrollPane(poemTable), BorderLayout.CENTER);

	        poemTable.getSelectionModel().addListSelectionListener(e -> {
	            if (!e.getValueIsAdjusting()) {
	                int selectedRow = poemTable.getSelectedRow();
	                if (selectedRow != -1) {
	                    PoemDTO selectedPoem = poems.get(selectedRow);
	                    displayVersesOfPoem(selectedPoem);
	                }
	            }
	        });
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    poemWindow.setVisible(true);
	}

	private static void displayVersesOfPoem(PoemDTO poem) {
	    JFrame verseWindow = new JFrame("Verses of " + poem.getPoemTitle());
	    verseWindow.setSize(800, 400);
	    verseWindow.setLocationRelativeTo(null);
	    verseWindow.setLayout(new BorderLayout());

	    List<VerseDTO> verses = bll.getVersesByPoemID(poem.getPoemID());

	    // Define column names for the verse table
	    String[] columnNames = {"Verse"};

	    // Create a 2D array to hold the data for the verse table
	    Object[][] data = new Object[verses.size()][columnNames.length];

	    // Populate the data array
	    for (int i = 0; i < verses.size(); i++) {
	        VerseDTO verse = verses.get(i);
	        data[i][0] = verse.getVerse1() + "      " + verse.getVerse2();
	    }

	    // Create the table with the data and column names
	    JTable verseTable = new JTable(data, columnNames);
	    DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
	    renderer.setHorizontalAlignment(SwingConstants.CENTER);
	    verseTable.getColumnModel().getColumn(0).setCellRenderer(renderer);

	    verseTable.getSelectionModel().addListSelectionListener(e -> {
	        if (!e.getValueIsAdjusting()) {
	            int selectedRow = verseTable.getSelectedRow();
	            if (selectedRow != -1) {
	                VerseDTO selectedVerse = verses.get(selectedRow);
	                try {
	                    displayRootOfVerse(selectedVerse);
	                } catch (SQLException e1) {
	                    e1.printStackTrace();
	                }
	            }
	        }
	    });

	    verseWindow.add(new JScrollPane(verseTable), BorderLayout.CENTER);

	    verseWindow.setVisible(true);
	}


	private static void displayRootOfVerse(VerseDTO verse) throws SQLException {
	    JFrame verseWindow = new JFrame("Roots");
	    verseWindow.setSize(800, 400);
	    verseWindow.setLocationRelativeTo(null);
	    verseWindow.setLayout(new BorderLayout());

	    System.out.println(verse.getVerseID());
	    List<RootDTO> roots = bll.getRootsFromVerses(verse.getVerseID());

	    // Define column names for the root table
	    String[] columnNames = {"Root Value"};

	    // Create a 2D array to hold the data for the root table
	    Object[][] data = new Object[roots.size()][columnNames.length];

	    // Populate the data array
	    for (int i = 0; i < roots.size(); i++) {
	        data[i][0] = roots.get(i).getValue();
	    }

	    // Create the table with the data and column names
	    JTable rootTable = new JTable(data, columnNames);
	    DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
	    renderer.setHorizontalAlignment(SwingConstants.CENTER);
	    rootTable.getColumnModel().getColumn(0).setCellRenderer(renderer);

	    JTextArea textArea = new JTextArea();
	    textArea.setEditable(false);

	    JPanel panel = new JPanel(new BorderLayout());
	    panel.add(new JScrollPane(rootTable), BorderLayout.CENTER);
	    panel.add(new JScrollPane(textArea), BorderLayout.SOUTH);

	    rootTable.getSelectionModel().addListSelectionListener(e -> {
	        if (!e.getValueIsAdjusting()) {
	            int selectedRow = rootTable.getSelectedRow();
	            if (selectedRow != -1) {
	                String selectedRootValue = (String) rootTable.getValueAt(selectedRow, 0);
	                System.out.println("Selected root: " + selectedRootValue);
	                try {
	                    Boolean content = ManualAssignment(selectedRootValue);
	                    if (content) {
	                        textArea.setText("Root Successfully Assigned to the Verse");
	                    }
	                } catch (SQLException e1) {
	                    e1.printStackTrace();
	                }
	            }
	        }
	    });

	    verseWindow.add(panel, BorderLayout.CENTER);
	    verseWindow.setVisible(true);
	}


	private static Boolean ManualAssignment(String value) throws SQLException {

		@SuppressWarnings("unused")
		Boolean flag = bll.setManualRoot(value);
		return true;
	}
}