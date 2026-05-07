package business

import data.Material

class OrcamentoService {

    fun calcularCustoFinal(pesoPeca: Double, precoPorGrama: Double): Double {
        val taxaFalha = 1.10
        return pesoPeca * precoPorGrama * taxaFalha
    }

    fun temStockSuficiente(pesoNecessario: Double, material: Material): Boolean {
        return material.pesoRestante >= pesoNecessario
    }

}
