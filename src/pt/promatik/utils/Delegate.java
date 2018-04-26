package pt.promatik.utils;

import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Delegate {
	private long startTime, cancelTime = 0;
	private ScheduledExecutorService executor = null;
	private ScheduledFuture<?> task;

	private Runnable runnable;
	private long delayInt;
	private Calendar delayDate;
	private long period;
	private TimeUnit unit;

	public Delegate(Runnable runnable, long delay) {
		this(runnable, delay, 0);
	}

	public Delegate(Runnable runnable, long delay, TimeUnit unit) {
		this(runnable, delay, 0, unit);
	}

	public Delegate(Runnable runnable, Calendar delay) {
		this(runnable, delay, 0);
	}

	public Delegate(Runnable runnable, long delay, long period) {
		this(runnable, delay, period, TimeUnit.MILLISECONDS);
	}

	public Delegate(Runnable runnable, long delay, long period, TimeUnit unit) {
		_delegate(runnable, delay, null, period, unit);
	}

	public Delegate(Runnable runnable, Calendar delay, long period) {
		this(runnable, delay, period, TimeUnit.MILLISECONDS);
	}

	public Delegate(Runnable runnable, Calendar delay, long period, TimeUnit unit) {
		_delegate(runnable, 0, delay, period, unit);
	}

	private void _delegate(Runnable runnable, long delayInt, Calendar delayDate, long period, TimeUnit unit) {
		this.runnable = runnable;
		this.delayInt = delayInt;
		this.delayDate = delayDate;
		this.period = period;
		this.unit = unit;
	}

	public void run() {
		if (executor != null && !executor.isTerminated())
			cancel();

		executor = Executors.newScheduledThreadPool(2);

		try {
			// Save start time
			startTime = System.nanoTime();

			if (period > 0) {
				if (delayDate != null)
					task = executor.scheduleAtFixedRate(runnable, delayDate.getTimeInMillis() - System.currentTimeMillis(), period, TimeUnit.MILLISECONDS);
				else
					task = executor.scheduleAtFixedRate(runnable, delayInt, period, unit);
			}
			else {
				Runnable runAndShutdown = () -> {
					runnable.run();
					executor.shutdown();
				};

				if (delayDate != null)
					task = executor.schedule(runAndShutdown, delayDate.getTimeInMillis() - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
				else
					task = executor.schedule(runAndShutdown, delayInt, unit);
			}
		}
		catch (Exception e) {
			System.err.println("Delegate timer run exception");
		}
	}

	public void cancel() {
		executor.shutdown();
		task.cancel(true);
		cancelTime = System.nanoTime();
	}

	public int getElapsedTime() {
		return Math.round(((cancelTime == 0 ? System.nanoTime() : cancelTime) - startTime) / 1000000f);
	}

	public static Delegate run(Runnable runnable, long delay) {
		Delegate t = new Delegate(runnable, delay);
		t.run();
		return t;
	}

	public static Delegate run(Runnable runnable, long delay, TimeUnit unit) {
		Delegate t = new Delegate(runnable, delay, unit);
		t.run();
		return t;
	}

	public static Delegate run(Runnable runnable, long delay, long period) {
		Delegate t = new Delegate(runnable, delay, period);
		t.run();
		return t;
	}

	public static Delegate run(Runnable runnable, long delay, long period, TimeUnit unit) {
		Delegate t = new Delegate(runnable, delay, period, unit);
		t.run();
		return t;
	}

	public static Delegate run(Runnable runnable, Calendar time) {
		Delegate t = new Delegate(runnable, time);
		t.run();
		return t;
	}

	public static Delegate run(Runnable runnable, Calendar time, long period) {
		Delegate t = new Delegate(runnable, time, period);
		t.run();
		return t;
	}

	public static Delegate run(Runnable runnable, Calendar time, long period, TimeUnit unit) {
		Delegate t = new Delegate(runnable, time, period, unit);
		t.run();
		return t;
	}
}
