package Interface;

import java.awt.EventQueue;
import java.awt.Image;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.SwingConstants;

import org.omg.CORBA.PUBLIC_MEMBER;

import com.google.api.services.gmail.model.Message;
import com.google.api.services.gmail.model.Thread;

import GmailAPI.GmailAPI.Email;

public class CopoEmailAWT extends JFrame 
{

	private static final long serialVersionUID = 1L;
	private static boolean[][] propriedades = new boolean[50][4];
	public static ImageIcon redimensionarIcon(int xComponete, int yComponete, String endereco)
	{
		ImageIcon icon = new ImageIcon(endereco);
		Image image = icon.getImage();
		Image newImage = image.getScaledInstance(xComponete, yComponete,  java.awt.Image.SCALE_SMOOTH);
		icon = new ImageIcon(newImage);
		return icon;
	}
	
	
	
	public JPanel gerarCorpo(final Email email,int locationY )
	{
		//propriedades[index][0]=email.getiisSelected;
//		propriedades[email.getIndex()][1]=email.getIsFavorite();
//		propriedades[email.getIndex()][2]=email.getIsImportant();
//		propriedades[email.getIndex()][3]=email.getIsUnread();
		
		
		final JButton btnSelecionar,
		              btnFavoritar,
		              btnImportante,
		              btnMarcarComoNaoLida = new JButton(),
		              btnExcluir = new JButton(),
		              btnArquivar = new JButton();
		
		final JLabel lblFrom = new JLabel(email.getFrom()),
		             lblSubject = new JLabel(email.getSubject()+"  -  "+email.getSnippet()),
		             lblDate = new JLabel("03/Jan/2021");
		Font font =new Font("Yu Gothic UI Semibold", Font.PLAIN, 15);
		
		
		
		final JPanel panelCorpoDoEmail = new JPanel();
		panelCorpoDoEmail.setBackground(Color.LIGHT_GRAY);
		if (email.getIsUnread())
		{
			panelCorpoDoEmail.setBackground(new Color(230,230,230));
		}
		else
		{
			panelCorpoDoEmail.setBackground(new Color(194,196,196));
		}
		panelCorpoDoEmail.setSize(1036,40);
		panelCorpoDoEmail.setLocation(10,locationY);
		panelCorpoDoEmail.setLayout(null);
		panelCorpoDoEmail.addMouseListener(new MouseAdapter(){
			public void mouseEntered(MouseEvent e)
			{
				btnMarcarComoNaoLida.setVisible(true);
				btnExcluir.setVisible(true);
				btnArquivar.setVisible(true);
				lblDate.setVisible(false);
			}
			public void mouseExited(MouseEvent e)
			{
				btnMarcarComoNaoLida.setVisible(false);
				btnExcluir.setVisible(false);
				btnArquivar.setVisible(false);
				lblDate.setVisible(true);
			}
			public void mouseClicked(MouseEvent e)
			{
				if (email.removeLabelIds("UNREAD"))
				{
					panelCorpoDoEmail.setBackground(new Color(194,196,196));
					//email.getIsUnread() = false;
					btnMarcarComoNaoLida.setIcon(redimensionarIcon(btnMarcarComoNaoLida.getWidth(),btnMarcarComoNaoLida.getHeight(),"Imagens/marcarComoNaoLida.png"));
				}
				final JFrame frame = new JFrame();
				frame.getContentPane().add(email.showEmail());
				frame.setMinimumSize(new Dimension(640, 480));
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				frame.setVisible(true);
				
			}
		});
		
		btnSelecionar = new JButton("");
		btnSelecionar.setLocation(10, 12);
		btnSelecionar.setSize(20, 20);
		btnSelecionar.setFocusable(false);
		btnSelecionar.setBackground(new Color(0,0,0,0));
		panelCorpoDoEmail.add(btnSelecionar);
		btnSelecionar.setBorder(null);
		if (propriedades[email.getIndex()][0]==true)
		{
			btnSelecionar.setIcon(redimensionarIcon(btnSelecionar.getWidth(),btnSelecionar.getHeight(),"Imagens/emailSelecionado.png"));
		}
		else
		{
			btnSelecionar.setIcon(redimensionarIcon(btnSelecionar.getWidth(),btnSelecionar.getHeight(),"Imagens/emailNaoselecionado.png"));
		}
		btnSelecionar.addMouseListener(new MouseAdapter(){
			public void mouseEntered(MouseEvent e)
			{
				btnMarcarComoNaoLida.setVisible(true);
				btnExcluir.setVisible(true);
				btnArquivar.setVisible(true);
				lblDate.setVisible(false);
			}
			public void mouseExited(MouseEvent e)
			{
				btnMarcarComoNaoLida.setVisible(false);
				btnExcluir.setVisible(false);
				btnArquivar.setVisible(false);
				lblDate.setVisible(true);
			}
			public void mouseClicked(MouseEvent e)
			{
				if (propriedades[email.getIndex()][0]==true)
				{
					btnSelecionar.setIcon(redimensionarIcon(btnSelecionar.getWidth(),btnSelecionar.getHeight(),"Imagens/emailNaoselecionado.png"));
					propriedades[email.getIndex()][0] = false;
				}
				else
				{
					btnSelecionar.setIcon(redimensionarIcon(btnSelecionar.getWidth(),btnSelecionar.getHeight(),"Imagens/emailSelecionado.png"));
					propriedades[email.getIndex()][0] = true;
				}
			}
		});

		btnFavoritar = new JButton("");
		btnFavoritar.setBorder(null);
		btnFavoritar.setSize(25,25);
		btnFavoritar.setLocation(40,7);
		btnFavoritar.setFocusable(false);
		btnFavoritar.setBackground(new Color(0,0,0,0));
		panelCorpoDoEmail.add(btnFavoritar);
		btnFavoritar.setBorder(null);
		if (email.getIsFavorite())
		{
			btnFavoritar.setIcon(redimensionarIcon(btnFavoritar.getWidth(),btnFavoritar.getHeight(),"Imagens/favoritoSelecionado.png"));
		}
		else
		{
			btnFavoritar.setIcon(redimensionarIcon(btnFavoritar.getWidth(),btnFavoritar.getHeight(),"Imagens/favoritoNaoSelecionado.png"));
		}
		btnFavoritar.addMouseListener(new MouseAdapter(){
			public void mouseEntered(MouseEvent e)
			{
				btnMarcarComoNaoLida.setVisible(true);
				btnExcluir.setVisible(true);
				btnArquivar.setVisible(true);
				lblDate.setVisible(false);
				
			}
			public void mouseExited(MouseEvent e)
			{
				btnMarcarComoNaoLida.setVisible(false);
				btnExcluir.setVisible(false);
				btnArquivar.setVisible(false);
				lblDate.setVisible(true);
			}
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (email.getIsFavorite())
				{
					if (email.removeLabelIds("STARRED"))
					{
						email.setIsFavorite(false);
						btnFavoritar.setIcon(redimensionarIcon(btnFavoritar.getWidth(),btnFavoritar.getHeight(),"Imagens/favoritoNaoSelecionado.png"));
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Erro ao tentar Remover labelId STARRED mouseClicked do btnFavoritar");
					}
				}
				else
				{
					if (email.addLabelIds("STARRED"))
					{
						email.setIsFavorite(true);
						btnFavoritar.setIcon(redimensionarIcon(btnFavoritar.getWidth(),btnFavoritar.getHeight(),"Imagens/favoritoSelecionado.png"));
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Erro ao tentar adicionar labelId STARRED mouseClicked do btnFavoritar");
					}
				}
			}
		});

