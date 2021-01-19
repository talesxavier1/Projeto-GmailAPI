package GmailAPI.GmailAPI;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;
import com.google.api.client.util.Base64;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;
import com.google.api.services.gmail.model.ModifyMessageRequest;



public class GmailAPI
{
	static User user = new User();
	
	public static void main(String args[]) throws IOException, GeneralSecurityException
	{
		//Configuracao do obj User.
		user.setAccessToken(null);
		//(é necessario fazer as configuracoes da api e armazenar o arquivo credentials.json dentro da pasta credentiais)
		user.setCredentialPath("credentials2.json");
		user.setName("me");
		user.setRefreshToken("1//0hLhiSM9a82E6CgYIARAAGBESNwF-L9IrGaTQbvqWf1QsuiO8J8eUxvDJEHSSkrl2jcZob32Y-_CZGEixlY22sZmi2zVjXeJNhk4");
		user.setScope(null);
		
		//Gera o servico para as operacoes
		Service.generateService(user.getRefreshToken(), user.getCredentialPath());
		
		
		//lista de ids dos emails.
		//Quando parametro label null, retorna os emails do inbox.
		//Quando parametro page é null ou 1, retorna a primeira pagina.
		//listMessagesResponse é um JSON que contem um Array de ids dentro da chave messages.
		ListMessagesResponse listMessagesResponse = getMessageList(null,null); 
		
		//Mensagem
		//A mensagem é um JSON que contem todas as informacoes do email solicitado.
//		Message message = getMessage(listMessagesResponse.getMessages().get(0).getId());
//		System.out.println(message.toPrettyString());
		
		
		String to ="talesxavier1@gmail.com";
		String from = "teste@teste.com";
		String subject = "teste de mandar email 01";
		String bodyText = "corpo da mensagem";
		
		sendEmail(to, from, subject, bodyText);
	}
	
	/*
	 * Esse metodo manda um email com corpo text/plain ou text/html,ambos sem anexo.
	 * @parametro to destinatario do email.
	 * @parametro from Remetente do email. 
	 */
	public static void sendEmail(String to, String from, String subject, String bodyText)
	{
		try
		{
			Properties properties = new Properties();
			Session session = Session.getDefaultInstance(properties,null);
			MimeMessage mimeMessage = new MimeMessage(session);
			
			//montagem da mensagem
			mimeMessage.setFrom(new InternetAddress(from));
			mimeMessage.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(to));
			mimeMessage.setSubject(subject);
			mimeMessage.setText(bodyText);
			
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			mimeMessage.writeTo(buffer);
			byte[] bytes = buffer.toByteArray();
			String encodedEmail = Base64.encodeBase64URLSafeString(bytes);
			Message message = new Message();
			message.setRaw(encodedEmail);
			
			Gmail service = Service.getService();
			message = service.users().messages().send("me",message).execute();
			System.out.println(message.toPrettyString());
			
			
		}
		catch (Exception e)
		{
		
		}
		//return bodyText;
	}
	
	/*
	 * Essa funcao retorna uma lista de 50 ids de emails do que contem o label solicitado.
	 * @parametro labelId Id do label que vai restringir a busca.
	 * @pageNextToken token da pagina seguinte.
	 * @return Retorna um JSON com um array que contem 50 messagesIds dentro da chave message e uma chave pageNextToken que contem o token da proxima pagina.
	 * 
	 * O parametro labelId pode ser null, nesse caso o retorno sera de uma lista de ids de emails do imbox.
	 * O parametro pageNextToken pode ser null, nesse caso ele ira retornar a primeira pagina de ids de emails.
	 * 
	 */
	private static ListMessagesResponse getMessageList(String labelId,Integer pageNextToken)
	{
		try
		{
			String nextPageToken = null;
			ListMessagesResponse listMessagesResponse;
			int cont = -1;
			do
			{
				Gmail service = Service.getService();
				Gmail.Users.Messages.List request = service.users().messages().list("me").setQ(labelId).setMaxResults((long) 50).setPageToken(nextPageToken);
				listMessagesResponse = request.execute();
				nextPageToken = listMessagesResponse.getNextPageToken();
				cont++;
			}while(pageNextToken!=null && cont<pageNextToken);
			return listMessagesResponse;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	/*
	 * Esse metodo solicita um email baseado no messageId.
	 * @parametro messageId Id da mensagem solicitada.
	 * @return retorna o emailRaw que é um JSON com todas as informacoes do email.
	 */
	private static Message getMessage(String messageId)
	{
		try 
		{
			Gmail service = Service.getService();
			Message message = service.users().messages().get("me", messageId).execute();
			return message;
		} 
		catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	/*
	 * Essa funcao remove labels no email requisitado.
	 * Possiveis labels Ids https://bityli.com/oXeHU
	 * 
	 * @parametro labelId id do label que vai ser removido.
	 * @parametro messageId Id da mensagem que vai ser alterada.
	 * @return Retorna true quando o label é removido e false quando ocorre algum erro. 
	 */
	public boolean removeLabelIds(String labelId, String messageId)
	{
		try
		{
			Gmail service =  Service.getService();
			List<String>labelsToRemove = new ArrayList<String>();
			labelsToRemove.add(labelId);
			ModifyMessageRequest mods = new ModifyMessageRequest().setRemoveLabelIds(labelsToRemove);
			service.users().messages().modify("me",messageId, mods).execute();
			return true;
		} 
		catch (IOException e)
		{
			JOptionPane.showMessageDialog(null,"Erro na classe GmailAPI metodo removeLabelIds ERRO:" + e);
			return false;
		}
	}
	
	/*
	 * Essa funcao adiciona labelId no email requisitado.
	 * Possiveis labels Ids https://bityli.com/oXeHU
	 * 
	 * @parametro labelId Id do label que vai ser adicionado.
	 * @parametro messageId Id da mensagem que vai ser alterada.
	 * @return Retorna  true quando o Id é adicionado e false quando ocorre algum erro.
	 */
	public boolean addLabelIds(String labelId, String messageId)
	{
		try
		{
			Gmail service =  Service.getService();
			List<String>labelsToAdd = new ArrayList<String>();
			labelsToAdd.add(labelId);
			ModifyMessageRequest mods = new ModifyMessageRequest().setAddLabelIds(labelsToAdd);
			service.users().messages().modify("me",messageId, mods).execute();
			return true;
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null,"Erro na classe GmailAPI.java metodo addLabelIds ERRO:" + e);
			return false;
		}
	}
	
	/*
	 * Essa funcao deleta o email requisitado.
	 * @parametro messageId Id da mensagem que vai ser deletada
	 * @return Retorna true quando o email é deletado e false quando ocorre algum erro.
	 */
	public Boolean deleteEmail(String messageId)
	{
		try
		{
			Gmail service = Service.getService();
			service.users().messages().delete("me",messageId);
			return true;
		}
		catch (IOException e)
		{
			JOptionPane.showMessageDialog(null, "Erro na classe Email metodo deleteEmail Erro:"+ e );
			return false;
		}
	}
}
