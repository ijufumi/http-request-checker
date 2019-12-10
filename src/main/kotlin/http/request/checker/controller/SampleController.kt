package http.request.checker.controller

import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Header
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import org.slf4j.LoggerFactory


@Controller("/")
class SampleController {
    val logger = LoggerFactory.getLogger("test")

    @Post(consumes = [MediaType.APPLICATION_JSON])
    fun post(
            @Header("box-delivery-id") id: String,
            @Header("box-delivery-timestamp") timestamp: String,
            @Header("box-signature-algorithm") algorithm: String,
            @Header("box-signature-primary") primaryKey: String,
            @Header("box-signature-secondary") secondaryKey: String,
            @Header("box-signature-version") version: String,
            @Body body: String
    ): String {
        logger.info("request headers -----")
        logger.info("[box-delivery-id]:{}", id)
        logger.info("[box-delivery-timestamp]:{}", timestamp)
        logger.info("[box-signature-algorithm]:{}", algorithm)
        logger.info("[box-signature-primary]:{}", primaryKey)
        logger.info("[box-signature-secondary]:{}", secondaryKey)
        logger.info("[box-signature-version]:{}", version)
        logger.info("request headers -----")

        logger.info("request body:{}", body)
        return "hello, world"
    }

    @Get
    fun index(): String {
        return "hello, world"
    }
}
