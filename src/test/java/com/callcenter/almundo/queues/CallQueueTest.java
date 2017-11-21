package com.callcenter.almundo.queues;

import com.callcenter.almundo.domain.Call;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

public class CallQueueTest {

    @Test
    public void add_withQueueEmpty_returnCall() {
        CallQueue callQueue = new CallQueue(1);
        Call call = new Call(1L);
        Call newCall = callQueue.add(call);
        assertThat(newCall).isEqualTo(call);
    }

    @Test
    public void add_withFullQueue_returnCallWithStandByTrue() {
        CallQueue callQueue = new CallQueue(1);
        Call call = new Call(1L);
        callQueue.add(call);
        Call otherCall = new Call(2L);
        Call newCall = callQueue.add(otherCall);

        assertThat(newCall).isEqualToIgnoringGivenFields(otherCall, "wasInStandBy").hasFieldOrPropertyWithValue("wasInStandBy", true);
    }

    @Test
    public void attend_withQueueWithOnlyOneElement_returnCall() throws InterruptedException {
        CallQueue callQueue = new CallQueue(1);
        Call call = new Call(1L);
        callQueue.add(call);
        Call newCall = callQueue.attend();
        assertThat(callQueue.getCallsInProgress().get()).isEqualTo(1);
        assertThat(newCall).isEqualTo(call);
    }   

    @Test
    public void attend_withQueueWithElementsInStandBy_returnCallWithStandBy() throws InterruptedException {
        CallQueue callQueue = new CallQueue(1);
        Call call = new Call(1L);
        callQueue.add(call);
        Call otherCall = new Call(2L);
        callQueue.add(otherCall);
        callQueue.attend();
        Call newCall = callQueue.attend();
        assertThat(newCall).isEqualToIgnoringGivenFields(otherCall, "wasInStandBy").hasFieldOrPropertyWithValue("wasInStandBy", true);
    }
}
