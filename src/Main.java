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

		if (!aco.loadedOptionsFromFile()) {
			aco.createDefaultOptionsFile();
		}
		cod4l.setPath(sip.getCod4Path());
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);

		// get previous connected servers
		ArrayList<String> previousServers = aco.getPreviousConnectedServers();
		if (previousServers != null) {
			for (int i = 0; i < previousServers.size(); i++) {
				System.out.println((i + 1) + ". " + previousServers.get(i));
			}
		}
		// get what server id or ip user wants to connect
		while (true) {
			System.out.println("Please inform the server IP and PORT. ex.: 123.123.123.123:1234");
			String userInput = scanner.nextLine();
			System.out.println("Scanning the ip");
			sv.loadFromIp(userInput);
			System.out.println("Loading the ip");
			sip.loadFromServer(sv);
			// validate informations
			System.out.println("Validating server ip");
			sv.validate();
			if (sv.isValid() && sip.isQueryInformationValid(sv)) {
				System.out.println("Server info is valid.");
				break;
			}
		}
		if (aco.isSavingServersToFile()) {
			aco.saveServerToFile(sv);
		}

		while (true) {
			// loop getting informations if valid
			System.out.println("###############");
			System.out.println("Loading server");
			sip.loadFromServer(sv);
			System.out.println("Refreshing info");
			sip.refreshInfo();
			if (!sip.hasTimedOut()) {
				System.out.println("Parsing info");
				sip.parseInfo();
				// once empty slot connect
				if (sip.hasEmptySlot()) {
					System.out.println("##Empty slot found");
					// cod4l.launchOnServer(sv);
					System.exit(0);
				} else {
					System.out.println("##SERVER IS FULL");
				}
			}
		}
	}
}
