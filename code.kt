fun main() {
    val panaderia = Panaderia()
    println("¡Bienvenido a la Panadería!")

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
            3 -> panaderia.realizarPedido()
            4 -> panaderia.verPedidos()
            5 -> {
                println("¡Gracias por usar el sistema!")
                break
            }
            else -> println("Opción inválida.")
        }
    }
}

// Clases y funciones

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
        return "$nombre: $cantidad unidades | Precio unitario: $${"%.2f".format(precio)}"
    }
}

class Pedido(private val producto: Producto, private val cantidad: Int) {
    private val total = producto.getPrecio() * cantidad

    override fun toString(): String {
        return "Pedido: ${producto.getNombre()} x$cantidad = $${"%.2f".format(total)}"
    }
}

class Panaderia {
    private val productos = mutableListOf(
        Producto("galletas", 5.0, 10),
        Producto("muffins", 8.0, 10),
        Producto("pasteles", 15.0, 5)
    )
    private val pedidos = mutableListOf<Pedido>()

    fun mostrarMenu() {
        println("\nMenú de productos:")
        productos.forEach { println(it) }
    }

    fun reabastecerProducto() {
        print("Introduce el nombre del producto a reabastecer: ")
        val nombre = readLine()?.lowercase()?.trim()
        val producto = productos.find { it.getNombre() == nombre }

        if (producto != null) {
            val cantidad = leerEntero("Cantidad a añadir: ")
            val precio = leerDecimal("Nuevo precio unitario: ")
            producto.agregarStock(cantidad)
            producto.setPrecio(precio)
            println("Producto actualizado con éxito.")
        } else {
            println("Producto no encontrado.")
        }
    }

    fun realizarPedido() {
        print("Introduce el nombre del producto a comprar: ")
        val nombre = readLine()?.lowercase()?.trim()
        val producto = productos.find { it.getNombre() == nombre }

        if (producto != null) {
            val cantidad = leerEntero("Cantidad a comprar: ")

            if (producto.getCantidad() >= cantidad) {
                producto.reducirStock(cantidad)
                val pedido = Pedido(producto, cantidad)
                pedidos.add(pedido)
                println("¡Pedido realizado con éxito!\n$pedido")
            } else {
                println("No hay suficiente stock disponible.")
            }
        } else {
            println("Producto no encontrado.")
        }
    }

    fun verPedidos() {
        if (pedidos.isEmpty()) {
            println("No hay pedidos aún.")
        } else {
            println("\nPedidos realizados:")
            pedidos.forEach { println(it) }
        }
    }
}

// Funciones auxiliares de lectura

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
