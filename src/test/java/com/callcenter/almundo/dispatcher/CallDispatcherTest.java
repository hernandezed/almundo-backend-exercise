package com.callcenter.almundo.dispatcher;

import com.callcenter.almundo.CallCenterAbstractTest;
import com.callcenter.almundo.domain.Call;
import java.util.ArrayList;
import java.util.List;
import javax.jms.Queue;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jms.core.JmsMessagingTemplate;

@PrepareForTest(JmsMessagingTemplate.class)
public class CallDispatcherTest extends CallCenterAbstractTest {

    @Autowired
    @InjectMocks
    private CallDispatcher callDispatcher;

    @MockBean
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Before
    public void setUp() {
        doNothing().when(jmsMessagingTemplate).convertAndSend(any(Queue.class), any(Call.class));
        callDispatcher.clean();
    }

    @Test
    public void dispatchCall_dispatchOneCallWithEmptyQueue_modifyCall() {
        Call call = new Call(1l);
        callDispatcher.dispatchCall(call);
        assertThat(call).hasFieldOrPropertyWithValue("wasInStandBy", false);
        verify(jmsMessagingTemplate, times(1)).convertAndSend(any(Queue.class), any(Call.class));
    }

    @Test
    public void dispatchCall_dispatchElevenCallsWithEmptyQueue_modifyCallSetOneInStandBy() throws InterruptedException {
        List<Call> calls = new ArrayList();
        for (int i = 1; i <= 11; i++) {
            Call call = new Call(i);
            calls.add(call);
            callDispatcher.dispatchCall(call);
        }
        assertThat(calls).extracting("wasInStandBy").last().isEqualTo(true);
        verify(jmsMessagingTemplate, times(11)).convertAndSend(any(Queue.class), any(Call.class));
    }
}
