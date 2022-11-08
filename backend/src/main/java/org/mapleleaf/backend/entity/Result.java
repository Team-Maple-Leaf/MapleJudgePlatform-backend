package org.mapleleaf.backend.entity;

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
    ACCEPTED,
    /**
     * 체점중
     */
    PROCEEDING,
    /**
     * 오답
     */
    WRONG_ANSWER,
    /**
     * 시간초과
     */
    TIME_LIMIT_EXCEEDED,
    /**
     * 메모리 초과
     */
    MEMORY_LIMIT_EXCEEDED,
    /**
     * 컴파일 에러
     */
    COMPILE_ERROR,
    /**
     * 런타임 에러
     */
    RUNTIME_ERROR;
}
