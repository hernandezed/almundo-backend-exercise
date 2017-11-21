package com.callcenter.almundo.consumer;

import com.callcenter.almundo.CallCenterAbstractTest;
import com.callcenter.almundo.domain.Call;
import com.callcenter.almundo.domain.Employee;
import com.callcenter.almundo.domain.Operator;
import java.util.concurrent.BlockingQueue;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class DispatchToEmployeeConsumerTest extends CallCenterAbstractTest {

    @Autowired
    private DispatchToEmployeeConsumer dispatchToEmployeeConsumer;

    @Autowired
    private BlockingQueue<Employee> employees;

    @Before
    public void setUp() {
        employees.add(new Operator("Marcos"));
    }

    @Test
    public void dispatchToEmployee_withValidCall_modifyCall() throws InterruptedException {
        Call call = new Call(1);
        call.setWasStandBy(false);
        dispatchToEmployeeConsumer.dispatchToEmployee(call);
        assertThat(call).hasNoNullFieldsOrProperties();
    }

}
