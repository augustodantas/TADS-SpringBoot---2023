package com.example.demo.brocker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ProcessorService {
    
    @Autowired
    private SimpMessagingTemplate template;

    public ProcessorService() {
    }

    @Async
    public void execute() {
        template.convertAndSend("/statusProcessor", "Adicionou");
    }
}