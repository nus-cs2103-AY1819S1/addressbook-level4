package seedu.address.commons.core;

@FunctionalInterface
public interface ProcessCommand<T, R, K extends Throwable> {
    R apply(T t) throws K;
}
