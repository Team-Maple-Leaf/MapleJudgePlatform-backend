package org.mapleleaf.backend.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 체점 상태
 * @see #ACCEPTED
 * @see #PROCEEDING
 * @see #WRONG_ANSWER
 * @see #TIME_LIMIT_EXCEEDED
 * @see #MEMORY_LIMIT_EXCEEDED
 * @see #COMPILE_ERROR
 * @see #RUNTIME_ERROR
 */
public enum Result {
    /**
     * 정답
     */
    @JsonProperty("Accepted")
    ACCEPTED,
    /**
     * 체점중
     */
    @JsonProperty("Proceeding")
    PROCEEDING,
    /**
     * 오답
     */
    @JsonProperty("WrongAnswer")
    WRONG_ANSWER,
    /**
     * 시간초과
     */
    @JsonProperty("TimeLimitExceeded")
    TIME_LIMIT_EXCEEDED,
    /**
     * 메모리 초과
     */
    @JsonProperty("MemoryLimitExceeded")
    MEMORY_LIMIT_EXCEEDED,
    /**
     * 컴파일 에러
     */
    @JsonProperty("CompileError")
    COMPILE_ERROR,
    /**
     * 런타임 에러
     */
    @JsonProperty("RuntimeError")
    RUNTIME_ERROR;
}
