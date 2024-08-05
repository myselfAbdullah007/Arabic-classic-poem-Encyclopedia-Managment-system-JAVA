
package MainPackage;

import java.awt.BorderLayout;
import com.formdev.flatlaf.FlatDarkLaf; // for dark theme
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.spi.LoggerAdapter;

import businesslogic.BusinessLogicInterface;
import businesslogic.FacadeBusinessLogic;
import dataacess.DatabaseConnector;
import dataacess.FacadeDal;
import dataacess.FacadeDalInterface;
import dtos.BookDTO;
import dtos.PoemDTO;
import dtos.VerseDTO;
import presentation.Book.BookForm;
import presentation.Poem.PoemForm;
import presentation.Token.RootsMainForm;
public class MainClass {
    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(LoggerAdapter.class.getName());
	// Business Logic Interface for handling business logic operations.
	static BusinessLogicInterface bll;

	public static void main(String[] args) throws UnsupportedLookAndFeelException {
//		 System.out.print("Hello");
//
//	        // Use the library class
//	       System.out.println(AlKhalil2Analyzer.getInstance().processToken("بلسان").getAllResults());
//	        System.out.println(net.oujda_nlp_team.AlKhalil2Analyzer.getInstance().processToken("بلسان").getAllRootString());
//	       
//	       

		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
       
		//Data Access layer Object
		FacadeDalInterface dal = new FacadeDal();
		//Creating Business layer Object using Data Access layer Object
		bll = new FacadeBusinessLogic(dal);
        UIManager.setLookAndFeel(new FlatDarkLaf()); // or FlatLightLaf() for light theme
        UIManager.put("Button.hoverBackground", new Color(100, 100, 200));
        UIManager.put( "Button.arc", 800 );
        UIManager.put("Button.pressedBackground", new Color(138,43,226));
        UIManager.put("Button.default.boldText", true);
		// Create a GUI frame and make it visible
		JFrame frame = new JFrame("Arabic Poem");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 400);

		JPanel panel = new JPanel();
		frame.add(panel);
		panel.setLayout(new GridLayout(5, 1));
		frame.setLocationRelativeTo(null);
		JLabel titleLabel = new JLabel("موسوعة الشعر العربية في العصر الجاهلية");
		titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
		//titleLabel.setForeground(Color.DARK_GRAY);
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		frame.add(titleLabel, BorderLayout.NORTH);

		// Customize the panel
		panel.setBorder(new EmptyBorder(new Insets(15, 15, 15, 15)));
		panel.setBackground(Color.lightGray); // Set a background color
		JButton bookButton = new JButton("Book Management");
		JButton poemButton = new JButton("Poem Management");
		JButton rootButton = new JButton("Root Management");
		JButton viewAllBooksButton = new JButton("View All Books");
		panel.add(bookButton);
		panel.add(poemButton);
		panel.add(rootButton);

		panel.add(viewAllBooksButton);

		bookButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				BookForm bookForm = new BookForm(bll);
				bookForm.showForm();
			}
		});

		poemButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PoemForm poemForm = new PoemForm(bll);
				try {
					poemForm.showGUI();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});

		rootButton.addActionListener(new ActionListener() {
			@SuppressWarnings("unused")
			@Override
			public void actionPerformed(ActionEvent e) {
				//TokenForm rootForm = new TokenForm(bll);
				RootsMainForm rootsMainForm  = new RootsMainForm(bll); 
				
			}
		});

		viewAllBooksButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showAllBooks();
			}
		});

		frame.setVisible(true);
        // Add a window listener to close the database connection when the window is closing
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				DatabaseConnector dbConnector= DatabaseConnector.getInstance();
				dbConnector.closeConnection();
				System.out.println("Database connection closed.");
				 logger.info( "Database connection closed.");
			}
		});

	}
	private static void showAllBooks() {
	    logger.info("Showing all books");

	    JFrame bookWindow = new JFrame("All Books");
	    bookWindow.setSize(800, 400);
	    bookWindow.setLayout(new BorderLayout());
	    bookWindow.setLocationRelativeTo(null);

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
	    bookTable.setShowGrid(true);
	    bookTable.setGridColor(Color.RED);
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
	        poemTable.setShowGrid(true);
	        poemTable.setGridColor(Color.BLACK);
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
	    logger.info("Displaying verses for poem: " + poem.getPoemTitle());
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
	        data[i][0] = verse.getVerse1() + "\n" + verse.getVerse2();
	    }

	    // Create the table with the data and column names
	    JTable verseTable = new JTable(data, columnNames);
	    verseTable.setShowGrid(true);
	    verseTable.setGridColor(Color.BLACK);
	    verseTable.setFont(new Font("Arial", Font.BOLD, 25));
	 //   verseTable.setBackground(Color.LIGHT_GRAY);

	    // Set up table cell renderer for center alignment
	    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	    centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
	    verseTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);

	    // Set row height to accommodate larger font size
	    verseTable.setRowHeight(60); // Adjust the height as needed

	    verseWindow.add(new JScrollPane(verseTable), BorderLayout.CENTER);

	    verseWindow.setVisible(true);
	}

}
