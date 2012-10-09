import java.io.IOException;

public class COD4Launcher {
	private String pathToCod4;

	public COD4Launcher() {
		this.pathToCod4 = "C:\\Program Files (x86)\\Steam\\steamapps\\common\\call of duty 4";
	}

	public void setPath(String cod4Path) {
		if (cod4Path != null) {
			this.pathToCod4 = cod4Path;
		}
	}

	public void launchOnServer(Server sv) {
		try {
			Runtime.getRuntime().exec("cmd.exe /C cd \"" + this.pathToCod4 + "\" && start iw3mp.exe connect " + sv.getIp() + ":" + sv.getPort());
		} catch (IOException e) {
			System.out.println("Error launching the .exe");
			e.printStackTrace();
		}
	}

}
