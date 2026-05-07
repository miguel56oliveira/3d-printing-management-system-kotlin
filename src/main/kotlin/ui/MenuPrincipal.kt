package ui

class MenuPrincipal {

    fun exibirPainelControlo() {
            println("========================================")
            println("   SISTEMA DE GESTÃO DE IMPRESSÃO 3D    ")
            println("========================================")
            println("1. Novo Pedido (Upload .STL/.3MF)")
            println("2. Gerir Inventário de Materiais")
            println("3. Estado das Impressoras (UC04)")
            println("4. Histórico de Pedidos (UC05)")
            println("0. Sair")
            println("========================================")
            print("Selecione uma opção: ")
    }

        fun lerOpcao(): Int {
                return readlnOrNull()?.toIntOrNull() ?: -1
        }

        fun lerEntradaTexto(mensagem: String): String {
                print(mensagem)
                return readlnOrNull() ?: ""
        }

        fun lerEntradaDouble(mensagem: String): Double {
                print(mensagem)
                return readlnOrNull()?.toDoubleOrNull() ?: 0.0
        }
}