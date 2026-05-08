package data

import kotlinx.serialization.Serializable

@Serializable
data class Impressora (
    val id: Int,
    val modelo: String,
    var estado: EstadoImpressora = EstadoImpressora.DISPONIVEL,
    val historicoManutencao: MutableList<String> = mutableListOf()
)
