package com.robertfenyiner.latstream.resilience;

@FunctionalInterface
public interface RetryExecutor<T> {

    T run() throws Exception;

}
