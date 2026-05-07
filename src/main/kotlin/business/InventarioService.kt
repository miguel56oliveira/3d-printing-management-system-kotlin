package business

import data.Material

class InventarioService {

    fun verificarAlertaStock(material: Material): String? {
        return if (material.pesoRestante < 100.0) {
            "ALERTA: O material ${material.tipo} (${material.cor}) está com stock baixo!"
        } else {
            null
        }
    }

    fun consumirMaterial(material: Material, pesoConsumido: Double) {
        if (material.pesoRestante >= pesoConsumido) {
            material.pesoRestante -= pesoConsumido
        }
    }
}

