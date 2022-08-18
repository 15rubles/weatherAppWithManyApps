package com.task.weatherApp.cadence.activities;

import com.uber.cadence.activity.ActivityMethod;

public interface LaunchPostWeatherWorkflowActivity {
    @ActivityMethod(scheduleToCloseTimeoutSeconds = 2)
    void launchWorkflow(String city, Double temp);
}

