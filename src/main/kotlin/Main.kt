import business.ImpressoraService
import data.Material
import business.OrcamentoService
import business.InventarioService
import data.DatabaseConfig
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import ui.ImpressaoController
import ui.MenuPrincipal


fun main() {
    DatabaseConfig.init()

    val orcamentoService = OrcamentoService()
    val inventarioService = InventarioService()
    val controller = ImpressaoController(orcamentoService, inventarioService)
    val menu = MenuPrincipal()

    var continuar = true

    while (continuar) {
        menu.exibirPainelControlo()

        when (menu.lerOpcao()) {
            1 -> {
                val materiais = inventarioService.listarTodos()

                if (materiais.isEmpty()) {
                    println("Erro: Não existem materiais registados no sistema!")
                } else {
                    println("--- Selecionar Material ---")
                    materiais.forEach { println("${it.id}: ${it.tipo} ${it.cor} (${it.pesoRestante}g)") }

                    val idEscolhido = menu.lerOpcao()
                    val materialSelecionado = materiais.find { it.id == idEscolhido }

                    if (materialSelecionado != null) {
                        val ficheiro = menu.lerEntradaTexto("Nome do ficheiro (.stl/ .3mf): ")
                        val peso = menu.lerEntradaDouble("Peso estimado da peça (g): ")

                        controller.processarNovoPedido(ficheiro, peso, materialSelecionado)
                    } else {
                        println("ID de material inválido.")
                    }
                }
            }

            2 -> {
                println("--- Inventário ---")
                val materiais = inventarioService.listarTodos()

                if (materiais.isEmpty()) {
                    println("O inventário está vazio.")
                    println("Deseja registar o primeiro rolo? (1-Sim / 0-Não")
                    if (menu.lerOpcao() == 1) {
                        val tipo = menu.lerEntradaTexto("Tipo (Ex: PLA): ")
                        val cor = menu.lerEntradaTexto("Cor: ")
                        val peso = menu.lerEntradaDouble("Peso Inicial (g): ")
                        val preco = menu.lerEntradaDouble("Preço por grama (€): ")
                        inventarioService.registarMaterial(tipo, cor, peso, preco)
                    }
                } else {
                    materiais.forEach { m ->
                        println("ID: ${m.id} | ${m.tipo} ${m.cor} | Stock: ${m.pesoRestante}g")
                        val alerta = inventarioService.verificarAlertaStock(m)
                        if (alerta != null) println("  -> $alerta")
                    }
                }
            }

            3 -> {
                val maquinas = ImpressoraService.li
                maquinas.forEach { println("${it.id}: ") }

            }

            4 -> {
                println("--- Histórico de Pedidos ---")
                transaction {
                    data.Pedidos.selectAll().forEach {
                        println("Pedido #${it[data.Pedidos.id]}: ${it[data.Pedidos.nomeFicheiro]} | " +
                        "${it[data.Pedidos.peso]}g | ${"%.2f".format(it[data.Pedidos.custo])}€")
                    }
                }
            }

            0 -> {
                println("A encerrar o sistema...")
                continuar = false
            }
            else -> println("Opção inválida! Tente novamente.")
        }
    }
}