package http.request.checker.controller

import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Header
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import org.slf4j.LoggerFactory
import com.box.sdk.BoxWebHookSignatureVerifier


@Controller("/")
class SampleController {
    val logger = LoggerFactory.getLogger("test")
    val primaryKey = System.getenv("BOX_PRIMARY_KEY")
    val secondaryKey = System.getenv("BOX_SECONDARY_KEY")
    @Post(consumes = [MediaType.APPLICATION_JSON])
    fun post(
            @Header("box-delivery-id") id: String,
            @Header("box-delivery-timestamp") timestamp: String,
            @Header("box-signature-algorithm") algorithm: String,
            @Header("box-signature-primary") primarySignature: String,
            @Header("box-signature-secondary") secondarySignature: String,
            @Header("box-signature-version") version: String,
            @Body body: String
    ): String {
        logger.info("request headers -----")
        logger.info("[box-delivery-id]:{}", id)
        logger.info("[box-delivery-timestamp]:{}", timestamp)
        logger.info("[box-signature-algorithm]:{}", algorithm)
        logger.info("[box-signature-primary]:{}", primarySignature)
        logger.info("[box-signature-secondary]:{}", secondarySignature)
        logger.info("[box-signature-version]:{}", version)
        logger.info("request headers -----")

        logger.info("request body:{}", body)

        val validation = BoxWebHookSignatureVerifier(primaryKey, secondaryKey)
        logger.info("primaryKey:{}", primaryKey)
        logger.info("secondaryKey:{}", secondaryKey)

        logger.info("validation:{}", validation.verify(version, algorithm, primarySignature, secondarySignature, body, timestamp))
        logger.info("primarySign:{}", validation.sign(BoxWebHookSignatureVerifier.BoxSignatureAlgorithm.HMAC_SHA256, primaryKey, body, timestamp))
        logger.info("secondarySign:{}", validation.sign(BoxWebHookSignatureVerifier.BoxSignatureAlgorithm.HMAC_SHA256, secondaryKey, body, timestamp))
        return "hello, world"
    }

    @Get
    fun index(): String {
        return "hello, world"
    }
}
