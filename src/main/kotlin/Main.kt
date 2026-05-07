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
                val impressoraService = ImpressoraService()
                val maquinas = impressoraService.listarTodas()

                if (maquinas.isEmpty()) {
                    println("Nenhuma impressora na base de dados. Deseja registar uma? (1-Sim)")
                    if (menu.lerOpcao() == 1) {
                        val modelo = menu.lerEntradaTexto("Modelo: ")
                        impressoraService.registarImpressora(modelo)
                        println("Impressora registada com sucesso!")
                    }
                } else {
                    println("\n--- Estado Das Impressoras ---")
                    maquinas.forEach { println("${it.id}: ${it.modelo} [${it.estado}]") }

                    println("\n1. Colocar em Manutenção | 0. Voltar")
                    val escolhaAcao = menu.lerOpcao()

                    if (escolhaAcao == 1) {
                        print("ID da Máquina: ")
                        val idMaq = menu.lerOpcao()

                        if (maquinas.any { it.id == idMaq }) {
                            impressoraService.atualizarEstado(idMaq, data.EstadoImpressora.MANUTENCAO)
                            println("Sucesso: Máquina $idMaq colocada em manutenção.")
                        } else {
                            println("Erro: ID de máquina não encontrado.")
                        }
                    }
                }
            }

            4 -> {
                println("--- Histórico de Pedidos ---")
                val pedidos = orcamentoService.obterHistorico()

                if (pedidos.isEmpty()) {
                    println("Ainda não foram registados pedidos.")
                } else {
                    pedidos.forEach { p ->
                        println("Pedido #${p.id}: ${p.nomeFicheiro} | ${p.pesoEstimado}g | ${"%.2f".format(p.custoTotal)}€")
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