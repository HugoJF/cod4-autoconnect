import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ServerInformationParser {
	private String cod4Path;
	private Server server;
	private int currentClients;
	private int maxClients;
	private String rawInformation;

	public String getCod4Path() {
		return this.cod4Path;
	}

	public boolean isQueryInformationValid(Server sv) {
		loadFromServer(server);
		String temp = "";
		try {
			temp = queryServer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (temp.contains("disconnected")) {
			return false;
		}
		return true;

	}

	public void loadFromServer(Server sv) {
		this.server = sv;
	}

	public boolean hasEmptySlot() {
		if (this.currentClients == this.maxClients) {
			return false;
		}
		return true;
	}

	public void refreshInfo() {
		try {
			this.rawInformation = queryServer();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void parseInfo() {
		String[] temp1, temp2;
		temp1 = this.rawInformation.split("\\\\clients\\\\");
		temp2 = temp1[1].split("\\\\");
		this.currentClients = Integer.valueOf(temp2[0]);

		temp1 = this.rawInformation.split("\\\\sv_maxclients\\\\");
		temp2 = temp1[1].split("\\\\");
		this.maxClients = Integer.valueOf(temp2[0]);
	}

	private String queryServer() throws IOException {
		DatagramSocket clientSocket = new DatagramSocket();
		InetAddress IPAddress = InetAddress.getByName((String) this.server.getIp());
		byte[] sendData = new byte[1024];
		byte[] receiveData = new byte[1024];
		sendData = new byte[] { (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, 0x67, 0x65, 0x74, 0x69, 0x6E, 0x66, 0x6F, 0x20, 0x78, 0x78, 0x78 };
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, Integer.parseInt(this.server.getPort()));
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		clientSocket.send(sendPacket);
		clientSocket.receive(receivePacket);
		String modifiedSentence = new String(receivePacket.getData());
		clientSocket.close();
		return modifiedSentence;
	}

}
