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

public class ReqSendKeeper implements MessageListener{
	private ConnectionFactory cf;
	private javax.jms.Connection c;
    private Destination d;
    private Session session;
    

    public ReqSendKeeper() throws NamingException {
    	this.cf = InitialContext.doLookup("jms/RemoteConnectionFactory");
        this.d = InitialContext.doLookup("jms/queue/PlayQueue");
        
		
        
    }
    
    public void send (Message msg, Destination replyTo )throws Exception{
	   
    	 JMSContext jcontext = cf.createContext("test", "test");
         JMSProducer mp = jcontext.createProducer();
         //req.setJMSCorrelationID(msg.getJMSCorrelationID());
        
         mp.send(replyTo, msg);
         System.out.println("enviou");
         jcontext.close();
            
         //jcontext.close();   
    }
   
    

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
