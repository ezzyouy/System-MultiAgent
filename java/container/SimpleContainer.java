package container;

import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.core.Profile;
import jade.core.ProfileImpl;

public class SimpleContainer {
	public static void main(String[] args) throws Exception {
		Runtime runtime=Runtime.instance();
		ProfileImpl profile=new ProfileImpl();
		profile.setParameter(Profile.MAIN_HOST,"localhost");
		AgentContainer container=runtime.createAgentContainer(profile);
		container.start();
	}
}
