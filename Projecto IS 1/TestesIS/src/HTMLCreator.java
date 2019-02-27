import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.Charset;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;


public class HTMLCreator {

	public static void main(String[] args) {
		try{
			TopicSSincro r = new TopicSSincro();
			while(true){
				String mensagem = r.receive();
				InputStream stream = new ByteArrayInputStream(mensagem.getBytes(Charset.forName("UTF-8")));
				Validar(stream);
				generate(stream);
			}
			
		}catch(Exception e){
			
		}

	}
	
	public static void generate(InputStream stream){
		try {
	        TransformerFactory tFactory=TransformerFactory.newInstance();

	        Source xslDoc=new StreamSource("file:\\C:\\Users\\utilizador\\Desktop\\TestesIS\\src\\HSC.xsl");
	        Source xmlDoc=new StreamSource("file:\\C:\\Users\\utilizador\\Desktop\\TestesIS\\src\\Medalhas.xml");

	        String outputFileName="Table.html";

	        OutputStream htmlFile=new FileOutputStream(outputFileName);
	        Transformer trasform=tFactory.newTransformer(xslDoc);
	        trasform.transform(xmlDoc, new StreamResult(htmlFile));
	    } 
	    catch (Exception e) 
	    {
	        e.printStackTrace();
	    }
	}
	
	public static void Validar(InputStream stream) throws IOException, SAXException{
		
		 URL schemaFile = new URL("file:\\C:\\Users\\utilizador\\Desktop\\TestesIS\\src\\Medalhas.xsd");
		 Source xmlFile = new StreamSource(stream);
		 SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		 Schema schema = schemaFactory.newSchema(schemaFile);
		 Validator validator = schema.newValidator();
		 try {
		   validator.validate(xmlFile);
		   System.out.println(xmlFile.getSystemId() + " is valid");
		 } catch (SAXException e) {
		   System.out.println(xmlFile.getSystemId() + " is NOT valid");
		   System.out.println("Reason: " + e.getLocalizedMessage());
		 }
	 }

}

