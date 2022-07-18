package agents;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.core.behaviours.WakerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.wrapper.ControllerException;

public class ConsumerAgent extends Agent {
	
	@Override
	protected void setup() {
		String bookName = null;
		if(this.getArguments().length==1) {
			bookName=(String)this.getArguments()[0];
		}
		System.out.println("Initialisation de l'agent"+ this.getAID().getName());
		System.out.println("I'm trying to buy the book"+bookName);
		
		ParallelBehaviour parallelBehaviour= new ParallelBehaviour();
		addBehaviour(parallelBehaviour);
		/*
		 * addBehaviour(new Behaviour() { private int counter=0;
		 * 
		 * @Override public void action() { System.out.println("---------------");
		 * System.out.println("Step "+counter); System.out.println("---------------");
		 * ++counter; }
		 * 
		 * @Override public boolean done() {
		 * 
		 * return (counter==10); }
		 * 
		 * });
		 */
		
		  parallelBehaviour.addSubBehaviour(new OneShotBehaviour() {
		 
		  @Override public void action() {
			  System.out.println("One Shot behaviour");
		  }
		 
		  });
		 /* 
		 * }); addBehaviour(new CyclicBehaviour() { private int counter=0;
		 * 
		 * @Override public void action() { System.out.println("Counter =>"+counter);
		 * ++counter; }
		 * 
		 * });
		 */
		/*
		 * addBehaviour(new TickerBehaviour(this, 1000) {
		 * 
		 * @Override protected void onTick() {
		 * 
		 * System.out.println("Tic ");
		 * System.out.println(myAgent.getAID().getLocalName()); }
		 * 
		 * });
		 */
		SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy:HH:mm");
		Date date = null;
		try {
			date = dateFormat.parse("17/07/2022:18:06");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		parallelBehaviour.addSubBehaviour(new WakerBehaviour(this,date) {
			@Override
			protected void onWake() {
				System.out.println("Waker Behaviour......");
			}
		});
		
		parallelBehaviour.addSubBehaviour(new CyclicBehaviour() {

			@Override
			public void action() {
				MessageTemplate messageTemplate=MessageTemplate.and(
						MessageTemplate.MatchPerformative(ACLMessage.CFP),
						MessageTemplate.MatchLanguage("fr")
						);
						
				ACLMessage aclMessage=receive(messageTemplate);
				if(aclMessage!=null) {
					System.out.println("Sender :"+aclMessage.getSender().getName());
					System.out.println("Content :"+aclMessage.getContent());
					System.out.println("SpeechAct :"+aclMessage.getPerformative());
					
					ACLMessage replay=new ACLMessage(ACLMessage.CONFIRM);
					replay.addReceiver(aclMessage.getSender());
					replay.setContent("Pice=900");
					send(replay);
				}else {
					System.out.println("Bloc....");
					block();
				};
			}
			
		});
	}
	
	@Override
	protected void beforeMove() {
		try {
			System.out.println("Before Migration"+this.getContainerController().getContainerName());
		} catch (ControllerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("...........................");
	}
	@Override
	protected void afterMove() {
		try {
			System.out.println("After Migration"+this.getContainerController().getContainerName());
		} catch (ControllerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("...........................");
	}
	
	@Override
	protected void takeDown() {
		System.out.println("I m Going to die....");
	}
}
