import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.JMSRuntimeException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ReceiverQueue implements MessageListener {
	private ConnectionFactory cf;
    private Destination d;
    private Message mensagem;
    private JMSProducer producer;
    private MedalKeeper keep;
    //vai buscar a data que está no topico
    public ReceiverQueue() throws NamingException {
        this.cf = InitialContext.doLookup("jms/RemoteConnectionFactory");
        this.d = InitialContext.doLookup("jms/queue/PlayQueue");
    }

    public Message receive() throws JMSException {
    	//System.out.println("entrou");
        Message mensagem = null;
        try (JMSContext jcontex = cf.createContext("test", "test");) {
        	JMSConsumer mc = jcontex.createConsumer(d);
        	 mensagem = mc.receive();
        	 System.out.println(""+mensagem.getJMSReplyTo());
            
        } catch (JMSRuntimeException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
        	ex.printStackTrace();
        }
        System.out.println(mensagem.getStringProperty("query"));
        return mensagem;
}

	@Override
	public void onMessage(Message msg) {
		
		
		
		////////////////////////
		
		Message resposta = null;
		try {
			resposta = keep.requester(msg.getStringProperty("query"), msg.getIntProperty("type"),msg.getJMSCorrelationID());
		} catch (JMSException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			this.producer.send(mensagem.getJMSReplyTo(),mensagem );
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}
}
