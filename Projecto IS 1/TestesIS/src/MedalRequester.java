 import java.util.Random;
import java.util.Scanner;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.JMSRuntimeException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.management.Query;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Persistence;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;




	
	public class MedalRequester  {
	 
		/*o que eu ja tinha*/
	private static int type;
	private static String query;
	private static ConnectionFactory cf;
    private static Destination d;
    private static MedalKeeper keep;
    ////////////////////////////////////////
    
	private javax.jms.Connection c;
   
    private Session session;
    private static Destination tempDest;
	
	
	public MedalRequester() throws NamingException {
		this.cf = InitialContext.doLookup("jms/RemoteConnectionFactory");
        this.d = InitialContext.doLookup("jms/queue/PlayQueue");
	}
	
	
	//////////////////////////////////////ja tinha///////////////
	public static void main(String[] args) throws Exception {

		while(true){
		Requests qs = new Requests();
		String pedido = keep.pedido();
		
		
		String query = pedido.substring(1, pedido.length());
	
		int type = Integer.parseInt(pedido.substring(0,1));

		qs.sendcreate(query, type);
		//sendcreate(query, type);
		
		/*
		ReceiverQueueRequester qt = new ReceiverQueueRequester();
		Message repostasfinal = qt.receive(tempDest);
		System.out.println(repostasfinal);*/
		
		}
	}
	}
	
	
	
	