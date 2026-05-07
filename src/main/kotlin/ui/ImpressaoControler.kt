package ui

import business.OrcamentoService
import business.InventarioService
import data.Material

class ImpressaoControler(
    private val orcamentoService: OrcamentoService,
    private val inventarioService: InventarioService
) {

    fun processarNovoPedido(nomeFicheiro: String, pesoEstimado: Double, material: Material) {
        println("A processar pedido: $nomeFicheiro")

        if (!orcamentoService.temStockSuficiente(pesoEstimado,material)) {
            println("Erro: Stock insuficiente de ${material.tipo}!")
            return
        }

        val custo = orcamentoService.calcularCustoFinal(pesoEstimado, material.precoPorGrama)

        exibirConfirmacao(nomeFicheiro, custo, material)
    }

    private fun exibirConfirmacao(ficheiro: String, custo: Double, material: Material) {
        println("Orçamento Gerado:")
        println("Ficheiro: $ficheiro")
        println("Material: ${material.tipo} (${material.cor})")
        println("Custo Total (com taxa de falha): ${"%.2f".format(custo)}€")
    }
}