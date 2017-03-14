package pt.promatik;

import pt.promatik.utils.Delegate;
import pt.promatik.utils.Utils;

public class Main {
	
	public static void main(String[] args) {
		new Main();
	}
	
	public Main() {
		Utils.log("Utils test");
		Utils.nanoTime();

		Delegate.run(() -> runEveryTenSeconds(), 10000, 10000);
		Delegate.run(() -> runInFiveSeconds(), 5000);
		Delegate.run(() -> runAnErrorEverySevenSeconds(), 7000);
		
		Utils.log(Utils.nanoTime("to initialize app"));
	}
	
	private void runEveryTenSeconds() {
		Utils.log("10 seconds");
	}
	
	private void runInFiveSeconds() {
		Utils.log("5 seconds");
	}
	
	private void runAnErrorEverySevenSeconds() {
		Utils.error("7 seconds");
	}
}
