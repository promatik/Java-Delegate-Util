package pt.promatik;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

import pt.promatik.utils.Delegate;
import pt.promatik.utils.Utils;

public class Main {

	public static void main(String[] args) {
		new Main();
	}

	public Main() {
		Utils.log("Utils test");
		Utils.nanoTime();

		Delegate.run(() -> runEveryTenSeconds(), 10, 10, TimeUnit.SECONDS);
		Delegate.run(() -> runAfterFiveSeconds(), 5, TimeUnit.SECONDS);
		Delegate.run(() -> runAnErrorAfterSevenSeconds(), 7, TimeUnit.SECONDS);
		Delegate.run(() -> runEveryMinute(), 1, TimeUnit.MINUTES);

		// Calendar for tonight
		Calendar cal = GregorianCalendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, 1); // Tomorrow
		cal.set(Calendar.HOUR_OF_DAY, 0); // Midnight
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		Delegate.run(() -> Utils.log("Every 2 hours after midnight"), cal, 2, TimeUnit.HOURS);

		Utils.log(Utils.nanoTime("to initialize app"));
	}

	private void runEveryTenSeconds() {
		Utils.log("10 seconds");
	}

	private void runAfterFiveSeconds() {
		Utils.log("5 seconds");
	}

	private void runAnErrorAfterSevenSeconds() {
		Utils.error("7 seconds");
	}

	private void runEveryMinute() {
		Utils.log("1 minute");
	}
}
