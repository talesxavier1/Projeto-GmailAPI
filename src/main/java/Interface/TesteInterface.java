package Interface;
import java.io.IOException;
import java.security.GeneralSecurityException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;
import GmailAPI.GmailAPI.Email;
import GmailAPI.GmailAPI.Service;
import GmailAPI.GmailAPI.User;

public class TesteInterface extends JFrame
{
	private static final long serialVersionUID = 1L;
	//Service service_ = new Service();
	CopoEmailAWT awt = new CopoEmailAWT();
	
	Email[] emails = new Email[10];
	
	public TesteInterface() throws IOException, GeneralSecurityException
	{
		this.setSize(1000,1000);
		this.setLocation(0,0);
		this.setLayout(null);
		this.setDefaultCloseOperation(TesteInterface.EXIT_ON_CLOSE);
		
		JPanel jPanel = new JPanel();
		jPanel.setSize(1050,700);
		jPanel.setLocation(10,10);
		jPanel.setBackground(java.awt.Color.blue);
		jPanel.setLayout(null);
		this.add(jPanel);
		
		User user = new User();
		user.setAccessToken(null);
		user.setCredentialPath("credentials2.json");
		user.setName("me");
		user.setRefreshToken("1//0hLhiSM9a82E6CgYIARAAGBESNwF-L9IrGaTQbvqWf1QsuiO8J8eUxvDJEHSSkrl2jcZob32Y-_CZGEixlY22sZmi2zVjXeJNhk4");
		user.setScope(null);
		
		//////
		//gerar servico
		Service.generateService(user.getRefreshToken(), user.getCredentialPath());
		//pego servico gerado
		Gmail service = Service.getService();
		//////
		
		Gmail.Users.Messages.List request = service.users().messages().list("me").setQ("label:inbox");
		ListMessagesResponse listMessagesResponse = request.execute();
		

		int initH=10;
		for (int i = 0; i < 10; i++)
		{
			
			String mensagenId = listMessagesResponse.getMessages().get(i).getId();
			Message message = service.users().messages().get("me", mensagenId).execute();
			Email email = new Email(message,i);

			emails[i] = email;	
			jPanel.add(awt.gerarCorpo(emails[i],initH));
			initH=initH+40;
		}
		
		
	}
	
	public static void main(String args[]) throws IOException, GeneralSecurityException
	{
		new TesteInterface().setVisible(true);
	}
	
	
}
