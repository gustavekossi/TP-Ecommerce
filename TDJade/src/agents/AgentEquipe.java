package agents;


import gui.Window4Agent;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.WakerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.proto.AchieveREInitiator;


/**
 * agent equipe qui doit trouver des crenaux
 * @author Emmanuel ADAM
 *
 */
@SuppressWarnings("serial")
public class AgentEquipe extends Agent {
	
	/**vue personnelle sur agenda */
	int [][][] localView;
	/**son no*/
	int noEquipe;
	/**sa specificite*/
	String type;
	/** ses voisins equipes*/
	AID[] equipesVoisines;
	/** ses voisins clients*/
	AID[] clientsVoisins;
	/**sa fenetre d'affichage*/
	Window4Agent gui;

	/** Initialisation de l’agent */	
	protected void setup() {
		localView = new int[2][3][4];
		
		//recup des infos dans la ligne de commande
		Object[] args = getArguments();
		if (args != null) {
			if (args.length == 2)
			{
				// normalement param 1 = no de l'agent
				noEquipe = Integer.parseInt(args[0].toString());
				//et param 2 = type de l'agent
				type = args[1].toString();
			}
		}
		
		//se declarer en tant que equipe (type de service = assistance, nom de service = equipe (possibilite de modifier)
		register("assistance", "equipe");
		
		
		System.out.println("Hello! Agent Equipe "+getAID().getName()+"; je suis pret. ");
		gui = new Window4Agent(getLocalName());		
		afficheAgenda();
					
		// ajout du comportement ChoisirDate
		// à faire....
		
		//partir à la recherche de voisins equipe  et clients:  attendre un peu que les autres soient lancés
		// (100ms)
		addBehaviour(new WakerBehaviour(AgentEquipe.this, 50) {
			protected void onWake() {
				equipesVoisines = updateVoisins("assistance", "equipe");
				println(": Les agents suivants ont été trouvés :");
				for(int i=0; i<equipesVoisines.length; i++)
					println("equipe " + equipesVoisines[i].getLocalName());
				
				clientsVoisins = updateVoisins("assistance", "client");
				println(": Les agents suivants ont été trouvés :");
				for(int i=0; i<clientsVoisins.length; i++)
					println("voisin " + clientsVoisins[i].getLocalName());
			}
		});	
		
		//demander les contraintes aux clients (idem apres qq ms)
		addBehaviour(new WakerBehaviour(AgentEquipe.this, 100) {
			protected void onWake() {
				//preparation du message
				ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
				request.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
				for(int i=0; i<clientsVoisins.length; i++)
					request.addReceiver(clientsVoisins[i]);
				request.setConversationId("askContraintes");
				request.setContent("give me your constraints please");
				request.setReplyWith("askContraintes"+System.currentTimeMillis());
				//on passe par un protocole, pour attendre les réponses de tous les destinataires
				myAgent.addBehaviour( new AchieveREInitiator(myAgent, request){
				//attend toutes les réponses, pour chaque reponse inform, handleInform est lancé
					protected void handleInform(ACLMessage reponse) 
					 {
							String contenu = reponse.getContent();
							println("recu ceci : " + contenu );
							String[] elts = contenu.split(";");
							int noClient = Integer.parseInt(elts[0]);
							for(int j=1; j<elts.length; j+=2)
								localView[Integer.parseInt(elts[j])-1][noClient-1][Integer.parseInt(elts[j+1])-1] = -1;
					 }
					protected void handleAllResponses(java.util.Vector responses)
					{
						println("toutes les reponses ont ete recues, je peux travailler");
						println("apres reception de contraintes des clients : ");
						afficheAgenda();						
						// lancer alors le comportement de recherche de créneaux... comportement à définir
						myAgent.addBehaviour(new roles.SePlacer());
					}
				});	
			}});
	}
	
	
	
	/**affiche la connaissance sur l'agenda*/
	public void afficheAgenda()
	{
		gui.println("mes connaissances sur l'agenda");
		for(int i=0; i<2; i++)
		{
			gui.println("JOUR " + (i+1));
			gui.println("       |09-11|11-13|XX|14-16|16-18|");
			gui.println("-----------------------------------");
			for(int j=0; j<3; j++)
			{
				String ch = "c "+ (j+1) + " ";				
				for(int k=0; k<2; k++)
				{
					int val = localView[i][j][k];
					ch = ch + "|  "+ val;
					ch = ch + (val<0?" ":"  ");
				}
				ch = ch + "|XX";
				for(int k=2; k<4; k++)
				{
					int val = localView[i][j][k];
					ch = ch + "|  "+ val;
					ch = ch + (val<0?" ":"  ");
				}
				
				gui.println(ch+"|");
				gui.println("-----------------------------------");
				gui.println("");
			}
		}		

	}
	

	
	/***
	 * met à jour la liste des voisins
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
		catch (FIPAException fe) {
			fe.printStackTrace();
		}
		result = new AID[j];
		for(int i=0; i<j; i++)
			result[i] = agentsAID[i];
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
		System.out.println("Agent equipe "+getAID().getName()+" quitte la plateforme. ");
	}
	
	public void addCreneau(int equipe, int jour, int client, int creneau)
	{
		localView[jour][client][creneau] = equipe;
	}


	/**
	 * @return the localView
	 */
	public int[][][] getLocalView() {
		return localView;
	}


	/**
	 * @return the noEquipe
	 */
	public int getNoEquipe() {
		return noEquipe;
	}



	/**
	 * @return the equipesVoisines
	 */
	public AID[] getEquipesVoisines() {
		return equipesVoisines;
	}

}
