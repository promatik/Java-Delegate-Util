package pt.promatik.utils;

import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Delegate
{
	private long startTime, cancelTime = 0;
	private ScheduledExecutorService executor = null;
	private ScheduledFuture<?> task; 

	public Delegate(Runnable runnable, long delay)
	{
		this(runnable, delay, 0);
	}
	
	public Delegate(Runnable runnable, long delay, TimeUnit unit)
	{
		this(runnable, delay, 0, unit);
	}

	public Delegate(Runnable runnable, Calendar delay)
	{
		this(runnable, delay, 0);
	}

	public Delegate(Runnable runnable, long delay, long period)
	{
		_delegate(runnable, delay, null, period, TimeUnit.MILLISECONDS);
	}

	public Delegate(Runnable runnable, long delay, long period, TimeUnit unit)
	{
		_delegate(runnable, delay, null, period, unit);
	}

	public Delegate(Runnable runnable, Calendar delay, long period)
	{
		_delegate(runnable, 0, delay, period, TimeUnit.MILLISECONDS);
	}

	public Delegate(Runnable runnable, Calendar delay, long period, TimeUnit unit)
	{
		_delegate(runnable, 0, delay, period, unit);
	}

	private void _delegate(Runnable runnable, long delayInt, Calendar delayDate, long period, TimeUnit unit)
	{
		if(executor != null) return;
		executor = Executors.newScheduledThreadPool(2);
		
		try {
			// Save start time
			startTime = System.nanoTime();

			if(period > 0) {
				if(delayDate != null)
					task = executor.scheduleAtFixedRate(runnable, delayDate.getTimeInMillis() - System.currentTimeMillis(), period, TimeUnit.MILLISECONDS);
				else
					task = executor.scheduleAtFixedRate(runnable, delayInt, period, unit);
			} else if(delayDate != null)
				task = executor.schedule(runnable, delayDate.getTimeInMillis() - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
			else
				task = executor.schedule(runnable, delayInt, unit);
		} catch (Exception e) {
			Utils.log("Delegate timer run exception", e);
		}
	}

	public void cancel()
	{
		executor.shutdown();
		task.cancel(true);
		cancelTime = System.nanoTime();
	}

	public int elapsedTime() {
		return Math.round(((cancelTime == 0 ? System.nanoTime() : cancelTime) - startTime) / 1000000f);
	}

	public static Delegate run(Runnable runnable, long delay)
	{
		return new Delegate(runnable, delay);
	}

	public static Delegate run(Runnable runnable, long delay, TimeUnit unit)
	{
		return new Delegate(runnable, delay, unit);
	}

	public static Delegate run(Runnable runnable, long delay, long period)
	{
		return new Delegate(runnable, delay, period);
	}

	public static Delegate run(Runnable runnable, long delay, long period, TimeUnit unit)
	{
		return new Delegate(runnable, delay, period, unit);
	}

	public static Delegate run(Runnable runnable, Calendar time)
	{
		return new Delegate(runnable, time);
	}

	public static Delegate run(Runnable runnable, Calendar time, long period)
	{
		return new Delegate(runnable, time, period);
	}

	public static Delegate run(Runnable runnable, Calendar time, long period, TimeUnit unit)
	{
		return new Delegate(runnable, time, period, unit);
	}
}
