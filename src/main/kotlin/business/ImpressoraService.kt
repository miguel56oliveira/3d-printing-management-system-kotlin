package business

import data.Impressoras
import data.Impressora
import data.EstadoImpressora
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class ImpressoraService {

    fun registarImpressora(modelo: String) {
        transaction {
            Impressoras.insert {
                it[Impressoras.modelo] = modelo
                it[Impressoras.estado] = EstadoImpressora.DISPONIVEL.name
            }
        }
    }

    fun listarTodas(): List<Impressora> {
        return transaction {
            Impressoras.selectAll().map {
                Impressora(
                    id = it[Impressoras.id],
                    modelo = it[Impressoras.modelo],
                    estado = EstadoImpressora.valueOf(it[Impressoras.estado])
                )
            }
        }
    }

    fun atualizarEstado(idImpressora: Int, novoEstado: EstadoImpressora) {
         transaction {
             Impressoras.update({ Impressoras.id eq idImpressora }) {
                 it[estado] = novoEstado.name
             }
         }
    }

    fun registarManutencao(idImpressora: Int, descricao: String) {
        transaction {
            Impressoras.update({ Impressoras.id eq idImpressora }) {
                it[estado] = EstadoImpressora.MANUTENCAO.name
            }
            println("Manutenção registada: $descricao")
        }
    }
}