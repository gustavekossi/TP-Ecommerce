package roles;

import agents.AgentEquipe;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
 * comportement principal, cherche une solution au DCSP
 */
@SuppressWarnings("serial")
public class SePlacer extends Behaviour
{


	/** message filter */
	public MessageTemplate mt;
	
	AgentEquipe monAgent;

	/** initialise le comportement*/
	public void onStart()
	{
		monAgent = (AgentEquipe)this.myAgent;
		monAgent.addBehaviour(new EcouteCreneaux());
		
	}

	/**comportement de recherche cooperative de solution
	 * A MODIFIER !!! car placement "non intelligent"*/
	public void action() {
		//recuperation de la vue locale
		int[][][]localView = monAgent.getLocalView();

		//preparation du message à transmettre aux voisins
		ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
		msg.setConversationId("recevoirCreneaux");
		AID[] equipesVoisines = monAgent.getEquipesVoisines();
		for(int i=0; i<equipesVoisines.length; i++)
			msg.addReceiver(equipesVoisines[i]);

		//recherche de solution.. attention, il faut réécrire pour un vrai backtracking !!
		for(int j=0; j<2; j++)
		{
			for(int creneau=0; creneau<4 ; )
			{
				for(int client=0, nbPlacements=0; client<3 && nbPlacements<2; client++)
				{
					if(localView[j][client][creneau]==0)
					{
						StringBuffer sb = new StringBuffer();
						localView[j][client][creneau] = monAgent.getNoEquipe();
						//msg = no equipe -  no jour -  no client - no creneau
						sb.append( monAgent.getNoEquipe()).append("-").append(j).append("-").append(client).append("-").append(creneau).append("-");
						msg.setContent(sb.toString());
						monAgent.println("j'envoie " + sb.toString());
						monAgent.send(msg);
						nbPlacements++;
						creneau++;
					}
				}
			}
		}
		

	}

	@Override
	public boolean done() {
		//definir ici quand retourner vrai (quand les agents sont tous bien placés ou si aucune solution n'existe)
		return true;
	}

	
}
