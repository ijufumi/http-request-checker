package http.request.checker.controller

import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import org.slf4j.LoggerFactory


@Controller("/")
class SampleController {
    val logger = LoggerFactory.getLogger("test")

    @Post(consumes = [MediaType.APPLICATION_JSON])
    fun post( @Body payload: String): String {
        logger.info("request body:{}", payload)
        return "hello, world"
    }

    @Get
    fun index(): String {
        return "hello, world"
    }
}
