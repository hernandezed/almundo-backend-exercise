package com.callcenter.almundo.dispatcher;

import com.callcenter.almundo.comparator.EmployeePriorityComparator;
import com.callcenter.almundo.domain.Call;
import com.callcenter.almundo.domain.Director;
import com.callcenter.almundo.domain.Employee;
import com.callcenter.almundo.domain.Operator;
import com.callcenter.almundo.domain.Supervisor;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Before;
import org.junit.Test;

public class CallDispatcherTest {

    private ExecutorService executorService;
    private CallDispatcher callDispatcher;

    @Before
    public void setUp() {
        BlockingQueue<Employee> employees = new PriorityBlockingQueue(10, new EmployeePriorityComparator());
        employees.add(new Operator("Maria"));
        employees.add(new Operator("Martin"));
        employees.add(new Operator("Luisa"));
        employees.add(new Operator("Laureano"));
        employees.add(new Operator("Carolina"));
        employees.add(new Operator("Leonardo"));
        employees.add(new Supervisor("Diego"));
        employees.add(new Supervisor("Paola"));
        employees.add(new Supervisor("Teo"));
        employees.add(new Director("Ramon"));
        executorService = Executors.newCachedThreadPool();
        callDispatcher = new CallDispatcher(executorService, employees);
    }

    @Test
    public void dispatchCall_dispatchOneCallWithEmptyQueue_modifyCall() {
        Call call = new Call(1l);
        callDispatcher.dispatchCall(call);
        assertThat(call)
                .hasNoNullFieldsOrProperties();
        assertThat(call.getDuration()).isBetween(5, 10);
    }

    @Test
    public void dispatchCall_dispatchTwoCallsWithEmptyQueue_modifyCallWithDiferentsEmployee() throws InterruptedException {
        Call call = new Call(1l);
        Call otherCall = new Call(2l);
        callDispatcher.dispatchCall(call);
        callDispatcher.dispatchCall(otherCall);

        executorService.shutdown();
        executorService.awaitTermination(2, TimeUnit.MINUTES);

        assertThat(call)
                .hasNoNullFieldsOrProperties();
        assertThat(call.getEmployee()).isNotEqualTo(otherCall.getEmployee());
        assertThat(call.getDuration()).isBetween(5, 10);
    }

    @Test
    public void dispatchCall_dispatchTenCallsWithEmptyQueue_modifyCallWithDiferentsEmployee() throws InterruptedException {
        List<Call> calls = new ArrayList();
        for (int i = 1; i <= 10; i++) {
            Call call = new Call(i);
            calls.add(call);
            callDispatcher.dispatchCall(call);
        }
        executorService.shutdown();
        executorService.awaitTermination(20, TimeUnit.SECONDS);

        assertThat(calls).extracting("employee")
                .hasAtLeastOneElementOfType(Operator.class)
                .hasAtLeastOneElementOfType(Supervisor.class)
                .hasAtLeastOneElementOfType(Director.class);
    }

    @Test
    public void dispatchCall_dispatchElevenCallsWithEmptyQueue_modifyCallSetOneInStandBy() throws InterruptedException {
        List<Call> calls = new ArrayList();
        for (int i = 1; i <= 11; i++) {
            Call call = new Call(i);
            calls.add(call);
            callDispatcher.dispatchCall(call);
        }
        executorService.shutdown();
        executorService.awaitTermination(20, TimeUnit.SECONDS);

        assertThat(calls).extracting("standBy").contains(true);
    }
}
