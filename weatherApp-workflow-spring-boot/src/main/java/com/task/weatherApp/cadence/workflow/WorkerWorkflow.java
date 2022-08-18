package com.task.weatherApp.cadence.workflow;

import com.uber.cadence.workflow.WorkflowMethod;

public interface WorkerWorkflow {
    String TASK_LIST = "postWeather";

    @WorkflowMethod(executionStartToCloseTimeoutSeconds = 10, taskList = TASK_LIST)
    void postWeather(String city, Double temperature);
}

