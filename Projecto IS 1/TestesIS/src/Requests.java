import java.sql.Connection;
import java.util.Random;
import java.util.Scanner;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.QueueReceiver;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Requests implements MessageListener{
	private ConnectionFactory cf;
	private javax.jms.Connection c;
    private Destination d;
    private Session session;
    private Destination tempDest;

    public Requests() throws NamingException {
    	this.cf = InitialContext.doLookup("jms/RemoteConnectionFactory");
        this.d = InitialContext.doLookup("jms/queue/PlayQueue");
        
		
        
    }
    
    public void sendcreate(String query1, int type1)throws Exception{
	    String query = "query";
	    String type = "type";
    	 JMSContext jcontext = cf.createContext("test", "test");
         JMSProducer mp = jcontext.createProducer();
         
         //mp.send(d, jcontext.createTextMessage(pedido));
         
         //mc.setMessageListener();
         Message req =  jcontext.createMessage();
         req.setStringProperty(query,query1 );
         req.setIntProperty(type, type1);
         System.out.println(req.getStringProperty("query"));
         System.out.println(req.getIntProperty("type"));
         //req.setText(pedido);
         

         tempDest = jcontext.createTemporaryQueue();
         
         req.setJMSReplyTo(tempDest);
         String correlationId = this.createString();
         req.setJMSCorrelationID(correlationId);
         mp.send(d, req);
         System.out.println("enviou");
         jcontext.stop();
         
         JMSConsumer mc = jcontext.createConsumer(tempDest);
         Message received = mc.receive();
         System.out.println(received);
         System.out.println(received.getJMSCorrelationID());
         System.out.println(received.getJMSMessageID());
         System.out.println(received.getStringProperty("resposta"));
        	 
         jcontext.close(); 
         /*
        //JMSConsumer mc2 = jcontext.createConsumer(tempDest);
        ReceiverQueueRequester qt = new ReceiverQueueRequester();
 		Message repostasfinal = qt.receive(tempDest);
 		System.out.println(repostasfinal);
 		jcontext.close(); */
 		

         
    }
    /*
    public Destination getDestination(){
		 return tempDest ;
	 }*/
    
    
    

	private String createString() {
		Random random = new Random(System.currentTimeMillis());
        long randomLong = random.nextLong();
        return Long.toHexString(randomLong);
	}

	@Override
	public void onMessage(Message textMsg) {
		String msg=null;
		try{
            if(textMsg instanceof TextMessage){
                TextMessage tmsg = (TextMessage) textMsg;
                msg = tmsg.getText();
                System.out.println(msg);
            }
        }catch(JMSException e){
            e.printStackTrace();
        }
		
	}
	
    

}
