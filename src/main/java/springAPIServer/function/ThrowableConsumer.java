package springAPIServer.function;

// 例外を投げられるConsumer
public interface ThrowableConsumer<T> {
	public void accept(T t)throws Exception;
}
