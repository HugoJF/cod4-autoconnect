public class Server {
	private String port;
	private String ip;

	public void validate() {

	}

	public void loadFromIp(String userInput) {
		String[] split = userInput.split(":");
		this.ip = split[0];
		this.port = split[1];
	}

	public boolean isValid() {
		if (this.port == null || this.port.isEmpty()) {
			return false;
		}
		if (this.ip == null || this.ip.isEmpty()) {
			return false;
		}
		return true;
	}

	public Object getIp() {
		return this.ip;
	}

	public String getPort() {
		return this.port;
	}

}
