fun main() {
    val moneda = "$"
    val productos = mutableMapOf(
        "galletas" to mutableMapOf("cantidad" to 0, "precio" to 0.0),
        "muffins" to mutableMapOf("cantidad" to 0, "precio" to 0.0),
        "pasteles" to mutableMapOf("cantidad" to 0, "precio" to 0.0)
    )

    fun mostrarInventario() {
        println("\nInventario actual:")
        productos.forEach { (producto, datos) ->
            println("$producto: ${datos["cantidad"]} unidades | Precio unitario: $moneda${datos["precio"]}")
        }
    }

    fun leerEntero(mensaje: String): Int {
        while (true) {
            print(mensaje)
            val entrada = readLine()?.toIntOrNull()
            if (entrada != null && entrada >= 0) return entrada
            println("Entrada no válida. Introduce un número válido.")
        }
    }

    fun leerDecimal(mensaje: String): Double {
        while (true) {
            print(mensaje)
            val entrada = readLine()?.toDoubleOrNull()
            if (entrada != null && entrada >= 0) return entrada
            println("Entrada no válida. Introduce un precio válido.")
        }
    }

    fun reabastecerArticulo() {
        print("Introduce el nombre del artículo: ")
        val producto = readLine()?.lowercase()
        if (producto in productos) {
            val cantidad = leerEntero("Cantidad a añadir: ")
            val precio = leerDecimal("Nuevo precio unitario: ")

            productos[producto]?.set("cantidad", (productos[producto]?.get("cantidad") as Int) + cantidad)
            productos[producto]?.set("precio", precio)

            println("¡Reabastecimiento completado!")
        } else {
            println("Artículo no encontrado.")
        }
    }

    fun venderArticulo() {
        print("Introduce el nombre del artículo: ")
        val producto = readLine()?.lowercase()
        if (producto in productos) {
            val cantidad = leerEntero("Cantidad a vender: ")
            val stockActual = productos[producto]?.get("cantidad") as Int

            if (cantidad > stockActual) {
                println("No hay suficiente stock disponible.")
            } else {
                val precioUnitario = productos[producto]?.get("precio") as Double
                productos[producto]?.set("cantidad", stockActual - cantidad)

                println("Venta realizada. Ingresos generados: $moneda${cantidad * precioUnitario}")
            }
        } else {
            println("Artículo no encontrado.")
        }
    }

    while (true) {
        println("\nMenú de gestión de inventario:")
        println("1. Ver niveles de inventario")
        println("2. Reabastecer artículos")
        println("3. Vender artículos")
        println("4. Salir")

        when (leerEntero("Elige una opción: ")) {
            1 -> mostrarInventario()
            2 -> reabastecerArticulo()
            3 -> venderArticulo()
            4 -> {
                println("Saliendo del programa...")
                break
            }
            else -> println("Opción no válida.")
        }
    }
}