		btnImportante = new JButton("");
		btnImportante.setBorder(null);
		btnImportante.setLocation(75,9);
		btnImportante.setSize(25,25);
		btnImportante.setBackground(new Color(0,0,0,0));
		btnImportante.setFocusable(false);
		panelCorpoDoEmail.add(btnImportante);
		if (email.getIsImportant())
		{
			btnImportante.setIcon(redimensionarIcon(btnImportante.getWidth(),btnImportante.getHeight(),"Imagens/importanteSelecionado.png"));
		}
		else
		{
			btnImportante.setIcon(redimensionarIcon(btnImportante.getWidth(),btnImportante.getHeight(),"Imagens/importanteNaoSelecionado.png"));
		}
		btnImportante.addMouseListener(new MouseAdapter(){
			public void mouseEntered(MouseEvent e)
			{
				btnMarcarComoNaoLida.setVisible(true);
				btnExcluir.setVisible(true);
				btnArquivar.setVisible(true);
				lblDate.setVisible(false);
			}
			public void mouseExited(MouseEvent e)
			{
				btnMarcarComoNaoLida.setVisible(false);
				btnExcluir.setVisible(false);
				btnArquivar.setVisible(false);
				lblDate.setVisible(true);
			}
			public void mouseClicked(MouseEvent e)
			{
				if (email.getIsImportant())
				{
					if (email.removeLabelIds("IMPORTANT"))
					{
						btnImportante.setIcon(redimensionarIcon(btnImportante.getWidth(),btnImportante.getHeight(),"Imagens/importanteNaoSelecionado.png"));
						email.setIsImportant(false);
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Erro ao tentar Remover labelId IMPORTANT mouseClicked do btnImportante");
					}

				}
				else
				{
					if (email.addLabelIds("IMPORTANT"))
					{
						btnImportante.setIcon(redimensionarIcon(btnImportante.getWidth(),btnImportante.getHeight(),"Imagens/importanteSelecionado.png"));
						email.setIsImportant(true);
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Erro ao tentar Adicionar labelId IMPORTANT mouseClicked do btnImportante");
					}

				}
				
			}
		});
		
