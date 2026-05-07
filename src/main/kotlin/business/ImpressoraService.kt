package business

import data.Impressora

class ImpressoraService {

    fun registarManuntencao(impressora: Impressora, descricao: String) {
        impressora.historicoManuntecao.add(descricao)
    }

    fun atualizarEstado(impressora: Impressora, novoEstado: data.EstadoImpressora) {
        impressora.estado = novoEstado
    }
}