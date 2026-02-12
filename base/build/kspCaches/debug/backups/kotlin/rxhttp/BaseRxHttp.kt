package rxhttp

import com.chuyou.base.http.ResponseParser
import java.io.IOException
import java.lang.Class
import java.lang.reflect.Type
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List
import kotlin.jvm.Throws
import okhttp3.Response
import rxhttp.wrapper.CallFactory
import rxhttp.wrapper.ITag
import rxhttp.wrapper.parse.OkResponseParser
import rxhttp.wrapper.parse.Parser
import rxhttp.wrapper.parse.SmartParser
import rxhttp.wrapper.utils.TypeUtil
import rxhttp.wrapper.utils.parameterizedBy

/**
 * User: ljx
 * Date: 2020/4/11
 * Time: 18:15
 */
public abstract class BaseRxHttp : ITag, CallFactory {
    @Throws(IOException::class)
    public fun execute(): Response = newCall().execute()

    @Throws(IOException::class)
    public fun <T> execute(parser: Parser<T>): T = parser.onParse(execute())

    @Throws(IOException::class)
    public fun <T> executeClass(type: Type): T = execute(SmartParser.wrap(type))

    @Throws(IOException::class)
    public fun <T> executeClass(clazz: Class<T>): T = executeClass(clazz as Type)

    @Throws(IOException::class)
    public fun executeString(): String = executeClass(String::class.java)

    @Throws(IOException::class)
    public fun <T> executeList(clazz: Class<T>): List<T> {
        val typeList = List::class.parameterizedBy(clazz)
        return executeClass(typeList)
    }

    public companion object {
        @Suppress("UNCHECKED_CAST")
        public fun <T> wrapResponseParser(type: Type): Parser<T> {
            val actualType = TypeUtil.getActualType(type) ?: type
            val parser = ResponseParser<Any>(actualType)
            val actualParser = if (actualType == type) parser else OkResponseParser(parser)
            return actualParser as Parser<T>
        }
    }
}
