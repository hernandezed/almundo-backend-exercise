package com.callcenter.almundo.controller;

import com.callcenter.almundo.dispatcher.CallDispatcher;
import com.callcenter.almundo.domain.Call;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/calls")
public class CallRestController {

    private final CallDispatcher dispatchCall;
    private final List<Call> finishedCalls;

    public CallRestController(CallDispatcher dispatchCall, List<Call> finishedCalls) {
        this.dispatchCall = dispatchCall;
        this.finishedCalls = finishedCalls;
    }

    @GetMapping("/finished")
    public List<Call> getAllFinished() {
        return finishedCalls;
    }

    @PostMapping
    public Call save(@RequestBody Call call) {
        dispatchCall.dispatchCall(call);
        return call;
    }

}
