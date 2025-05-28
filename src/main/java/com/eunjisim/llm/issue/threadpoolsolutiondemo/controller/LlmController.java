package com.eunjisim.llm.issue.threadpoolsolutiondemo.controller;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class LlmController {

    private final ExecutorService blockingExecutor = Executors.newFixedThreadPool(2);

    @GetMapping("/api/llm/call")
    public CompletableFuture<String> callLlm() {
        return CompletableFuture.supplyAsync(() -> {
            simulateLlm("기존 방식 (병목) 호출");
            return "기존 LLM 응답 완료";
        }, blockingExecutor);
    }

    @Async("llmExecutor")
    @GetMapping("/api/llm/call-optimized")
    public CompletableFuture<String> callLlmOptimized() {
        simulateLlm("개선된 방식 (ThreadPoolTaskExecutor) 호출");
        return CompletableFuture.completedFuture("최적화된 LLM 응답 완료");
    }
    private void simulateLlm(String label) {
        try {
            System.out.println("[START] " + label);
            Thread.sleep(3000);
            System.out.println("[END]  " + label);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    
}
