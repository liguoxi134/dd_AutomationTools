package com.dangdang.tools.atf.utilities.data;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.JobListener;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

public class HttpJobListener implements JobListener {

	private int repeatCount = 0;

	@Override
	public String getName() {
		return "__HttpJobListener__";
	}

	@Override
	public void jobToBeExecuted(JobExecutionContext context) {
		// TODO Auto-generated method stub

	}

	@Override
	public void jobExecutionVetoed(JobExecutionContext context) {
		// TODO Auto-generated method stub

	}

	@Override
	public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
		JobKey jobKey = context.getJobDetail().getKey();
		Scheduler scheduler =null;
		try {
			scheduler =StdSchedulerFactory.getDefaultScheduler();
			if (context.getNextFireTime() == null) {
				scheduler.deleteJob(jobKey);
			}
		}
		catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	public int getRepeatCount() {
		return repeatCount;
	}

	public void setRepeatCount(int repeatCount) {
		this.repeatCount = repeatCount;
	}

}
