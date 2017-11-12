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
@RequestMapping("/api/call")
public class CallRestController {

    private final CallDispatcher dispatchCall;
    private final List<Call> finalizeCalls;

    public CallRestController(CallDispatcher dispatchCall, List<Call> finalizeCalls) {
        this.dispatchCall = dispatchCall;
        this.finalizeCalls = finalizeCalls;
    }

    @GetMapping
    public List<Call> getAll() {
        return finalizeCalls;
    }

    @PostMapping
    public Call save(@RequestBody Call call) {
        dispatchCall.dispatchCall(call);
        return call;
    }

}
