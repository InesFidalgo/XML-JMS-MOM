 import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.JMSRuntimeException;
import javax.naming.InitialContext;
import javax.naming.NamingException;


public class TopicSender {
	private ConnectionFactory cf;
    private Destination d;
    

    public TopicSender() throws NamingException {
        this.cf = InitialContext.doLookup("jms/RemoteConnectionFactory");
        this.d = InitialContext.doLookup("jms/topic/Topic");
    }

    public void send(String xml){
    	try{
    		JMSContext jcontext = cf.createContext("test", "test");
            JMSProducer mp = jcontext.createProducer();
            mp.send(d, jcontext.createTextMessage(xml));
            jcontext.close();
    	}catch(JMSRuntimeException e){
    		e.printStackTrace();
    		
    	}
                        
            
}
    
    public static void main(String[] args) throws NamingException {
    	//MedalKeeper m = new MedalKeeper();
    	
    	//send("")
    	//System.out.println("criou");
    }
    

}
