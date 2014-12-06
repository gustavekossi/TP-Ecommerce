package launch;



public class LaunchSimu {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// ******************JADE******************
		String[] jadeArgs = new String[2];
		StringBuffer sbAgents = new StringBuffer();
		sbAgents.append("e1:agents.AgentEquipe(1,webService)").append(";");
		sbAgents.append("e2:agents.AgentEquipe(2,interaction)").append(";");
		sbAgents.append("e3:agents.AgentEquipe(3,database);").append(";");
		sbAgents.append("c1:agents.AgentClient(1,1,4,2,3)").append(";");
		sbAgents.append("c2:agents.AgentClient(2,2,2,1,4)").append(";");
		sbAgents.append("c3:agents.AgentClient(3,1,3,2,1)");
		
		jadeArgs[0] = "-gui";
		jadeArgs[1] = sbAgents.toString();

		jade.Boot.main(jadeArgs);
	}

}
