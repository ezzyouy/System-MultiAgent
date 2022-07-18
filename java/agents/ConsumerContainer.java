package agents;

import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.core.Profile;
import jade.core.ProfileImpl;

public class ConsumerContainer {
	public static void main(String[] args) throws Exception {
		Runtime runtime=Runtime.instance();
		ProfileImpl profile=new ProfileImpl();
		profile.setParameter(Profile.MAIN_HOST,"localhost");
		AgentContainer container=runtime.createAgentContainer(profile);
		AgentController consumerAgent=container.createNewAgent("consumer","agents.ConsumerAgent",new Object[] {"XML"});
		consumerAgent.start();
	}
}
