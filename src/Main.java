import java.util.ArrayList;
import java.util.Scanner;


public class Main {
	public static void main(String args[]) {
		new Main();
	}
	
	public Main() {
		ServerInformationParser sip = new ServerInformationParser();
		Server sv = new Server();
		AutoConnectOptions aco = new AutoConnectOptions();
		COD4Launcher cod4l = new COD4Launcher();
		aco.loadOptionsFromFile("options.xml");
		if(!aco.loadedOptionsFromFile) {
			aco.createDefaultOptionsFile();
		}
		Scanner scanner = new Scanner(System.in);
		
		//get previous connected servers
		ArrayList<String> previousServers = aco.getPreviousConnectedServers();
		for(int i = 0; i < previousServers.size(); i++) {
			System.out.println((i + 1) + ". " + previousServers.get(i));
		}
		
		//get what server id or ip user wants to connect
		while(true) {
			String userInput = scanner.nextLine();
			sv.loadFromIp(userInput);
			//validate informations
			sv.validate();
			if(sv.isValid && sip.validateInformation(sv)) {
				break;
			}
		}
		if(aco.saveServersToFile){
			aco.saveServerToFile(sv);
		}
		
		while(true) {
			//loop getting informations if valid
			sip.loadFromServer(sv);
			sip.refreshInfo();
			sip.parseInfo();
			//once empty slot connect
			if(sip.hasEmptySlot) {
				cod4l.launchOnIp(sv.getIp());
				System.exit(0);
			}
		}
		
		
		//quit program
		
	}
}
