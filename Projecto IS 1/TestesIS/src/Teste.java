import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.util.ArrayList;

import javax.naming.NamingException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Node;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;



public class Teste {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		Mundo novo = new Mundo();
		
		MedalKeeper m = null;
		long time=5000;
		 while (true){
			Document doc;
			String nova = null;
			TopicSender st = null;
		
			System.out.println("A funcionar");
			
			
			try {
				doc = Jsoup.connect("https://www.rio2016.com/en/medal-count-country").get();
				Elements s = doc.select("tr.table-medal-countries__link-table");//child 2 nome inteiro cjild 1 nome abreviado 3-ouro 4-prata 5-bronze 6-todas
			
			
			for (Element e:s){
				novo.pais.add( new Mundo.Pais(e.child(1).getElementsByClass("country").text(),e.child(2).getElementsByClass("country").text(), Integer.parseInt(e.child(3).text()), Integer.parseInt( e.child(4).text()), Integer.parseInt(e.child(5).text()) ));
			
				}
			
			s = doc.select("tr");//child 2 nome inteiro cjild 1 nome abreviado 3-ouro 4-prata 5-bronze 6-todas
			Mundo.Pais help = null;
			
			for (Element e:s){//0-medalha 1-desporto 2-tipo 3-nome
				if (e.className().equals("table-medal-countries__link-table")){
					for (Mundo.Pais p:novo.pais){
						if (p.abreviatura.equals(e.child(1).text())){
							help=p;
						}
					}
				}
				if (e.className().equals("type")){	
					Element h;
					h=e;
					help.addPessoa(h.child(0).text(),h.child(1).text(),h.child(2).text(),h.child(3).text());
					while (h.nextElementSibling()!=null){
						help.addPessoa(e.child(0).text(),h.nextElementSibling().child(1).text(),h.nextElementSibling().child(2).text(),h.nextElementSibling().child(3).text());
						h=h.nextElementSibling();
					}
					
				}
					
					
					
				}
			time=10*1000;
		 }catch(Exception ex){
	    	 
	    	 time=time*2;
	    	 System.out.println("waiting"+time);
	     }

	     try {

	        File file = new File("C:\\Users\\utilizador\\Desktop\\TestesIS\\src\\Medalhas.xml");
	        FileOutputStream os = new FileOutputStream(file);
	        StringWriter stw = new StringWriter();
	        JAXBContext jaxbContext = JAXBContext.newInstance(Mundo.class);//supostamente iria buscar o report da classe
	        //para voltar a aparecer o que aparece no xml... caso contrario aparece uma referencia para o objeto
		            javax.xml.bind.Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		
		    // output pretty printed
		    jaxbMarshaller.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, true);
		           
		    jaxbMarshaller.marshal(novo, os);
		    
		    jaxbMarshaller.marshal(novo, stw);
		    
		    nova = stw.toString();
		    
		    st = new TopicSender();
		    st.send(nova);
		    
		    
		   
		  } catch (JAXBException e) {
		    e.printStackTrace();
		  }

		    	
		     
		    Thread.sleep(time);

		 }
	
		
		
	}
}

