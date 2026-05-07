package ui

class MenuPrincipal {

    fun exibirPainelControlo() {
            println("========================================")
            println("   SISTEMA DE GESTÃO DE IMPRESSÃO 3D    ")
            println("========================================")
            println("1. Novo Pedido (Upload .STL/.3MF)")
            println("2. Gerir Inventário de Materiais")
            println("3. Estado das Impressoras")
            println("4. Histórico de Pedidos")
            println("0. Sair")
            println("========================================")
    }

        fun lerOpcao(): Int {
                while (true) {
                    print("Selecione uma opção: ")
                    val entrada = readln()
                    val numero = entrada.toIntOrNull()
                    if (numero != null) return numero
                    println("Erro: Por favor, insira um número válido.")
                }
        }

        fun lerEntradaTexto(mensagem: String): String {
            while (true) {
                print(mensagem)
                val entrada = readlnOrNull()?.trim()
                if (!entrada.isNullOrEmpty()) return entrada
                println("O campo não pode estar vazio.")            }
        }

        fun lerEntradaDouble(mensagem: String): Double {
            while (true) {
                print(mensagem)
                val entrada = readlnOrNull()?.replace(',', '.')
                val numero = entrada?.toDoubleOrNull()

                if (numero != null && numero >= 0) {
                    return numero
                }
                println("Insira um valor numérico válido (ex: 10.5).")
            }
        }
}