package http.request.checker

import io.micronaut.runtime.Micronaut

object Application {

    @JvmStatic
    fun main(args: Array<String>) {
        Micronaut.build()
                .packages("http.request.checker")
                .mainClass(Application.javaClass)
                .start()
    }
}