package agents;


import gui.Window4Agent;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.WakerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.AchieveREResponder;

/**
 * agent client o� les equipes vont intervenir
 * @author Emmanuel ADAM
 *
 */
@SuppressWarnings("serial")
public class AgentClient extends Agent {
	
	/**vue personnelle sur agenda */
	int [][][] localView;
	/**son no*/
	int noClient;
	/**ses contraintes (en 0 le nb de contraintes, 1 contrainte = jour,creneau)*/
	int []contraintes;
	/** ses voisins equipes*/
	AID[] equipesVoisines;
	/**sa fenetre d'affichage*/
	Window4Agent gui;
	
	/** Initialisation de l�agent */	
	protected void setup() {
		localView = new int[2][3][4];
		contraintes = new int[5];
		
		//recup des infos dans la ligne de commande
		Object[] args = getArguments();
		if (args != null) {
			if (args.length >= 3)
			{
				// normalement param 1 = no de l'agent
				noClient = Integer.parseInt(args[0].toString());
				//il existe au moins une contraintes
				contraintes[0] = 1;
				//et param 2 = jour de la premi�re contrainte
				contraintes[1] = Integer.parseInt(args[1].toString());
				//et param 3 = creneau de la premi�re contrainte
				contraintes[2] = Integer.parseInt(args[2].toString());
				if (args.length >= 5)
				{
					//il existe au moins deux contraintes
					contraintes[0]++;
					//et param 2 = jour de la premi�re contrainte
					contraintes[3] = Integer.parseInt(args[3].toString());
					//et param 3 = creneau de la premi�re contrainte
					contraintes[4] = Integer.parseInt(args[4].toString());
				}
			}
		}
		
		//se declarer en tant que equipe (type de service = assistance, nom de service = equipe (possibilite de modifier)
		register("assistance", "client");
		
		
		System.out.println("Hello! Agent Client "+getAID().getName()+"; je suis pret. ");
		gui = new Window4Agent(getLocalName());		
		gui.println("Mes impossibilites : ");
		for(int i=0; i<contraintes[0]; i++)
			gui.println("indisponible jour " + contraintes[2*i+1] + ", creneau " + contraintes[2*i+2]  );
					
		
		//partir � la recherche de voisins equipe :  attendre un peu que les autres soient lanc�s
		// (50ms)
		addBehaviour(new WakerBehaviour(AgentClient.this, 50) {
			protected void onWake() {
				equipesVoisines = updateVoisins("assistance", "equipe");
				println(": Les agents suivants ont �t� trouv�s :");
				for(int i=0; i<equipesVoisines.length; i++)
					println("equipe " + equipesVoisines[i].getLocalName());

			}
		});
		// attendre une demande de contraintes de la part des �quipes
		addBehaviour(new WakerBehaviour(AgentClient.this, 100) {
			protected void onWake() {
				//preparation de la reception du message
				MessageTemplate mt = MessageTemplate.MatchConversationId("askContraintes");
				// on passe par un protocole pour la garantie de succ�s
				myAgent.addBehaviour(new AchieveREResponder(myAgent, mt)
				{
					// si une question est re�ue par le client, cette fonction est lanc�e
					protected ACLMessage handleRequest(ACLMessage request)
					{
						//la reponse est de type INFORM
						ACLMessage result = request.createReply();
						result.getPostTimeStamp(); 
						result.setPerformative(ACLMessage.INFORM);
						String content = ""+ noClient;
						for(int i=0; i<contraintes[0]; i++)
							content = content + ";" + contraintes[2*i+1] + ";" + contraintes[2*i+2];
						println("j'envoie mes contraintes sous la forme " + content + "� l'agent " + request.getSender().getLocalName());
						result.setContent(content);
						//l'envoie au client est realis� par le protocole
						return result;
					}
				}
				);}});
		
	}
	
	
	/***
	 * met � jour la liste des voisins
	 */
	private AID[] updateVoisins(String _typeService, String _nameService)
	{
		AID[] result = null;;
		DFAgentDescription modele = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType(_typeService);
		sd.setName(_nameService);
		modele.addServices(sd);
		int j=0;
		AID[] agentsAID = null;
		try {
			DFAgentDescription[] agentsDescription = DFService.search(this, modele); 
			agentsAID = new AID[agentsDescription.length];
			for (int i = 0; i < agentsDescription.length; ++i) {
				if(!agentsDescription[i].getName().equals(this.getName()))
				{
					agentsAID[j] = agentsDescription[i].getName();
					j++;
				}
			}
		}
		catch (FIPAException fe) { fe.printStackTrace(); }
		result = new AID[j];
		for(int i=0; i<j; i++) result[i] = agentsAID[i];
		return result;
	}
	
	/**affiche la chaine dans la fenetre associee*/
	public void println(String msg)
	{
		gui.println(msg);
	}
	
	/**
	 * register as a service to the Directory Facilitator
	 * @param type the type of service
	 * @param name the name of the service
	 * */
	void register(String _type, String _name)
	{
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType(_type);
		sd.setName(_name);
		dfd.addServices(sd);
		try { DFService.register(this, dfd); } 
		catch (FIPAException fe) { fe.printStackTrace(); }		
	}

	
	// 'Nettoyage' de l'agent
	protected void takeDown() {
		System.out.println("Agent client "+getAID().getName()+" quitte la plateforme. ");
	}
	
	public void addCreneau(int equipe, int jour, int client, int creneau)
	{
		localView[jour][client][creneau] = equipe;
	}
}
