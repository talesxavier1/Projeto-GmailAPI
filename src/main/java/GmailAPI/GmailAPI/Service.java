package GmailAPI.GmailAPI;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.security.GeneralSecurityException;

import javax.swing.JOptionPane;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.gmail.Gmail;

public class Service 
{
	private static Gmail service;
	
	public static void generateService(String refrashToken,String credentialName)
	{
		try
		{
			File filePath = new File(System.getProperty("user.dir")+"/credentiais/"+credentialName);
			com.google.api.client.json.JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
			InputStream in = new FileInputStream(filePath);
			GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
			
			@SuppressWarnings("deprecation")
			Credential authorize = new GoogleCredential.Builder().setTransport(GoogleNetHttpTransport.newTrustedTransport())
					.setJsonFactory(JSON_FACTORY)
					.setClientSecrets(clientSecrets.getDetails().getClientId().toString(),
					 clientSecrets.getDetails().getClientSecret().toString())
					.build()
					.setAccessToken("")
					.setRefreshToken(refrashToken);
			final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
			Gmail service_ = new Gmail.Builder(httpTransport, JSON_FACTORY, authorize).setApplicationName(GmailAPI.APPLICATION_NAME).build();
			service=service_;
		}
		catch (IOException | GeneralSecurityException e)
		{
			JOptionPane.showMessageDialog(null,"Erro na classe Service.java, metodo generateService Erro:" + e);
		}
		
	}
	
	public static Gmail getService()
	{
		return service;
	}
}
