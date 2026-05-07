package business

import data.Material
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class OrcamentoService {

    fun calcularCustoFinal(pesoPeca: Double, precoPorGrama: Double): Double {
        val taxaFalha = 1.10
        return pesoPeca * precoPorGrama * taxaFalha
    }

    fun temStockSuficiente(pesoNecessario: Double, material: Material): Boolean {
        return material.pesoRestante >= pesoNecessario
    }

    fun guardarPedido(nomeFicheiro: String, peso: Double, custo: Double, materialId: Int) {
        transaction {
            data.Pedidos.insert {
                it[data.Pedidos.nomeFicheiro] = nomeFicheiro
                it[data.Pedidos.peso] = peso
                it[data.Pedidos.custo]= custo
                it[data.Pedidos.materialId] = materialId
            }
        }
    }

    fun obterHistorico(): List<data.Pedido> {
        return transaction {
            data.Pedidos.selectAll().map {
                data.Pedido(
                    id = it[data.Pedidos.id],
                    nomeFicheiro = it[data.Pedidos.nomeFicheiro],
                    pesoEstimado = it[data.Pedidos.peso],
                    custoTotal = it[data.Pedidos.custo]
                )
            }
        }
    }

}
