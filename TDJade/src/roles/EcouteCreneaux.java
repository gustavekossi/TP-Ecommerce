package roles;
import agents.AgentEquipe;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

@SuppressWarnings("serial")
public class EcouteCreneaux extends CyclicBehaviour{

	AgentEquipe monAgent;
	MessageTemplate mt;
	
	public void onStart()
	{
		monAgent = 	(AgentEquipe)myAgent;
		mt = MessageTemplate.MatchConversationId("recevoirCreneaux");		
	}
	
	public void action() {
		ACLMessage msg = monAgent.receive(mt);
		if(msg!=null)
		{
			String[]content = msg.getContent().split("-");
			for(int i=0; i<content.length-1; i+=4)
			{
				int equipe = Integer.parseInt(content[i]);;
				int jour = Integer.parseInt(content[i+1]);
				int client = Integer.parseInt(content[i+2]);
				int creneau = Integer.parseInt(content[i+3]);
				monAgent.addCreneau(equipe, jour, client, creneau);
				monAgent.println("recu " +msg.getContent()+" de " + msg.getSender().getLocalName());
				monAgent.afficheAgenda();
			}
		}
		else block();
	}

}
