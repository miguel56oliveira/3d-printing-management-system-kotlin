package data

import org.jetbrains.exposed.sql.Table

object Materiais : Table() {
    val id = integer("id").autoIncrement()
    val tipo = varchar("tipo", 50)
    val cor = varchar("cor", 50)
    val pesoRestante = double("peso_restante")
    val precoGrama = double("preco_grama")
    override val primaryKey = PrimaryKey(id)
}

object Impressoras : Table() {
    val id = integer("id").autoIncrement()
    val modelo = varchar("modelo", 100)
    val estado = varchar("estado", 20)
    override val primaryKey = PrimaryKey(id)
}

object Pedidos : Table() {
    val id = integer("id").autoIncrement()
    val nomeFicheiro = varchar("nome_ficheiro", 255)
    val peso = double("peso")
    val custo = double("custo")
    val materialId = integer("material_id")
    override val primaryKey = PrimaryKey(id)
}