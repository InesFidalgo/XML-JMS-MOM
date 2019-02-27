
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlMixed;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


//-------------------------MUNDO------------------------------------------

@XmlRootElement(name = "mundo")
public class Mundo {
	
	public Mundo(){
		pais= new ArrayList<Mundo.Pais>();
	}

    @XmlElement(name = "pais")
    public static List<Mundo.Pais> pais;
    

    public List<Mundo.Pais> getPais() {
        if (pais == null) {
            pais = new ArrayList<Mundo.Pais>();
        }
        return this.pais;
    }

    
//--------------------------------------PAIS--------------------------------------

    public static class Pais {
    	
    	
    	
        protected String nome;
        protected String abreviatura;
        @XmlAttribute(name="ouro")
        protected int ouro;
        @XmlAttribute(name="prata")
        protected int prata;
        @XmlAttribute(name="bronze")
        protected int bronze;
        
   
    	@XmlElement(name = "modalidade")
    	@XmlElementWrapper(name= "modalidades")
        public List<Modalidade> listap;
    	
    	

    	//mundo->lista de paises->3 listas ouro,prata, bronze-> lista modalidades->pessoas->atributo tipo
    	public Pais(String abreviatura, String nome, int ouro, int prata, int bronze){
    		this.nome=nome;
    		this.abreviatura= abreviatura;
    		this.ouro =ouro;
    		this.prata=prata;
    		this.bronze=bronze;
    		listap=new ArrayList<Modalidade>();
    		
    		
    		
    	}
    	public Pais(){}
    	
    	
    	public void addPessoa(String medalha, String modalidade, String tipo, String nome){
    		Modalidade m;
    		Boolean gotit=false;
    		
    			for (Modalidade T:listap){
    				if (T.nome.equals(modalidade)){
    					T.addPessoa(tipo,nome,medalha);
    					gotit=true;
    				}
    			}
    			if (gotit==false){
    				m=new Modalidade(modalidade);
    				m.addPessoa(tipo,nome,medalha);
    				listap.add(m);
    			}
    		
    	}
        

        public List<Modalidade> getP() {
            if ( listap== null) {
                listap = new ArrayList<Modalidade>();
            }
            return this.listap;
        }
        
        
        

        
       

        @XmlElement(name = "nome")
        public String getNome() {
            return nome;
        }

       
        public void setNome(String value) {
            this.nome = value;
        }

        @XmlAttribute(name = "abreviatura")
        public String getAbreviatura(){
            return abreviatura;
        }

        
        public void setAbreviatura(String value) {
            this.abreviatura = value;
        }
    }

    //----------------------------------MODALIDADE    -----------------------------------
        public static class Modalidade {
        	
        	@XmlAttribute
        	public String nome;
        	@XmlElement(name = "pessoa")
            public List<Pessoa> lista_pessoas;
            
        	Modalidade(String modalidade){
        		nome=modalidade;
        		lista_pessoas=new ArrayList<Pessoa>();
        	}
        	public Modalidade(){}
        	
            public List<Pessoa> getModalidade() {
                if ( lista_pessoas== null) {
                    lista_pessoas = new ArrayList<Pessoa>();
                }
                return this.lista_pessoas;
            }
        	
            public void addPessoa(String tipo, String nome, String medalha){
            	Pessoa p = new Pessoa(tipo,nome,medalha);
            	lista_pessoas.add(p);
            }
        	
        }
        
        
        
        //------------------------------------PESSOA---------------------------------------------
		public static class Pessoa {
			
			@XmlAttribute(name = "nome")
			public  String nome;
			@XmlAttribute(name = "tipo")
			public String tipo;
			@XmlAttribute(name = "medalha")
			public String medalha;
			private Pessoa(String tipo, String nome, String medalha){
				this.tipo = tipo;
				this.nome =nome;
				this.medalha=medalha;
   	
		        	
		}public Pessoa(){}
			
	        
	        
	      
			
		}
        
        
    
   
}
    
  
