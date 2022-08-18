package com.task.weatherApp.cadence.workflow;


import com.task.weatherApp.cadence.activities.ActivitiesImpl;
import com.task.weatherApp.cadence.activities.PostWeatherInfActivity;
import com.uber.cadence.client.WorkflowClient;
import com.uber.cadence.client.WorkflowClientOptions;
import com.uber.cadence.serviceclient.ClientOptions;
import com.uber.cadence.serviceclient.WorkflowServiceTChannel;
import com.uber.cadence.worker.Worker;
import com.uber.cadence.worker.WorkerFactory;
import com.uber.cadence.workflow.Workflow;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@SuppressWarnings("ALL")
public class WorkflowWorkerPostWeather {

    private static final String TASK_LIST = "postWeather";
    private static final String DOMAIN = "weather-domain";

    private final ActivitiesImpl activities;

    public void workflowWorkerLaunch(String city, Double temperature) {
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
        workflow.postWeather(city, temperature);
    }

    public static class WorkerWorkflowImpl implements WorkerWorkflow {


        private final PostWeatherInfActivity postWeatherInfActivity = Workflow.newActivityStub(PostWeatherInfActivity.class);

        @Override
        public void postWeather(String city, Double temperature) {
            postWeatherInfActivity.postWeatherInf(city, temperature);
        }
    }
}
