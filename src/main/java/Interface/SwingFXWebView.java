package Interface;



import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.sun.javafx.application.PlatformImpl;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;



public class SwingFXWebView{
	private static final long serialVersionUID = 1L;
	
	public static JPanel gerar(String load)
	{
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
				webEngine.load(load);
				
				ObservableList<Node> children = root.getChildren();
				children.add(browser);
				
				jfxPanel.setScene(scene);
			}
		});
		return jPanel;
	}
	

	
	public static void main(String args[])
	{
		
				
				final JFrame frame = new JFrame();
				frame.getContentPane().add(gerar("http://www.google.com"));
				frame.setMinimumSize(new Dimension(640, 480));
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				frame.setVisible(true);
				
				
				final JFrame frame2 = new JFrame();
				frame2.getContentPane().add(gerar("http://www.youtube.com"));
				frame2.setMinimumSize(new Dimension(640, 480));
				frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				frame2.setVisible(true);
		
	
	}
	

}
