package com.task.weatherApp.cadence.workflow;


import com.task.weatherApp.cadence.activities.ActivitiesImpl;
import com.task.weatherApp.cadence.activities.GetWeatherRespondActivity;
import com.task.weatherApp.cadence.activities.LaunchPostWeatherWorkflowActivity;
import com.uber.cadence.client.WorkflowClient;
import com.uber.cadence.client.WorkflowClientOptions;
import com.uber.cadence.serviceclient.ClientOptions;
import com.uber.cadence.serviceclient.WorkflowServiceTChannel;
import com.uber.cadence.worker.Worker;
import com.uber.cadence.worker.WorkerFactory;
import com.uber.cadence.workflow.Workflow;


@SuppressWarnings("ALL")
public class WorkflowWorkerGetWeather {

    private static final String TASK_LIST = "getWeather";
    private static final String DOMAIN = "weather-domain";

    private final ActivitiesImpl activities = new ActivitiesImpl();

    public Double workflowWorkerLaunch(String city) {
        WorkflowClient workflowClient =
                WorkflowClient.newInstance(
                        new WorkflowServiceTChannel(ClientOptions.defaultInstance()),
                        WorkflowClientOptions.newBuilder().setDomain(DOMAIN).build());
        WorkerFactory factory = WorkerFactory.newInstance(workflowClient);
        Worker worker = factory.newWorker(TASK_LIST);
        worker.registerWorkflowImplementationTypes(WorkerWorkflowImpl.class);
        worker.registerActivitiesImplementations(activities);
        factory.start();

        WorkerWorkflow workflow = workflowClient.newWorkflowStub(WorkerWorkflow.class);
        Double temp = workflow.getWeather(city);
        return temp;
    }

    public static class WorkerWorkflowImpl implements WorkerWorkflow {

        private final GetWeatherRespondActivity getWeatherRespondActivity = Workflow.newActivityStub(GetWeatherRespondActivity.class);
        private final LaunchPostWeatherWorkflowActivity launchPostWeatherWorkflowActivity = Workflow.newActivityStub(LaunchPostWeatherWorkflowActivity.class);

        @Override
        public Double getWeather(String city) {
            Double temp = getWeatherRespondActivity.getResponse(city);
            launchPostWeatherWorkflowActivity.launchWorkflow(city, temp);
            return temp;
        }
    }
}
