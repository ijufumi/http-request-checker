package http.request.checker.controller

import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Header
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import org.slf4j.LoggerFactory
import org.slf4j.Logger
import com.box.sdk.BoxWebHookSignatureVerifier
import io.micronaut.http.HttpRequest

@Controller("/")
class SampleController {

    private val logger: Logger = LoggerFactory.getLogger(javaClass.simpleName)
    private val primaryKey = System.getenv("BOX_PRIMARY_KEY")
    private val secondaryKey = System.getenv("BOX_SECONDARY_KEY")

    @Post(consumes = [MediaType.APPLICATION_JSON])
    fun post(
            request: HttpRequest<Any>,
            @Header("box-delivery-id") id: String,
            @Header("box-delivery-timestamp") timestamp: String,
            @Header("box-signature-algorithm") algorithm: String,
            @Header("box-signature-primary") primarySignature: String,
            @Header("box-signature-secondary") secondarySignature: String,
            @Header("box-signature-version") version: String,
            @Header("content-type") contentType: String,
            @Header("x-forwarded-for") xForwardedFor: String,
            @Body body: String
    ): String {
        logger.info("request headers -----")
        for (header in request.headers) {
            logger.info("[{}]: {}", header.key, header.value)
        }

        logger.info("request headers -----")
        // logger.info("remoteHost: {}", InetAddress.getByName(xForwardedFor).hostName)
        validation(version, algorithm, primarySignature, secondarySignature, timestamp, body)
        return "hello, world"
    }

    private fun validation(version: String,
                           algorithm: String,
                           primarySignature: String,
                           secondarySignature: String,
                           timestamp: String,
                           body: String) {
        val validation = BoxWebHookSignatureVerifier(primaryKey, secondaryKey)
        val newBody = convertToUnicode(body)
        logger.info("{} newBody: {}", "Unicode", newBody)
        logger.info("validation: {}", validation.verify(version, algorithm, primarySignature, secondarySignature, newBody, timestamp))
    }

    private fun convertToUnicode(body: String): String {
        val sb = StringBuilder()
        for(c in body.toCharArray()) {
            if (c.toString().toByteArray().size < 2) {
                sb.append(c)
            } else {
                logger.info("{}", c.toString())
                sb.append(String.format("\\u%04x", c.toInt()))
            }
        }
        return sb.toString()
    }

    @Get
    fun index(): String {
        return "hello, world"
    }
}
