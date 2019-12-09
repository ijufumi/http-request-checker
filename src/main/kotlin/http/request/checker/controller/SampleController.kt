package http.request.checker.controller

import io.micronaut.http.HttpRequest
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import org.slf4j.LoggerFactory
import java.awt.PageAttributes


@Controller("/")
class SampleController {
    val logger = LoggerFactory.getLogger("test")

    @Post(consumes = [MediaType.APPLICATION_JSON])
    fun post(request: HttpRequest<Any>): String {
        logger.info("request body:{}", request.body)
        return "hello, world"
    }

    @Get
    fun index(): String {
        return "hello, world"
    }
}
