package data

data class Material (
    val id: Int,
    val tipo: String,
    val cor: String,
    var pesoRestante: Double,
    var precoPorGrama: Double
)
