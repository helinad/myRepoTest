package com.constant_therapy.processor;

import com.constant_therapy.util.Constants;

public class ProcessorFactory {

	public static Processor createProcessor(int action)
			throws UnsupportedOperationException {

		switch (action) {

		case Constants.LOGIN_TOKEN:
		case Constants.ACCESS_TOKEN_LOGIN_TOKEN:
			return new LoginProcessor();
		case Constants.DASHBOARD_TOKEN:
			return new DashboardProcessor();
		case Constants.GET_PATIENT_LIST_TOKEN :
			return new PatientListProcessor();
		case Constants.GET_PATIENT_IMAGEPATH_TOKEN :
			return new PatientImagepathProcessor();
		case Constants.CLINICIAN_MIDDLE_ROW_TOKEN :
			return new ClinicianMiddleRowProcessor();
		case Constants.CLINICIAN_PLOT_ACCURACY_TOKEN :
			return new CombinedPlotProcessor();
		case Constants.CLINICIAN_PLOT_LATENCY_TOKEN :
			return new ComplianceProcessor();
		case Constants.CLINICIAN_TASKS_HIERARCHY_TOKEN :
			return new TasksProcessor();
		case Constants.CLINICIAN_TASKS_HIERARCHY_SCORES_TOKEN :
			return new TaskScoreProcessor();
		case Constants.CLINICIAN_TASKS_HOMEWORK_TOKEN :
			return new TasksHomeworkProcessor();
		case Constants.CLINICIAN_TASKS_HOMEWORK_RESTORE_TOKEN :
			return new TasksHomeworkProcessor();
		case Constants.HELP_OVERLAY_TOKEN :
			return new HelpOverlayProcessor();
		case Constants.DOING_TASKS_TOKEN :
			return new DoingTaskProcessor();
		case Constants.DELETE_TOKEN :
			return new DoingTaskProcessor();
		case Constants.SUMMARY_RESPONSE_TOKEN :
			return new TasksProcessor();
		case Constants.MULIT_TOKEN :
			return new DoingTaskProcessor();
		case Constants.PREFERENCE_TOKEN :
			return new SessionProcessor();
		case Constants.TASK_BASELINE_TOKEN :
			return new BaselineProcessor();
		case Constants.VALIDATE_EMAIL_TOKEN:
			return new SessionProcessor();
		case Constants.UPDATE_ACCOUNT_TOKEN :
			return new BaselineProcessor();
		case Constants.CHANGE_PASSWORD_TOKEN :
			return new BaselineProcessor();

		default:
			throw new UnsupportedOperationException(
					"No processor was found to match the requested URI. "
							+ action);

		}

	}

}
