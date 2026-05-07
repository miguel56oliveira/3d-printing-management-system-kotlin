package data

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseConfig {
    fun init() {
        Database.connect("jdbc:h2:./impressao3d", driver = "org.h2.Driver")

        transaction {
            SchemaUtils.create(Materiais, Impressoras, Pedidos)
        }
    }
}