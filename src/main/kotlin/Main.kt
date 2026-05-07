import data.Material
import business.OrcamentoService
import business.InventarioService
import ui.ImpressaoControler
import ui.MenuPrincipal

fun main() {
    val orcamentoService = OrcamentoService()
    val inventarioService = InventarioService()
    val controller = ImpressaoControler(orcamentoService, inventarioService)
    val menu = MenuPrincipal()

    val materialAtual = Material(1, "PLA", "Azul", 500.0, 0.05)

    var continuar = true

    while (continuar) {
        menu.exibirPainelControlo()

        when (menu.lerOpcao()) {
            1 -> {
                val ficheiro = menu.lerEntradaTexto("Nome do ficheiro (.stl/.3mf): ")
                val peso = menu.lerEntradaDouble("Peso estimado da peça (g):" )
                controller.processarNovoPedido(ficheiro, peso, materialAtual)
            }

            2 -> {
                println("Stock atual: ${materialAtual.pesoRestante}g")
                val alerta = inventarioService.verificarAlertaStock((materialAtual))
                if (alerta != null) println(alerta)
            }

            3 -> {
                println("Funcionalidade de Gestão de Impressoras (UC04) em desenvolvimento...")
            }
            0 -> {
                println("A encerrar o sistema...")
                continuar = false
            }
            else -> println("Opção inválida! Tente novamente.")
        }
    }
}