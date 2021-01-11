package GmailAPI.GmailAPI;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Label;
import com.google.api.services.gmail.model.ListLabelsResponse;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;
import com.google.api.services.gmail.model.ModifyMessageRequest;

import Interface.ShowEmailWebView;
import javafx.application.Application;

@SuppressWarnings("deprecation")
public class GmailAPI extends JFrame
{
	private static final long serialVersionUID = 1L;
	public static final String APPLICATION_NAME = "GmailAPI";
	private static final String user = "me";
	
	
	public static Gmail getService(String RefreshToken) throws IOException, GeneralSecurityException
	{
		File filePath = new File(System.getProperty("user.dir")+"/credentiais/credentials2.json");
		com.google.api.client.json.JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
		InputStream in = new FileInputStream(filePath);
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
		
		
		Credential authorize = new GoogleCredential.Builder().setTransport(GoogleNetHttpTransport.newTrustedTransport())
				.setJsonFactory(JSON_FACTORY)
				.setClientSecrets(clientSecrets.getDetails().getClientId().toString(),
				 clientSecrets.getDetails().getClientSecret().toString())
				.build()
				.setAccessToken("")
				.setRefreshToken(RefreshToken);
		final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
		Gmail service = new Gmail.Builder(httpTransport, JSON_FACTORY, authorize).setApplicationName(GmailAPI.APPLICATION_NAME).build();
		
		return service;
	}
	
	

	public static void main(String args[]) throws Exception
	{
		//1//0hLhiSM9a82E6CgYIARAAGBESNwF-L9IrGaTQbvqWf1QsuiO8J8eUxvDJEHSSkrl2jcZob32Y-_CZGEixlY22sZmi2zVjXeJNhk4
		//Gmail service = getService("1//0hwfloN23ZxTkCgYIARAAGBESNwF-L9Ir0eR0Dg71re_uFSQUwptJEYPu2sOX9XFXd2Y4PfYRay3mjV37n2oCTSnGZv9x6HWPjyI");
		Gmail service = getService("1//0hLhiSM9a82E6CgYIARAAGBESNwF-L9IrGaTQbvqWf1QsuiO8J8eUxvDJEHSSkrl2jcZob32Y-_CZGEixlY22sZmi2zVjXeJNhk4");
		
		//request

		Gmail.Users.Messages.List request = service.users().messages().list(user);
		ListMessagesResponse listMessagesResponse = request.execute();
		//System.out.println(request);
		//System.out.println(listMessagesResponse);
		
		String mensagenId = listMessagesResponse.getMessages().get(0).getId();
		
		service.users().messages().delete(user, mensagenId).execute();
				//.get(user, mensagenId).execute();


		//isso muda os labels id
//		List<String> labelsToRemove = new ArrayList<String>();
//		labelsToRemove.add("IMPORTANT");
//        ModifyMessageRequest mods;
//        mods = new ModifyMessageRequest().setRemoveLabelIds(labelsToRemove);
//        Message message = service.users().messages().modify("me", mensagenId, mods).execute();
		//System.out.println(message);

//		String user = "me";
//        ListLabelsResponse listResponse = service.users().labels().list(user).execute();
//        List<Label> labels = listResponse.getLabels();
//        if (labels.isEmpty())
//        {
//            System.out.println("No labels found.");
//        }
//        else
//        {
//            System.out.println("Labels:");
//            for (Label label : labels)
//            {
//                System.out.printf("- %s\n", label.getName());
//            }
//        }
		//System.out.println(message);
		
		//System.out.println(message.getSnippet());
//		
//		for (int i = 0; i < listMessagesResponse.getMessages().size(); i++)
//		{
//			String mensagenId = listMessagesResponse.getMessages().get(i).getId();
//			Message message = service.users().messages().get(user, mensagenId).execute();
//			System.out.println("--------------------------------------------------- email:"+ i +"---------------------------------------------------");
//			System.out.println(message.getSnippet());
//			System.out.println("--------------------------------------------------------------------------------------------------------------------");
//		}
		
//		if (message.getPayload().getMimeType().equals("text/html"))
//		{
//			String emailBody = StringUtils.newStringUtf8(Base64.decodeBase64(message.getPayload().getBody().getData()));
//
//			emailBody = emailBody.replace("<!DOCTYPE html>", "");
//			emailBody = emailBody.replace("<html>", "");
//			emailBody = emailBody.replace("</html>", "");
//			emailBody = emailBody.replace(" <head>\r\n" + 
//					"        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\r\n" + 
//					"        <title>Trabajo.org</title>\r\n" + 
//					"    </head>", "");
//			
//			
//		    JEditorPane editor = new JEditorPane("text/html",emailBody);
//		        editor.setEditable(false);
//		        JScrollPane pane = new JScrollPane(editor);
//		        JFrame f = new JFrame("HTML Demo");
//		        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		        f.getContentPane().add(pane);
//		        f.setSize(800, 600);
//		        f.setVisible(true);
//		}
//		else
//		{
//			String emailBody = StringUtils.newStringUtf8(Base64.decodeBase64(message.getPayload().getParts().get(0).getBody().getData()));
//			System.out.println("_________________________________Snippet_______________________________________________________");
//			System.out.println(message.getSnippet());
//			System.out.println("_______________________________________________________________________________________");
//			System.out.println(emailBody);
//		}

		
	}
	
}