		lblFrom.setSize(168,25);
		lblFrom.setLocation(110,7);
		lblFrom.setFont(font);
		lblFrom.setHorizontalAlignment(SwingConstants.CENTER);
		panelCorpoDoEmail.add(lblFrom);
		
		lblSubject.setSize(650,25);
		lblSubject.setLocation(280,7);
		lblSubject.setFont(font);
		panelCorpoDoEmail.add(lblSubject);
		
		lblDate.setSize(81,22);
		lblDate.setLocation(949,10);
		lblDate.setText(email.getDate());
		lblDate.setHorizontalAlignment(SwingConstants.CENTER);
		panelCorpoDoEmail.add(lblDate);
		
		btnMarcarComoNaoLida.setBorder(null);
		btnMarcarComoNaoLida.setLocation(1000,5);
		btnMarcarComoNaoLida.setSize(30,30);
		btnMarcarComoNaoLida.setBackground(new Color(0,0,0,0));
		btnMarcarComoNaoLida.setVisible(false);
		btnMarcarComoNaoLida.setFocusable(false);
		panelCorpoDoEmail.add(btnMarcarComoNaoLida);
		if (email.getIsUnread())
		{
			btnMarcarComoNaoLida.setIcon(redimensionarIcon(btnMarcarComoNaoLida.getWidth(),btnMarcarComoNaoLida.getHeight(),"Imagens/marcarComoLida.png"));
		}
		else
		{
			btnMarcarComoNaoLida.setIcon(redimensionarIcon(btnMarcarComoNaoLida.getWidth(),btnMarcarComoNaoLida.getHeight(),"Imagens/marcarComoNaoLida.png"));
		}
		btnMarcarComoNaoLida.addMouseListener(new MouseAdapter(){
			public void mouseEntered(MouseEvent e)
			{
				btnMarcarComoNaoLida.setVisible(true);
				btnExcluir.setVisible(true);
				btnArquivar.setVisible(true);
				lblDate.setVisible(false);
			}
			public void mouseExited(MouseEvent e)
			{
				btnMarcarComoNaoLida.setVisible(false);
				btnExcluir.setVisible(false);
				btnArquivar.setVisible(false);
				lblDate.setVisible(true);
			}
			public void mouseClicked(MouseEvent e)
			{
				if (email.getIsUnread())
				{
					
					if (email.removeLabelIds("UNREAD"))
					{
						email.setIsUnread(false);
						btnMarcarComoNaoLida.setIcon(redimensionarIcon(btnMarcarComoNaoLida.getWidth(),btnMarcarComoNaoLida.getHeight(),"Imagens/marcarComoNaoLida.png"));
						panelCorpoDoEmail.setBackground(new Color(194,196,196));
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Erro ao tentar Remover labelid UNREAD mouseClicked do btnMarcarComoNaoLida");
					}
				}
				else
				{
					if (email.addLabelIds("UNREAD"))
					{
						email.setIsUnread(true);
						btnMarcarComoNaoLida.setIcon(redimensionarIcon(btnMarcarComoNaoLida.getWidth(),btnMarcarComoNaoLida.getHeight(),"Imagens/marcarComoLida.png"));
						panelCorpoDoEmail.setBackground(new Color(230,230,230));
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Erro ao tentar adicionar labelid UNREAD mouseClicked do btnMarcarComoNaoLida");
					}
				}
			}
		});
		
