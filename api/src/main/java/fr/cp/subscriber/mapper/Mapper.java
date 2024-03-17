package fr.cp.subscriber.mapper;

/**
 *
 * @param <T> Entity
 * @param <V> Request
 * @param <I> Response
 */
public interface Mapper<T, V, I> {
    T mapToEntity(V request, MapperPatten pattern);
    I mapToResponse(T entity);
}
