package springAPIServer.function;

// 例外を投げられるPredicate
public interface ThrowablePredicate<T> {
	public boolean test(T t) throws Exception; 
}
