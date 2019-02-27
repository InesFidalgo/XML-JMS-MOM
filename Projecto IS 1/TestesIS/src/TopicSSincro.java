import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSRuntimeException;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class TopicSSincro {
	 private ConnectionFactory cf;
	    private Destination d;
	    //vai buscar a data que está no topico
	    public TopicSSincro() throws NamingException {
	        this.cf = InitialContext.doLookup("jms/RemoteConnectionFactory");
	        this.d = InitialContext.doLookup("jms/topic/Topic");
	    }

	    public String receive() {
	    	//System.out.println("entrou");
	        String mensagem = null;
	        try (JMSContext jcontex = cf.createContext("test", "test");) {
	            JMSConsumer mc = jcontex.createConsumer(d);
	            mensagem = mc.receiveBody(String.class);
	        } catch (JMSRuntimeException ex) {
	            ex.printStackTrace();
	        }
	        //System.out.println(mensagem);
	        return mensagem;
	}
}
