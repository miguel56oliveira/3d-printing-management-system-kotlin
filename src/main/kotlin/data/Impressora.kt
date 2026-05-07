package data

data class Impressora (
    val id: Int,
    val modelo: String,
    var estado: EstadoImpressora = EstadoImpressora.DISPONIVEL,
    val historicoManuntecao: MutableList<String> = mutableListOf()
)
