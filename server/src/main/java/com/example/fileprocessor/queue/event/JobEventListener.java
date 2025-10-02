package com.example.fileprocessor.queue.event;

import com.example.fileprocessor.queue.MessageProducer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@AllArgsConstructor
public class JobEventListener {
    private final MessageProducer messageProducer;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onJobCreated(JobCreatedEvent event) {
        messageProducer.sendMessage("job:" + event.getJobKey());
    }
}
