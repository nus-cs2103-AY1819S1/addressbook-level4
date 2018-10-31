package seedu.address.commons.core;

/**
 * A Generic functional interface to be used to replace Function. Currently used in the Logic Manager's command
 * processing, hence the name. This interface is like Function with generic parameters T and R, excepts that it can
 * also throw a specific exception, K.
 * @param <T> The first parameter that is taken in
 * @param <R> The output from the method call
 * @param <K> An exception that can be thrown.
 */
@FunctionalInterface
public interface ProcessCommand<T, R, K extends Throwable> {
    R apply(T t) throws K;
}
