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
    @JsonProperty("Wrong Answer")
    WRONG_ANSWER,
    /**
     * 시간초과
     */
    @JsonProperty("Time Limit Exceeded")
    TIME_LIMIT_EXCEEDED,
    /**
     * 메모리 초과
     */
    @JsonProperty("Memory Limit Exceeded")
    MEMORY_LIMIT_EXCEEDED,
    /**
     * 컴파일 에러
     */
    @JsonProperty("Compile Error")
    COMPILE_ERROR,
    /**
     * 런타임 에러
     */
    @JsonProperty("Runtime Error")
    RUNTIME_ERROR;
}
