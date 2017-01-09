package com.dangdang.tools.atf.pages;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dangdang.tools.atf.helper.JobHelper;
import com.dangdang.tools.atf.models.JobMetaData;
import com.dangdang.tools.atf.utilities.data.KeyValueMap;

public class JobPage extends BasePage {
	public static void Schedule(HttpServletRequest request, HttpServletResponse response) {
		Redirect(request, response, "/pages/mgmt/job/schedule.jsp");
	}

	@SuppressWarnings("unused")
	public static void List(HttpServletRequest request, HttpServletResponse response) {
		List<JobMetaData> joblist = JobHelper.JOBLIST;

	}

	public static void Create(HttpServletRequest request, HttpServletResponse response) {
		try {
			String jobName = getString(request, "");
			String jobDesc = getString(request, "");
			String _startTime = getString(request, "");
			Date startTime = new Date(Long.parseLong(_startTime));
			String groupName = UUID.randomUUID().toString();
			String cornExp = null;
			String occurType = getString(request, "");

			KeyValueMap<String, String> parameters = new KeyValueMap<String, String>();
			String rid = getString(request, "rid");
			parameters.set("rid", rid);
			String ids = getString(request, "ids");
			parameters.set("ids", ids);

			if (occurType.equalsIgnoreCase("one")) {
				SimpleDateFormat sdf = new SimpleDateFormat("ss mm HH dd MM ? yyyy");
				cornExp = sdf.format(startTime);
				JobHelper.buildJob(jobName, groupName, jobDesc, cornExp, startTime, null, parameters);
			} else if (occurType.equalsIgnoreCase("multiple")) {
				String occurMode = getString(request, "occurMode");

				SimpleDateFormat sdf = new SimpleDateFormat("ss mm HH");
				cornExp = sdf.format(startTime);
				if (occurMode.equalsIgnoreCase("byDay")) {
					String during = getString(request, "during");
					if (during != null && !during.isEmpty()) {
						// 1. "0 0 0 1/n * ?"每n天
						cornExp += " 1/" + during + " * ?";
					} else {
						// 2. "0 0 0 ? * MON-FRI"每隔工作日
						cornExp += " ? * MON-FRI";
					}

				} else if (occurMode.equalsIgnoreCase("byWeek")) {
					String weeks = getString(request, "weeks");
					cornExp += " ? * " + weeks;
				} else if (occurMode.equalsIgnoreCase("byMonth")) {
					String during = getString(request, "during");
					String theCount = getString(request, "theCount");
					if (theCount != null && !theCount.isEmpty()) {
						// 4. "0 0 0 18 /2 ? *"每2个月的18号
						cornExp += " " + theCount + " /" + during + " ? *";
					} else {
						// 5. "0 0 0 ? /2 6L(6#3)"每2个月的第3(最后一)个周五
						String weekOfMonth = getString(request, "weekOfMonth");
						String weekEx = getString(request, "weekEx");
						if (weekOfMonth.equalsIgnoreCase("L")) {
							cornExp += " ? /" + during + " " + weekEx + weekOfMonth;
						} else {
							cornExp += " ? /" + during + " " + weekEx + "#" + weekOfMonth;
						}
					}
				} else if (occurMode.equalsIgnoreCase("byYear")) {
					String during = getString(request, "during");
					String month = getString(request, "month");
					String theCount = getString(request, "theCount");
					if (theCount != null && !theCount.isEmpty()) {
						// 6. 0 0 0 17 3 ? /n 每n年的3/17
						cornExp += " " + theCount + " " + month + " ? /" + during;
					} else {
						// 7. 0 0 0 ? 3 6#1(6L) /2每2年的3月的第1(最后一)个周五
						String weekOfMonth = getString(request, "weekOfMonth");
						String weekEx = getString(request, "weekEx");
						if (weekOfMonth.equalsIgnoreCase("L")) {
							cornExp += " ? " + month + " " + weekEx + weekOfMonth + " /" + during;
						} else {
							cornExp += " ?　" + month + " " + weekEx + "#" + weekOfMonth + " /" + during;
						}
					}
				}
				String finishType = getString(request, "finishType");
				if (finishType.equalsIgnoreCase("custom")) {
					System.out.println("custom");
					String _endTime = getString(request, "endTime");
					Date endTime = new Date(Long.parseLong(_endTime));
					JobHelper.buildJob(jobName, groupName, jobDesc, cornExp, startTime, endTime, parameters);
				} else if (finishType.equalsIgnoreCase("time")) {
					System.out.println("time");
					String _endTime = getString(request, "endTime");
					int time = Integer.parseInt(_endTime);
					JobHelper.buildJob(jobName, groupName, jobDesc, cornExp, startTime, time, parameters);
				} else {
					System.out.println("forever");
					JobHelper.buildJob(jobName, groupName, jobDesc, cornExp, startTime, null, parameters);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
