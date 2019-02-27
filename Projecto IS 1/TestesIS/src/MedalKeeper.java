import java.awt.List;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Scanner;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.JMSRuntimeException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.Source;
import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.*;

import org.xml.sax.SAXException;

//import org.jboss.marshalling.Unmarshaller;

//import Mundo.Modalidade;

//VALIDAR MENSAGEM COM XSD + 
//FAZER UNMARSHALL DA INFORMAÇÃO +
//GUARDAR EM MEMORIA +
//tem de escutar o topico e a queue - threads -

public class MedalKeeper {
	public static ArrayList<Mundo.Pais> a = new ArrayList<>();
	private static ConnectionFactory cf;
	public static Destination global;
	
	
	
//fazer unmarshall
 public static void main(String[] args) throws Exception {
	  TopicSSincro r = new TopicSSincro();
	  //////////////////////////////////////
	  Receiver rec = new Receiver();
	  
	  
	  ////////////////////////////////////////
	  while(true){
		  String mensagem = r.receive(); 
		  InputStream stream = new ByteArrayInputStream(mensagem.getBytes(Charset.forName("UTF-8")));
		  //System.out.println("Message: " + mensagem);
		  //validar
		  Validar(stream);
		  
		  
		  StringReader str = new StringReader(mensagem);
		  XMLInputFactory xinput = XMLInputFactory.newInstance();
		  XMLStreamReader xstream = xinput.createXMLStreamReader(str);
		  
		  JAXBContext jaxbContext = JAXBContext.newInstance(Mundo.class);
		  Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		 
		  
		  Mundo pas = (Mundo) jaxbUnmarshaller.unmarshal(xstream);
		  //GUARDAR TUDO NUMA LISTA
		  for(int i=0;i<pas.pais.size();i++){
			  a.add(pas.pais.get(i));
			  
		  }
		  /*
		  System.out.println("dfhjifcgvhjnk");
		  for(int i=0;i<pas.pais.size();i++){
			  System.out.println("dfhjifcgvhjnk");
			  System.out.println(a.get(i));
		  }*/
		  
		  
	  }
	 
	
	  

 }
 
 public static String pedido(){
		int type;
		String query;
		System.out.println("Tipo de pedido:\n1- Paises\n2- Modalidades\n3- Pessoas"); 
		Scanner sc = new Scanner(System.in);
		type = Integer.parseInt(sc.nextLine());
		System.out.println("Pedido:\n");
		query = sc.nextLine();
		String pedido = type+query;
		return pedido;
		
	}
	
 
 public static Message requester(String query, int type, String d ) throws JMSException, NamingException{
	cf = InitialContext.doLookup("jms/RemoteConnectionFactory");
	JMSContext jcontext = cf.createContext("test","test");
	
	
	 Message m =  jcontext.createMessage();
	
	
	System.out.println(query);
	System.out.println(type);
	String nova="";
	 //procurou pais
	
	if(type==1){
		for(int i=0;i<a.size();i++){
			if(a.get(i).getNome().equals(query)){
				nova+="Nome do País: "+a.get(i).getNome()+"\n"+"Abreviatura: "+a.get(i).getAbreviatura()+"\n"+"Nº de Medalhas de Ouro: "+a.get(i).ouro+"\n"+"Nº de Medalhas de prata: "+a.get(i).prata+"\n"+"Nº de Medalhas de bronze: "+a.get(i).bronze+"\n";
				for(int j=0;j<a.get(i).listap.size();j++){
					 nova+="Modalidade: "+a.get(i).listap.get(j).nome+"\n";
					 for(int k = 0;k<a.get(i).listap.get(j).lista_pessoas.size();k++){
						 nova+="Nome: "+a.get(i).listap.get(j).lista_pessoas.get(k).nome+"\n"+"Tipo: "+a.get(i).listap.get(j).lista_pessoas.get(k).tipo+"\n"+"Medalha: "+a.get(i).listap.get(j).lista_pessoas.get(k).medalha+"n";
					 }
				}
			}
			
		}

	}
	//procurou modalidade
	else if(type==2){
		for(int i=0;i<a.size();i++){
			for(int j=0;j<a.get(i).listap.size();j++){
				if(a.get(i).listap.get(j).nome.equals(query)){
					nova+="Modalidade: "+a.get(i).listap.get(j).nome+"\n";
					for(int k = 0;k<a.get(i).listap.get(j).lista_pessoas.size();k++){
						 nova+="Nome: "+a.get(i).listap.get(j).lista_pessoas.get(k).nome+"\n"+"Tipo: "+a.get(i).listap.get(j).lista_pessoas.get(k).tipo+"\n"+"Medalha: "+a.get(i).listap.get(j).lista_pessoas.get(k).medalha+"\n";
					 }
					
				}
			}
		}
		
	//procurar pessoa	
	}else if(type==3){
		for(int i=0;i<a.size();i++){
			for(int j=0;j<a.get(i).listap.size();j++){
				for(int k=0;k<a.get(i).listap.get(j).lista_pessoas.size();k++){
					nova+="Nome: "+a.get(i).listap.get(j).lista_pessoas.get(k).nome+"\n"+"Tipo: "+a.get(i).listap.get(j).lista_pessoas.get(k).tipo+"\n"+"Medalha: "+a.get(i).listap.get(j).lista_pessoas.get(k).medalha+"\n";
				}
			}
		}
		
	}
	else{
		nova+= ("type is not valid");
	}
	String resposta = "resposta";
	//System.out.println(nova);
	m.setStringProperty(resposta, nova);
	m.setJMSCorrelationID(d);
	jcontext.close();
	return m;
	
	
	}

 public static void Validar(InputStream stream) throws IOException, SAXException{
	 
	 URL schemaFile = new URL("file:\\C:\\Users\\utilizador\\Desktop\\TestesIS\\src\\Medalhas.xsd");
	 Source xmlFile = new StreamSource(stream);
	 SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
	 Schema schema = schemaFactory.newSchema(schemaFile);
	 Validator validator = schema.newValidator();
	 try {
	   validator.validate(xmlFile);
	   System.out.println("the message is valid");
	 } catch (SAXException e) {
	   System.out.println("the message is NOT valid");
	   System.out.println("Reason: " + e.getLocalizedMessage());
	 }
 }
 
 ///////////////////////////////////
 
 
 
////////////////////////////////////

public static class Receiver implements Runnable{
	
	Thread t;
	Receiver(){
		//System.out.println("Hhdgjhfer3435dg");  
		new Thread(this).start();
	}
	
	
	@Override
	public void run() {
			while(true){
				System.out.println("ola");
				ReceiverQueue r2=null;
				try {
					r2 = new ReceiverQueue();
				} catch (NamingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				Message m = null;
				try {
					m = r2.receive();
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
				
				System.out.println(m);
				
				try {
					String d = m.getJMSCorrelationID();
				} catch (JMSException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				
				
				try {
					
					ReqSendKeeper k = new ReqSendKeeper();
					Message man  = requester(m.getStringProperty("query"), m.getIntProperty("type"), m.getJMSCorrelationID());
					k.send(man, m.getJMSReplyTo());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				try {
					System.out.println("destination: "+m.getJMSReplyTo());
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		
	}

	
	
	 
	
}



}