		btnExcluir.setBorder(null);
		btnExcluir.setLocation(960,5);
		btnExcluir.setSize(30,30);
		btnExcluir.setVisible(false);
		btnExcluir.setFocusable(false);
		btnExcluir.setIcon(redimensionarIcon(btnExcluir.getWidth(),btnExcluir.getHeight(),"Imagens/Excluir.png"));
		panelCorpoDoEmail.add(btnExcluir);
		btnExcluir.setBackground(new Color(0,0,0,0));
		btnExcluir.addMouseListener(new MouseAdapter(){
			public void mouseEntered(MouseEvent e)
			{
				btnMarcarComoNaoLida.setVisible(true);
				btnExcluir.setVisible(true);
				btnArquivar.setVisible(true);
				lblDate.setVisible(false);
			}
			public void mouseExited(MouseEvent e)
			{
				btnMarcarComoNaoLida.setVisible(false);
				btnExcluir.setVisible(false);
				btnArquivar.setVisible(false);
				lblDate.setVisible(true);
			}
			public void mouseClicked(MouseEvent e)
			{
				email.deleteEmail();
			}
		});
		
//		btnArquivar.setBorder(null);
//		btnArquivar.setLocation(920,5);
//		btnArquivar.setSize(30,30);
//		btnArquivar.setVisible(false);
//		panelCorpoDoEmail.add(btnArquivar);
//		btnArquivar.setBackground(Color.BLUE);
//		btnArquivar.addMouseListener(new MouseAdapter(){
//			public void mouseEntered(MouseEvent e)
//			{
//				btnMarcarComoNaoLida.setVisible(true);
//				btnExcluir.setVisible(true);
//				btnArquivar.setVisible(true);
//			}
//			public void mouseExited(MouseEvent e)
//			{
//				btnMarcarComoNaoLida.setVisible(false);
//				btnExcluir.setVisible(false);
//				btnArquivar.setVisible(false);
//			}
//			public void mouseClicked(MouseEvent e)
//			{
//				
//			}
//		});
		
		
		
		
		return panelCorpoDoEmail;
	}
	
	



/////////////////===========================================================================================================================================================================

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CopoEmailAWT frame = new CopoEmailAWT();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	
	public CopoEmailAWT()
	{
		this.setSize(1200,700);
		this.setLocation(0,0);
		this.setDefaultCloseOperation(CopoEmailAWT.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setSize(1050,400);
		panel.setLocation(10, 10);
		panel.setLayout(null);
		getContentPane().add(panel);
		String from = "talles";
		String Subject ="O aprendizado no Ano Novo é por nossa conta";
		String snippet= "Compre um curso da promoção e garanta acesso antecipado ao nosso curso de aprendizado gratuito. Encontre novas maneiras de definir suas metas e aproveite seu novo curso ao máximo. Meus Cursos";
		String date ="Sun, 03 Jan 2021 18:46:59 +0000 (UTC)";

		
		//panel.add(gerarCorpo(0,"1010293",10,10, false, false, false, false, from,Subject,snippet,date));
		
		
	}
	
}
