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
import java.net.InetAddress

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
        logger.info("[box-delivery-id]: {}", id)
        logger.info("[box-delivery-timestamp]: {}", timestamp)
        logger.info("[box-signature-algorithm]: {}", algorithm)
        logger.info("[box-signature-primary]: {}", primarySignature)
        logger.info("[box-signature-secondary]: {}", secondarySignature)
        logger.info("[box-signature-version]: {}", version)
        logger.info("[content-type]: {}", contentType)
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
                           body: String): Unit {
        val list = arrayOf(Charsets.UTF_8, Charsets.UTF_16, Charsets.UTF_32, Charsets.ISO_8859_1, Charsets.US_ASCII)
        val validation = BoxWebHookSignatureVerifier(primaryKey, secondaryKey)
        for (char in list) {
            val newBody = body.toByteArray(Charsets.UTF_8).toString(char)
            logger.info("{} newBody: {}", char, newBody)
            logger.info("validation: {}", validation.verify(version, algorithm, primarySignature, secondarySignature, newBody, timestamp))
        }
    }

    @Get
    fun index(): String {
        return "hello, world"
    }
}
