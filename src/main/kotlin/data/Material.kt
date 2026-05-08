package data

import kotlinx.serialization.Serializable

@Serializable
data class Material (
    val id: Int,
    val tipo: String,
    val cor: String,
    var pesoRestante: Double,
    var precoPorGrama: Double
)
