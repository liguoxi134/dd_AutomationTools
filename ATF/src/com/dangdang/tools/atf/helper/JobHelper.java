package com.dangdang.tools.atf.helper;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;

import com.dangdang.tools.atf.models.JobMetaData;
import com.dangdang.tools.atf.models.LoggerObject;
import com.dangdang.tools.atf.utilities.data.HttpJob;
import com.dangdang.tools.atf.utilities.data.KeyValueMap;
import com.dangdang.tools.atf.utilities.data.KeyValuePair;

public class JobHelper  extends LoggerObject{

	public static List<JobMetaData> JOBLIST = new ArrayList<JobMetaData>();

	static {
		// init log4j properties
		File file = new File("log4j.properties");
		if (!file.exists()) {
			try {
				FileOutputStream fos = new FileOutputStream(file);
				fos.write("\nlog4j.appender.stdout=org.apache.log4j.ConsoleAppender".getBytes());
				fos.write("\nlog4j.appender.stdout.layout=org.apache.log4j.PatternLayout".getBytes());
				fos.write("\nlog4j.appender.stdout.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss} %m%n  %l%n".getBytes());
				fos.write("\nlog4j.appender.file=org.apache.log4j.FileAppender".getBytes());
				fos.write("\nlog4j.appender.file.File=log4j.log".getBytes());
				fos.write("\nlog4j.appender.file.layout=org.apache.log4j.PatternLayout".getBytes());
				fos.write("\nlog4j.appender.file.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss} %m%n  %l%n".getBytes());
				fos.write("\nlog4j.rootLogger=file".getBytes());
				fos.flush();
				fos.close();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}

		// PropertyConfigurator.configure("log4j.properties");

		// init quartz store properties
		file = new File("quartz.properties");
		if (!file.exists()) {
			try {
				FileOutputStream fos = new FileOutputStream(file);
				fos.write("org.quartz.jobStore.class=org.quartz.impl.jdbcjobstore.JobStoreTX".getBytes());
				fos.write("org.quartz.jobStore.driverDelegateClass=org.quartz.impl.jdbcjobstore.StdJDBCDelegate".getBytes());
				fos.write("org.quartz.jobStore.useProperties=false".getBytes());
				fos.write("org.quartz.jobStore.dataSource=myDS".getBytes());
				fos.write("org.quartz.jobStore.tablePrefix=QRTZ_".getBytes());
				fos.write("org.quartz.threadPool.threadCount=5".getBytes());
				fos.write("org.quartz.jobStore.isClustered=false".getBytes());
				fos.write("org.quartz.dataSource.myDS.driver=org.gjt.mm.mysql.Driver".getBytes());
				fos.write("org.quartz.dataSource.myDS.URL=jdbc:mysql://localhost:3306/quartz".getBytes());
				fos.write("org.quartz.dataSource.myDS.user=root".getBytes());
				fos.write("org.quartz.dataSource.myDS.password=liguoxi134".getBytes());
				fos.write("org.quartz.dataSource.myDS.maxConnections=20".getBytes());
				fos.flush();
				fos.close();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		// resume Job
		try {
			SchedulerFactory schedulerFactory = new StdSchedulerFactory();
			Scheduler scheduler = schedulerFactory.getScheduler();
			List<String> triggerGroups = scheduler.getTriggerGroupNames();
			for (int i = 0; i < triggerGroups.size(); i++) {
				List<String> triggers = scheduler.getTriggerGroupNames();
				for (int j = 0; j < triggers.size(); j++) {
					scheduler.resumeJob(new JobKey(triggers.get(j), triggerGroups.get(i)));
				}
			}
			scheduler.start();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void buildJob(String jobName, String groupName, String jobDesc, String cornExp, Date startTime, int repeatCount, KeyValueMap<String, String> parameters) throws Exception {

		jobName = EmptyChecker.checkEmptyWithDefaultValue(jobName, "jobName" + UUID.randomUUID());
		groupName = EmptyChecker.checkEmptyWithDefaultValue(groupName, "groupName" + UUID.randomUUID());
		startTime = EmptyChecker.checkEmptyWithDefaultValue(startTime, new Date());
		parameters = EmptyChecker.checkEmptyWithDefaultValue(parameters, new KeyValueMap<String, String>());

		CronTrigger trigger = packageTrigger(cornExp, groupName, startTime, repeatCount);
		calculateFirst10TriggerTime(startTime, trigger);

		JobDetail job = packageJob(jobName, groupName, jobDesc, parameters);

		JobMetaData jmd = new JobMetaData();
		jmd.setJobKey(job.getKey().getName());
		jmd.setTriggerKey(trigger.getKey().getName());
		jmd.setJobName(jobName);

		JOBLIST.add(jmd);

		AppendJob(job, trigger);
	}

	private static void AppendJob(JobDetail job, CronTrigger trigger) throws SchedulerException {
		Scheduler sched = new StdSchedulerFactory().getScheduler();
		sched.scheduleJob(job, trigger);
		if (!sched.isStarted()) {
			sched.start();
		}
	}

	public static void buildJob(String jobName, String groupName, String jobDesc, String cornExp, Date startTime, Date endTime, KeyValueMap<String, String> parameters) throws Exception {

		jobName = EmptyChecker.checkEmptyWithDefaultValue(jobName, "jobName" + UUID.randomUUID());
		groupName = EmptyChecker.checkEmptyWithDefaultValue(groupName, "groupName" + UUID.randomUUID());
		startTime = EmptyChecker.checkEmptyWithDefaultValue(startTime, new Date());
		parameters = EmptyChecker.checkEmptyWithDefaultValue(parameters, new KeyValueMap<String, String>());

		if (!EmptyChecker.checkEmptyWithDefaultValue(endTime)) {
			// Start Time > End Time
			if (startTime.compareTo(endTime) > 0) { throw new Exception("开始时间不能大于结束时间"); }
			// End Time < Now
			if (new Date().compareTo(endTime) > 0) { throw new Exception("结束时间不能是已经过去的时间"); }
		}

		CronTrigger trigger = packageTrigger(cornExp, groupName, startTime, endTime);
		calculateFirst10TriggerTime(startTime, trigger);

		JobDetail job = packageJob(jobName, groupName, jobDesc, parameters);

		JobMetaData jmd = new JobMetaData();
		jmd.setJobKey(job.getKey().getName());
		jmd.setTriggerKey(trigger.getKey().getName());
		jmd.setJobName(jobName);

		JOBLIST.add(jmd);
		AppendJob(job, trigger);
	}

	private static JobDetail packageJob(String jobName, String groupName, String jobDesc, KeyValueMap<String, String> parameters) {
		JobDetail job = newJob(HttpJob.class).withDescription(jobDesc).withIdentity(jobName, groupName).build();
		if (!EmptyChecker.checkEmptyWithDefaultValue(parameters)) {
			for (KeyValuePair<String, String> kvp : parameters.getData()) {
				job.getJobDataMap().put(kvp.getKey(), kvp.getValue());
			}
		}
		return job;
	}

	private static CronTrigger packageTrigger(String cornExp, String groupName, Date startTime, Date endTime) {
		TriggerBuilder<CronTrigger> tb = packageTiggerBuilder(cornExp, groupName, startTime);
		if (endTime != null) {
			tb = tb.endAt(endTime);
		}
		CronTrigger trigger = tb.build();
		return trigger;
	}

	private static CronTrigger packageTrigger(String cornExp, String groupName, Date startTime, int repeatCount) {
		TriggerBuilder<CronTrigger> tb = packageTiggerBuilder(cornExp, groupName, startTime);
		CronTrigger trigger = tb.build();
		Date a = startTime;
		for (int i = 1; i <= repeatCount; i++) {
			a = trigger.getFireTimeAfter(a);
		}
		return trigger = tb.endAt(a).build();
	}

	private static TriggerBuilder<CronTrigger> packageTiggerBuilder(String cornExp, String groupName, Date startTime) {
		TriggerBuilder<CronTrigger> tb = newTrigger().withSchedule(cronSchedule(cornExp));
		tb = tb.startAt(startTime);
		tb = tb.withIdentity(groupName + "_trigger", groupName);
		return tb;
	}

	private static void calculateFirst10TriggerTime(Date startTime, CronTrigger trigger) {
		Date a = trigger.getFireTimeAfter(startTime);
		int i = 1;
		System.out.println(trigger.getCronExpression());
		do {
			System.out.println("第" + i + "次运行：" + a);
			a = trigger.getFireTimeAfter(a);
			i++;
		}
		while (a != null && i <= 10);
	}

	public static boolean releaseJob(String jobName, String groupName) throws SchedulerException {
		Scheduler scheduler = new StdSchedulerFactory().getScheduler();
		Set<JobKey> jobKeys = scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName));

		for (JobKey jk : jobKeys) {
			if (jk.getName() == jobName) { return scheduler.deleteJob(jk); }
		}
		return false;
	}

//	public static void main(String args[]) throws Exception {
//		String jobName = "testJobName";
//		String groupName = "testGroupName";
//		String cornExp = "/5 * * * * ?";
//		JobHelper.buildJob(jobName, groupName, cornExp, new Date(), 50, null);
//	}
}
