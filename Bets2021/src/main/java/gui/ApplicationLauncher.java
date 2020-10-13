package gui;


import java.net.URL;
import java.util.Locale;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.UIManager;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import configuration.ConfigXML;
import dataAccess.DataAccess;
import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;

public class ApplicationLauncher {
	
	public static void main(String[] args) {

		Lock lock = new ReentrantLock();
		
		ConfigXML c=ConfigXML.getInstance();
	
		System.out.println(c.getLocale());
		
		LanguageGUI l = new LanguageGUI();
		l.setVisible(true);
		
		//Locale.setDefault(new Locale(c.getLocale()));
		
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e1) {
			
			e1.printStackTrace();
			Thread.currentThread().interrupt();
		}
		
		System.out.println("Locale: "+Locale.getDefault());
		
		
		//LoginGUI a = new LoginGUI();
		

		try {
			
			BLFacade appFacadeInterface;
//			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
//			UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
			System.out.println(c);
			if (c.isBusinessLogicLocal()) {
				System.out.println("sartu"+c.getDataBaseOpenMode().equals("initialize"));
				
				DataAccess da= new DataAccess(c.getDataBaseOpenMode().equals("initialize"));
				System.out.println("sartu2");
				appFacadeInterface=new BLFacadeImplementation(da);
				System.out.println("kaixo");
				
				
				
			}
			
			else { //If remote
				
				 String serviceName= "http://"+c.getBusinessLogicNode() +":"+ c.getBusinessLogicPort()+"/ws/"+c.getBusinessLogicName()+"?wsdl";
				 
				//URL url = new URL("http://localhost:9999/ws/ruralHouses?wsdl");
				URL url = new URL(serviceName);

		 
		        //1st argument refers to wsdl document above
				//2nd argument is service name, refer to wsdl document above
//		        QName qname = new QName("http://businessLogic/", "FacadeImplementationWSService");
		        QName qname = new QName("http://businessLogic/", "BLFacadeImplementationService");
		 
		        Service service = Service.create(url, qname);
		 
		         appFacadeInterface = service.getPort(BLFacade.class);
			} 
			/*if (c.getDataBaseOpenMode().equals("initialize")) 
				appFacadeInterface.initializeBD();
				*/
			MainGUI.setBussinessLogic(appFacadeInterface);

		

			
		}catch (Exception e) {	
			System.out.println("Error in ApplicationLauncher: "+e.toString());
		}
		//a.pack();


	}

}
