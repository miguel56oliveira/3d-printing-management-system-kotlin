
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.routing.*
import io.ktor.server.application.*
import business.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import ui.configurarRotas


fun main() {
    data.DatabaseConfig.init()

    val orcamentoService = OrcamentoService()
    val inventarioService = InventarioService()
    val impressoraService = ImpressoraService()

    embeddedServer(Netty, port = 8080) {

        install(ContentNegotiation) { json() }

        routing {
            configurarRotas(impressoraService, inventarioService)
        }
    }.start(wait = true)
}