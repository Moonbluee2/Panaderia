fun main() {
    val moneda = "$"
    val productos = MutableMapof(
        "galletas" to Pair (0, 0.0),
        "muffins" to Pair(0, 0.0),
        "pasteles" to Pair(0, 0.0)
    )

    fun leerEntero(mensaje: String): Int {
        while (true) {
            print(mensaje)
            val entrada = readLine()?.toIntOrNull()
            if (entrada != null && entrada >= 0) return entrada
            println("Entrada no válida. Por favor, introduce un número válido.")
        }
    }

    fun leerDecimal(mensaje: String): Double {
        while (true) {
            print(mensaje)
            val entrada = readLine()?.toDoubleOrNull()
            if (entrada != null && entrada >= 0) return entrada
            println("Entrada no válida. Por favor, introduce un precio válido.")
        }
    }

    val productos = listOf("galletas", "muffins", "pasteles")
    val ingresos = mutableMapOf<String, Double>()

    productos.forEach { producto ->
        val vendidos = leerEntero("¿Cuántos $producto se vendieron? ")
        val precio = leerDecimal("Precio de cada $producto: ")
        ingresos[producto] = vendidos * precio
    }

    val ingresoTotal = ingresos.values.sum()

    println("\nReporte de Ingresos del Día:")
    ingresos.forEach { (producto, ingreso) ->
        println("Ingresos por $producto: $moneda$ingreso")
    }
    println("Ingreso total del día: $moneda$ingresoTotal")
}
