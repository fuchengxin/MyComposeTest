package rxhttp

import kotlin.collections.List
import rxhttp.wrapper.CallFactory
import rxhttp.wrapper.coroutines.CallAwait
import rxhttp.wrapper.coroutines.CallFlow
import rxhttp.wrapper.utils.javaTypeOf

public inline fun <reified T> BaseRxHttp.executeList(): List<T> = executeClass<List<T>>()

public inline fun <reified T> BaseRxHttp.executeClass(): T = executeClass<T>(javaTypeOf<T>())

public inline fun <reified T> CallFactory.toAwaitResponse(): CallAwait<T> = toAwait(BaseRxHttp.wrapResponseParser(javaTypeOf<T>()))

public inline fun <reified T> CallFactory.toFlowResponse(): CallFlow<T> = toFlow(BaseRxHttp.wrapResponseParser(javaTypeOf<T>()))
