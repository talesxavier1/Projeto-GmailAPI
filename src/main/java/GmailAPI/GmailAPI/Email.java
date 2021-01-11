package GmailAPI.GmailAPI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import com.google.api.client.util.Base64;
import com.google.api.client.util.StringUtils;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import com.google.api.services.gmail.model.ModifyMessageRequest;
import com.sun.javafx.application.PlatformImpl;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class Email
{
	private List<String> labelIds = new ArrayList<String>();
	private String id,
	               mimeType,
	               date,
	               from,
	               subject,
	               bodyData,
	               snippet;
	private int index;
	
	private Boolean isFavorite = false;
	private Boolean isImportant = false;
	private Boolean isRead = false;
	
	public Email(Message message,int index_)
	{
		setIndex(index_);
		id = message.getId();
		labelIds = message.getLabelIds();
		for (int i = 0; i < message.getPayload().getHeaders().size(); i++)
		{
			if (message.getPayload().getHeaders().get(i).get("name").equals("Date"))
			{
				@SuppressWarnings("deprecation")
				Date date_ = new Date(message.getPayload().getHeaders().get(i).getValue());
				DateFormat dateFormat1= new SimpleDateFormat("dd/MM/yyyy");
				
				Date dateAtual = new Date();
				DateFormat dateFormatDataAtual= new SimpleDateFormat("dd/MM/yyyy");
				
				if ((dateFormat1.format(date_).toString()).equals(dateFormatDataAtual.format(dateAtual)))
				{
					date="Hoje";
				}
				else
				{
					date=dateFormat1.format(date_).toString();
				}
			}
			else if (message.getPayload().getHeaders().get(i).get("name").equals("Subject"))
			{
				subject = message.getPayload().getHeaders().get(i).getValue();
			}
			else if (message.getPayload().getHeaders().get(i).get("name").equals("From"))
			{
				from = message.getPayload().getHeaders().get(i).getValue();
			}
		}
		mimeType = message.getPayload().getMimeType();
		
		if(mimeType.equals("text/html"))
		{
			bodyData = message.getPayload().getBody().getData();
		}
		else if (mimeType.equals("text/plain")) 
		{
			message.getPayload().getBody().getData();
			System.out.println("ff");
		}
		else
		{
			for (int i = 0; i < message.getPayload().getParts().size(); i++)
			{
				if(message.getPayload().getParts().get(i).getMimeType().equals("text/html"))
				{
					bodyData = message.getPayload().getParts().get(i).getBody().getData();
				}
			}
		}
		bodyData =StringUtils.newStringUtf8(Base64.decodeBase64(bodyData));
		
//		bodyData = bodyData.replace("<!DOCTYPE html>", "");
//		bodyData = bodyData.replace("<html>", "");
//		bodyData = bodyData.replace("</html>", "");
//		bodyData = bodyData.replace(" <head>\r\n" + 
//				"        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\r\n" + 
//				"        <title>Trabajo.org</title>\r\n" + 
//				"    </head>", "");
		
		//System.out.println(bodyData);
		
		snippet = message.getSnippet();
		
		for (int i = 0; i < labelIds.size(); i++)
		{
			if (labelIds.get(i).equals("STARRED")) 
			{
				isFavorite = true;
			}
			else if (labelIds.get(i).equals("IMPORTANT"))
			{
				isImportant=true;
			}
			else if (labelIds.get(i).equals("UNREAD"))
			{
				isRead = true;
			}
		}
	}
	////////////////////////////////////////
	public JPanel showEmail()
	{
		try {
			JPanel jPanel = new JPanel();
			JFXPanel jfxPanel = new JFXPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.add(jfxPanel, BorderLayout.CENTER);
			
			PlatformImpl.startup(new Runnable(){
				public void run(){
					Stage stage = new Stage();
					stage.setResizable(true);
					
					Group root = new Group();
					Scene scene = new Scene(root,80,20);
					stage.setScene(scene);
					
					
					WebView browser = new WebView();
					WebEngine webEngine = browser.getEngine();
					
					webEngine.loadContent(bodyData);
					
					ObservableList<Node> children = root.getChildren();
					children.add(browser);
					jfxPanel.setScene(scene);
				}
			});
			return jPanel;
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null,"Erro na classe Email metodo showEmail Erro:" + e);
			return null;
		}

		
		
	}
	
	public Boolean deleteEmail()
	{
		try
		{
			Gmail service = Service.getService();
			service.users().messages().delete("me",id);
			return true;
		} catch (IOException e)
		{
			JOptionPane.showMessageDialog(null, "Erro na classe Email metodo deleteEmail Erro:"+ e );
			return false;
		}
	}
	
	public Boolean removeLabelIds(String labelId)
	{
		try
		{
			Gmail service =  Service.getService();
			List<String>labelsToRemove = new ArrayList<String>();
			labelsToRemove.add(labelId);
			ModifyMessageRequest mods = new ModifyMessageRequest().setRemoveLabelIds(labelsToRemove);
			service.users().messages().modify("me",id, mods).execute();
			return true;
		} 
		catch (IOException e)
		{
			JOptionPane.showMessageDialog(null,"Erro na classe Email metodo removeLabelIds ERRO:" + e);
			return false;
		}
	}
	
	public boolean addLabelIds(String labelId)
	{
		try
		{
			Gmail service =  Service.getService();
			List<String>labelsToAdd = new ArrayList<String>();
			labelsToAdd.add(labelId);
			ModifyMessageRequest mods = new ModifyMessageRequest().setAddLabelIds(labelsToAdd);
			service.users().messages().modify("me",id, mods).execute();
			return true;
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null,"Erro na classe Email metodo addLabelIds ERRO:" + e);
			return false;
		}
	}
	
	
	////////////////////////////////////////
	
	
	
	
	public List<String> getLabelIds() {
		return labelIds;
	}


	public void setLabelIds(ArrayList<String> labelIds) {
		this.labelIds = labelIds;
	}


	public Boolean getIsFavorite() {
		return isFavorite;
	}


	public void setIsFavorite(Boolean isFavorite) {
		this.isFavorite = isFavorite;
	}


	public Boolean getIsImportant() {
		return isImportant;
	}


	public void setIsImportant(Boolean isImportant) {
		this.isImportant = isImportant;
	}


	public Boolean getIsUnread() {
		return isRead;
	}


	public void setIsUnread(Boolean isUnread) {
		this.isRead = isUnread;
	}


	public String getId() {
		return id;
	}


	public String getMimeType() {
		return mimeType;
	}


	public String getDate() {
		return date;
	}


	public String getFrom() {
		return from;
	}


	public String getSubject() {
		return subject;
	}


	public String getBodyData() {
		return bodyData;
	}


	public String getSnippet() {
		return snippet;
	}

	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	
	public String getToString() {
		return "Email [id=" + id + "| labelIds=" + labelIds + "| mimeType=" + mimeType + "| date=" + date + "| from="
				+ from + "| subject=" + subject + "| snippet=" + snippet + "| isFavorite="
				+ isFavorite + "| isImportant=" + isImportant + "| isUnread=" + isRead + "]";
	}
}
