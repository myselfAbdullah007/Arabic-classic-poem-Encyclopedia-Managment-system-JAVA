package presentation.Token;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import businesslogic.BusinessLogicInterface;

public class RootsMainForm extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;


	public RootsMainForm(BusinessLogicInterface bll) {


		JFrame frame = new JFrame("Arabic Poem");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(800, 400);

		JPanel panel = new JPanel();
		frame.add(panel);
		panel.setLayout(new GridLayout(5, 1));
		frame.setLocationRelativeTo(null);
		JLabel titleLabel = new JLabel("موسوعة الشعر العربية في العصر الجاهلية");
		titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		frame.add(titleLabel, BorderLayout.NORTH);

		// Customize the panel
		panel.setBorder(new EmptyBorder(new Insets(15, 15, 15, 15)));
		JButton displayButton = new JButton("Display All Roots");
		JButton manualButton = new JButton("Manually Assign Roots");
		JButton tokenButton = new JButton("Token Info");

		customizeButton(displayButton);
		panel.add(displayButton);
		customizeButton(manualButton);
		panel.add(manualButton);
		customizeButton(tokenButton );
		panel.add(tokenButton );

		frame.setVisible(true);



		displayButton.addActionListener(new ActionListener() {
			@SuppressWarnings("unused")
			@Override
			public void actionPerformed(ActionEvent e) {
				DisplayRootsForm displayroots = new DisplayRootsForm(bll);
				
			}
		});
		
		manualButton.addActionListener(new ActionListener() {
			@SuppressWarnings("unused")
			@Override
			public void actionPerformed(ActionEvent e) {
				ManualAssignForm manualAssignRoot = new ManualAssignForm(bll);
				
			}
		});
		
		tokenButton.addActionListener(new ActionListener() {
			@SuppressWarnings("unused")
			@Override
			public void actionPerformed(ActionEvent e) {
				TokenInfoForm tokenInfoForm = new TokenInfoForm(bll);
				
			}
		});
	}

	private static void customizeButton(JButton button) {
		button.setFont(new Font("Arial", Font.BOLD, 14));
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
