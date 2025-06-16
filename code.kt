fun main() {
    val panaderia = Panaderia()
    println("¡Bienvenido al sistema de la Panadería!")

    while (true) {
        println("\nMenú principal:")
        println("1. Ver menú de productos")
        println("2. Reabastecer productos")
        println("3. Realizar pedido")
        println("4. Ver pedidos realizados")
        println("5. Salir")

        when (leerEntero("Selecciona una opción: ")) {
            1 -> panaderia.mostrarMenu()
            2 -> panaderia.reabastecerProducto()
            3 -> panaderia.iniciarPedido()
            4 -> panaderia.verPedidos()
            5 -> {
                println("¡Gracias por usar el sistema!")
                break
            }
            else -> println("Opción inválida.")
        }
    }
}

// --- Clases ---

class Producto(private val nombre: String, private var precio: Double, private var cantidad: Int) {
    fun getNombre() = nombre
    fun getPrecio() = precio
    fun getCantidad() = cantidad

    fun setPrecio(nuevoPrecio: Double) {
        if (nuevoPrecio >= 0) precio = nuevoPrecio
    }

    fun agregarStock(cant: Int) {
        if (cant >= 0) cantidad += cant
    }

    fun reducirStock(cant: Int): Boolean {
        return if (cant <= cantidad) {
            cantidad -= cant
            true
        } else false
    }

    override fun toString(): String {
        return "$nombre: $cantidad unidades | Precio: $${"%.2f".format(precio)}"
    }
}

data class DetallePedido(val producto: Producto, val cantidad: Int) {
    fun subtotal(): Double = cantidad * producto.getPrecio()
    override fun toString(): String =
        "- ${producto.getNombre()} x$cantidad = $${"%.2f".format(subtotal())}"
}

class Pedido(
    private val cliente: String,
    private val numeroOrden: Int,
    private val detalles: List<DetallePedido>
) {
    private val total = detalles.sumOf { it.subtotal() }

    override fun toString(): String {
        val resumen = detalles.joinToString("\n") { it.toString() }
        return """
            Pedido #$numeroOrden - Cliente: $cliente
            $resumen
            Total: $${"%.2f".format(total)}
        """.trimIndent()
    }

    fun mostrarAgradecimiento() {
        println("\nGracias por tu compra, $cliente ❤️")
        println("Tu número de orden es: $numeroOrden")
    }
}

class Panaderia {
    private val productos = mutableListOf(
        Producto("galletas", 5.0, 10),
        Producto("muffins", 8.0, 10),
        Producto("pasteles", 15.0, 5)
    )

    private val pedidos = mutableListOf<Pedido>()
    private var contadorOrdenes = 1

    fun mostrarMenu() {
        println("\nMenú de productos:")
        productos.forEach { println(it) }
    }

    fun reabastecerProducto() {
        print("Nombre del producto a reabastecer: ")
        val nombre = readLine()?.lowercase()?.trim()
        val producto = productos.find { it.getNombre() == nombre }

        if (producto != null) {
            val cantidad = leerEntero("Cantidad a añadir: ")
            val precio = leerDecimal("Nuevo precio unitario: ")
            producto.agregarStock(cantidad)
            producto.setPrecio(precio)
            println("Producto actualizado.")
        } else {
            println("Producto no encontrado.")
        }
    }

    fun iniciarPedido() {
        print("Nombre del cliente: ")
        val cliente = readLine()?.trim() ?: "Cliente"

        val detalles = mutableListOf<DetallePedido>()

        while (true) {
            mostrarMenu()
            print("Producto a agregar (o escribe 'fin' para terminar): ")
            val nombre = readLine()?.lowercase()?.trim()
            if (nombre == "fin") break

            val producto = productos.find { it.getNombre() == nombre }

            if (producto != null) {
                val cantidad = leerEntero("Cantidad: ")
                if (producto.getCantidad() >= cantidad) {
                    detalles.add(DetallePedido(producto, cantidad))
                } else {
                    println("Stock insuficiente.")
                }
            } else {
                println("Producto no encontrado.")
            }
        }

        if (detalles.isEmpty()) {
            println("No se agregaron productos al pedido.")
            return
        }

        val numeroOrden = contadorOrdenes++
        val pedidoTemp = Pedido(cliente, numeroOrden, detalles)

        println("\nResumen del pedido:")
        println(pedidoTemp)

        print("\n¿Deseas modificar el pedido antes de confirmar? (s/n): ")
        val respuesta = readLine()?.lowercase()

        if (respuesta == "s") {
            println("Cancelando pedido. Puedes comenzarlo nuevamente si lo deseas.")
        } else {
            // Confirmar
            detalles.forEach {
                it.producto.reducirStock(it.cantidad)
            }
            pedidos.add(pedidoTemp)
            println("¡Pedido confirmado!")
            pedidoTemp.mostrarAgradecimiento()
        }
    }

    fun verPedidos() {
        if (pedidos.isEmpty()) {
            println("No hay pedidos aún.")
        } else {
            println("\nPedidos realizados:")
            pedidos.forEach {
                println("\n--------------------")
                println(it)
            }
        }
    }
}

// --- Funciones auxiliares ---

fun leerEntero(mensaje: String): Int {
    while (true) {
        print(mensaje)
        val entrada = readLine()?.toIntOrNull()
        if (entrada != null && entrada >= 0) return entrada
        println("Entrada inválida. Ingresa un número entero positivo.")
    }
}

fun leerDecimal(mensaje: String): Double {
    while (true) {
        print(mensaje)
        val entrada = readLine()?.toDoubleOrNull()
        if (entrada != null && entrada >= 0) return entrada
        println("Entrada inválida. Ingresa un número decimal positivo.")
    }
}
