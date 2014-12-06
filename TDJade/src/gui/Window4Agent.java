package gui;

import jade.core.Agent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;



/** a simple window with a text area to display informations*/
@SuppressWarnings("serial")
public class Window4Agent extends JFrame {
	/** Text area */
	JTextArea jTextArea;
	/**monAgent linked to this frame */
	Agent myAgent;

	/** window color*/
	Color color;


	public Window4Agent() {
		setBounds(10, 10, 300, 400);
		buildGui();
		setVisible(true);
	}

	public Window4Agent(Agent _agt) {
		this();
		myAgent = _agt;
	}

	public Window4Agent(String _titre) {
		this();
		setTitle(_titre);
	}

	public Window4Agent(String _titre, Agent _agt) {
		this(_titre);
		myAgent = _agt;
	}


	/** build the gui : a text area in the center of the window, with scroll bars*/
	private void buildGui()
	{
		getContentPane().setLayout(new BorderLayout());
		jTextArea = new JTextArea();
		jTextArea.setRows(5);
		JScrollPane jScrollPane  = new JScrollPane(jTextArea);        
		getContentPane().add(BorderLayout.CENTER, jScrollPane);
		jTextArea.setFont(new Font("Courier", Font.PLAIN, 12));
	}


	/** add a string to the text area */
	public void println(String chaine) {
		String texte = jTextArea.getText();
		texte = texte +  chaine + "\n";
		jTextArea.setText(texte);
		jTextArea.setCaretPosition(texte.length());
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
		jTextArea.setBackground(color);
	}



}
