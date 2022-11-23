package org.mapleleaf.backend.amqp;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapleleaf.backend.dto.ToJudgeDto;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
@RequiredArgsConstructor
@Slf4j
public class AmqpSender {
    private final RabbitTemplate template;
    private final Queue toRustQueue;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void send(ToJudgeDto dto) {
        String result;
        try {
            result = objectMapper.writeValueAsString(dto);
            this.template.convertAndSend(toRustQueue.getName(), result);
            log.info(result);
        } catch (JsonProcessingException e){
            log.error("객체 직렬화 실패");
        }
    }}
