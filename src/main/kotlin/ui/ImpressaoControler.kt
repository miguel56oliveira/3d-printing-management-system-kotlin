package ui

import business.OrcamentoService
import business.InventarioService
import data.Material

class ImpressaoController(
    private val orcamentoService: OrcamentoService,
    private val inventarioService: InventarioService
) {

    fun processarNovoPedido(nomeFicheiro: String, pesoEstimado: Double, material: Material) {
        println("--- A processar pedido: $nomeFicheiro ---")

        if (!orcamentoService.temStockSuficiente(pesoEstimado,material)) {
            println("Erro: Stock insuficiente de ${material.tipo}!")
            return
        }

        val custo = orcamentoService.calcularCustoFinal(pesoEstimado, material.precoPorGrama)

        exibirConfirmacao(nomeFicheiro, custo, material)

        print("Deseja confirmar e agendar esta impressão? (1-Sim / 0-Não): ")
        val confirmacao = readlnOrNull()?.toIntOrNull() ?: 0

        if (confirmacao == 1) {
            orcamentoService.guardarPedido(nomeFicheiro, pesoEstimado, custo, material.id)
            inventarioService.consumirMaterialDB(material.id, pesoEstimado)

            println("Sucesso: Pedido Registado e material resevado!")
        } else {
            println("Pedido cancelado pelo operador.")
        }
    }

    private fun exibirConfirmacao(ficheiro: String, custo: Double, material: Material) {
        println("--- Orçamento Gerado ---")
        println("Ficheiro: $ficheiro")
        println("Material: ${material.tipo} (${material.cor})")
        println("Custo Total: ${"%.2f".format(custo)}€")
    }
}