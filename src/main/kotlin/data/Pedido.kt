package data

import kotlinx.serialization.Serializable

@Serializable
data class Pedido (
    val id: Int,
    val nomeFicheiro: String,
    val pesoEstimado: Double,
    val custoTotal: Double,
    var status: String = "Pendente"
)
