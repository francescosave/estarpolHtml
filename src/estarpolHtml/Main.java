package estarpolHtml;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTabbedPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

public class Main extends JFrame {

	private JPanel contentPane;
	private JButton btnNewButton;
	private JScrollPane scrollPane;
	private JTextPane textPane;
	private JButton btnNewButton_1;
	private JTextPane textPane_1;
	private JTextField urlText;
	private JTextField tFieldNumber;
	private String urlTemplate = "http://albo.comune.bari.it/AlboPretorio/BA_PubblicIntranet.nsf/KeyPubblica/%type%%year%%number%";
	private String urlTemp = "";

	class Atto
	{
	    public  String Tipo; 
	    public  String Anno;  
	    public  String Numero; 
	 };
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public String LeggiUrl(String file){
		
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(file));
			String line = reader.readLine();
			if(line!=null) return line;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
		
		
		
		
	}
	
	public void EstrapolateLink() {
		// TODO Auto-generated method stub
		String pagina= ExtrapolateHTML();
		textPane.setText(pagina);
		
		String stringHTML = "href";
		int pointer = pagina.indexOf(stringHTML, 0);
		int occorrenze =0;
		
		while (pointer > -1){
			//textPane_1.setText(textPane_1.getText() + pointer +"\n");
			int iniziale = pagina.indexOf("\"", pointer);
			int finale = pagina.indexOf("\"", iniziale+1);
				System.out.print(pagina.substring(pointer-1, finale+1) +"\n");
				
				
		        switch (pagina.substring(pointer-1, pointer+4)) {
		            case ".href": textPane_1.setText(textPane_1.getText() + "link non valido" +"\n");
		                     break;
		            case " href":
		            		  textPane_1.setText(textPane_1.getText() + pagina.substring(++iniziale, finale) +"\n");
		            		 break;
		            default:  textPane_1.setText(textPane_1.getText() + "invalid link" +"\n");
		                     break;
		        }
				
				
			
			pointer = pagina.indexOf(stringHTML, pointer+1);
								
			++occorrenze;
		}
		
		System.out.printf("Ci sono %d occorrenze!!\n",occorrenze);
	}
	
	public String ExtrapolateHTML(){
		
		BufferedReader br=null;
		
		// esempio link 
		// http://albo.comune.bari.it/AlboPretorio/BA_PubblicIntranet.nsf/KeyPubblica/DC201700001
		// http://albo.comune.bari.it/AlboPretorio/BA_PubblicIntranet.nsf/KeyPubblica/DG201700002
		// http://albo.comune.bari.it/AlboPretorio/BA_PubblicIntranet.nsf/KeyPubblica/DET201707300

		// Apro la connessione impostando alcuni parametri
		URL u;
		try {
			u = new URL(urlText.getText() );
			HttpURLConnection huc = (HttpURLConnection) u.openConnection();
			br = new BufferedReader(new InputStreamReader(huc.getInputStream()));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		// Leggo la pagina di riposta
		String pagina = "";
		String tmp;
		try {
			while ((tmp = br.readLine()) != null) pagina += tmp;
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		//JOptionPane.showMessageDialog(null,pagina);
		
		return pagina ;
		
		
	}
	
	public String aggiornaUrl(String objName){
		urlTemp=urlTemplate;
		 switch (objName) {
         case "cBoxType": JOptionPane.showMessageDialog(null,"aggiornaUrl("+objName+")");
         		urlTemp=urlTemp.replace("%type%","DET");
                  break;
         case "cBoxYear": JOptionPane.showMessageDialog(null,"aggiornaUrl("+objName+")");
               urlTemp=(urlTemp.replace("%year%","2015"));
         		  break;
         case "tFieldNumber": JOptionPane.showMessageDialog(null,"aggiornaUrl("+objName+")");
         		urlTemp=(urlTemp.replace("%number%","00015"));
    		  break;		  
         default:  
                  break;
     }
		 		urlText.setText(urlTemp); 
		 return "ss";
	}
	
	/**
	 * Create the frame.
	 */
	public Main() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1015, 494);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(23, 11, 966, 433);
		JPanel panel = new JPanel();
		tabbedPane.add("",panel);
		panel.setLayout(null);
		
		urlText = new JTextField();
		urlText.setBounds(10, 6, 758, 20);
		panel.add(urlText);
		urlText.setText(LeggiUrl("c:\\suggerimento.txt"));
		urlText.setColumns(10);
		
		btnNewButton = new JButton("Carica HTML");
		btnNewButton.setBounds(778, 6, 173, 33);
		panel.add(btnNewButton);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 70, 941, 111);
		panel.add(scrollPane);
		
		textPane = new JTextPane();
		scrollPane.setViewportView(textPane);
		
		btnNewButton_1 = new JButton("Rileva link");
		btnNewButton_1.setBounds(811, 361, 140, 33);
		panel.add(btnNewButton_1);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 192, 941, 158);
		panel.add(scrollPane_1);
		
		textPane_1 = new JTextPane();
		scrollPane_1.setViewportView(textPane_1);
		
		JComboBox cBoxType = new JComboBox();
		cBoxType.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				//JOptionPane.showMessageDialog(null,"Cambio valore\n"+ e.getStateChange());
				
				if (e.getStateChange()== ItemEvent.SELECTED)
					aggiornaUrl("cBoxType");//JOptionPane.showMessageDialog(null,"aggiornaUrl(cBoxType)");
				
			}
		});
		cBoxType.setModel(new DefaultComboBoxModel(new String[] {"[DET] Determina", "[DC]   Delibera Consiglio", "[DG]   Delibera Giunta"}));
		cBoxType.setBounds(10, 37, 226, 20);
		panel.add(cBoxType);
		
		JComboBox cBoxYear = new JComboBox();
		cBoxYear.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				JOptionPane.showMessageDialog(null,"Cambio valore\n"+ arg0.getStateChange());
				//ItemEvent
				if (arg0.getStateChange()== ItemEvent.SELECTED)
					aggiornaUrl("cBoxYear"); //JOptionPane.showMessageDialog(null,"aggiornaUrl(cBoxYear)");
			}
		});
		cBoxYear.setModel(new DefaultComboBoxModel(new String[] {"2018", "2017", "2016", "2015", "2014"}));
		cBoxYear.setBounds(246, 37, 74, 20);
		panel.add(cBoxYear);
		
		tFieldNumber = new JTextField();
		tFieldNumber.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				//JOptionPane.showMessageDialog(null,"aggiornaUrl(tFieldNumber)");
				aggiornaUrl("tFieldNumber");
			}
		});
		
		
		
		tFieldNumber.setBounds(330, 37, 86, 20);
		panel.add(tFieldNumber);
		tFieldNumber.setColumns(10);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EstrapolateLink();
			}

			
			
		});
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
				textPane.setText(ExtrapolateHTML());
			}
		});
		tabbedPane.add("",new JPanel());
		
		contentPane.add(tabbedPane);
		
		
		
		
		
		
		
	}
}
