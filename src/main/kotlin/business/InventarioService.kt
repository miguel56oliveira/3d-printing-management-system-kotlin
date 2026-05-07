package business

import data.Materiais
import data.Material
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class InventarioService {

    fun verificarAlertaStock(material: Material): String? {
        return if (material.pesoRestante < 100.0) {
            "ALERTA: O material ${material.tipo} (${material.cor}) está com stock baixo!"
        } else {
            null
        }
    }

    fun registarMaterial(tipo: String, cor: String, peso: Double, preco: Double) {
        transaction {
            Materiais.insert {
                it[Materiais.tipo] = tipo
                it[Materiais.cor] = cor
                it[Materiais.pesoRestante] = peso
                it[Materiais.precoGrama] = preco
            }
        }
    }

    fun consumirMaterialDB(idMaterial: Int, pesoConsumido: Double) {
        transaction {
            val materialRow = Materiais.select { Materiais.id eq idMaterial }.single()
            val stockAtual = materialRow[Materiais.pesoRestante]

            if (stockAtual >= pesoConsumido) {
                Materiais.update ({ Materiais.id eq idMaterial }) {
                    it[pesoRestante] = stockAtual - pesoConsumido
                }
                println("Material consumido com sucesso na base de dados.")
            } else {
                println("Erro: Stock insuficiente!")
            }
        }
    }

    fun listarTodos(): List<Material> {
        return transaction {
            Materiais.selectAll().map {
                Material(
                    id = it[Materiais.id],
                    tipo = it[Materiais.tipo],
                    cor = it[Materiais.cor],
                    pesoRestante = it[Materiais.pesoRestante],
                    precoPorGrama = it[Materiais.precoGrama]
                )
            }
        }
    }
}

