package pt.promatik.utils;

import java.util.Timer;
import java.util.TimerTask;

public class Delegate
{
	private Timer timer = null;
	
	public Delegate(Runnable runnable, int delay)
	{
		this(runnable, delay, 0);
	}
	
	public Delegate(Runnable runnable, int delay, int period)
	{
		timer = null;
	    try {
			timer = new Timer();
			TimerTask t = new TimerTask() {
				@Override
				public void run() {
					try {
						runnable.run();
					} catch (Exception e) {
						System.err.println("Delegate run exception");
						e.printStackTrace();
					}
				}
			};
			if(period > 0) {
				timer.schedule(t, delay, period);
			} else {
				timer.schedule(t, delay);
			}
		} catch (Exception e) {
			System.err.println("Delegate timer run exception");
			e.printStackTrace();
		}
	}
	
	public void cancel() {
		timer.cancel();
	}
	
	public static Delegate run(Runnable runnable, int delay)
	{
		return new Delegate(runnable, delay);
	}
	
	public static Delegate run(Runnable runnable, int delay, int period)
	{
		return new Delegate(runnable, delay, period);
	}
}
