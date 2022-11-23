package org.mapleleaf.backend.amqp;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapleleaf.backend.config.AmpqConfig;
import org.mapleleaf.backend.dto.AnswerStatusDto;
import org.mapleleaf.backend.entity.Answer;
import org.mapleleaf.backend.entity.AnswerState;
import org.mapleleaf.backend.repository.AnswerRepository;
import org.mapleleaf.backend.repository.AnswerStatusRepository;
import org.mapleleaf.backend.service.AnswerStatusService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
@RabbitListener(queues = AmpqConfig.toSpringQueueName)
public class AmpqReceiver {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final AnswerStatusService answerStatusService;
    @RabbitHandler
    public void receive(byte[] raw) {
        try {
            AnswerStatusDto dto = objectMapper.readValue(raw, AnswerStatusDto.class);
            answerStatusService.update(dto);
            log.info(dto.toString());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
