import java.util.*;

public class ListaDoblementeLigada<T extends Comparable<T>> {

    private static class Nodo<T> {
        T dato;
        Nodo<T> anterior;
        Nodo<T> siguiente;

        Nodo(T dato) {
            this.dato = dato;
            this.anterior = null;
            this.siguiente = null;
        }
    }

    private Nodo<T> cabeza;
    private Nodo<T> cola;
    private int tamanio;

    public ListaDoblementeLigada() {
        cabeza = null;
        cola = null;
        tamanio = 0;
    }

    // 1. Adicionar en orden ascendente
    public void adicionar(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);

        if (cabeza == null) {
            cabeza = nuevo;
            cola = nuevo;
        } else if (dato.compareTo(cabeza.dato) <= 0) {
            nuevo.siguiente = cabeza;
            cabeza.anterior = nuevo;
            cabeza = nuevo;
        } else if (dato.compareTo(cola.dato) >= 0) {
            nuevo.anterior = cola;
            cola.siguiente = nuevo;
            cola = nuevo;
        } else {
            Nodo<T> actual = cabeza;

            while (actual != null && actual.dato.compareTo(dato) < 0) {
                actual = actual.siguiente;
            }

            Nodo<T> previo = actual.anterior;

            previo.siguiente = nuevo;
            nuevo.anterior = previo;

            nuevo.siguiente = actual;
            actual.anterior = nuevo;
        }

        tamanio++;
    }

    // 2. Mostrar hacia adelante
    public void mostrarAdelante() {

        if (cabeza == null) {
            System.out.println("La lista está vacía.");
            return;
        }

        System.out.print("Adelante: ");

        Nodo<T> actual = cabeza;

        while (actual != null) {
            System.out.print(actual.dato);

            if (actual.siguiente != null)
                System.out.print(" -> ");

            actual = actual.siguiente;
        }

        System.out.println();
    }

    // 3. Mostrar hacia atrás
    public void mostrarAtras() {

        if (cola == null) {
            System.out.println("La lista está vacía.");
            return;
        }

        System.out.print("Atrás: ");

        Nodo<T> actual = cola;

        while (actual != null) {
            System.out.print(actual.dato);

            if (actual.anterior != null)
                System.out.print(" -> ");

            actual = actual.anterior;
        }

        System.out.println();
    }

    // 4. Ordenar descendentemente
    public void ordenarDescendente() {

        if (cabeza == null)
            return;

        List<T> lista = new ArrayList<>();

        Nodo<T> actual = cabeza;

        while (actual != null) {
            lista.add(actual.dato);
            actual = actual.siguiente;
        }

        lista.sort(Collections.reverseOrder());

        cabeza = null;
        cola = null;
        tamanio = 0;

        for (T dato : lista) {
            adicionar(dato);
        }

        System.out.println("Lista ordenada descendentemente.");
    }

    // 5. Mostrar moda(s)
    public void mostrarModas() {

        if (cabeza == null) {
            System.out.println("La lista está vacía.");
            return;
        }

        Map<T, Integer> frecuencia = new LinkedHashMap<>();

        Nodo<T> actual = cabeza;

        while (actual != null) {
            frecuencia.put(actual.dato,
                    frecuencia.getOrDefault(actual.dato, 0) + 1);

            actual = actual.siguiente;
        }

        int maxFrecuencia = Collections.max(frecuencia.values());

        if (maxFrecuencia == 1) {
            System.out.println("No hay moda.");
            return;
        }

        System.out.print("Moda(s): ");

        boolean primero = true;

        for (Map.Entry<T, Integer> entry : frecuencia.entrySet()) {

            if (entry.getValue() == maxFrecuencia) {

                if (!primero)
                    System.out.print(", ");

                System.out.print(entry.getKey());

                primero = false;
            }
        }

        System.out.println(" (aparece " + maxFrecuencia + " veces)");
    }

    // 6. Mostrar gráfico
    public void mostrarGrafico() {

        if (cabeza == null) {
            System.out.println("La lista está vacía.");
            return;
        }

        Map<T, Integer> frecuencia = new LinkedHashMap<>();

        Nodo<T> actual = cabeza;

        while (actual != null) {
            frecuencia.put(actual.dato,
                    frecuencia.getOrDefault(actual.dato, 0) + 1);

            actual = actual.siguiente;
        }

        System.out.println("Gráfico de ocurrencias:");

        for (Map.Entry<T, Integer> entry : frecuencia.entrySet()) {

            System.out.print(entry.getKey() + " ");

            for (int i = 0; i < entry.getValue(); i++) {
                System.out.print("*");
            }

            System.out.println();
        }
    }

    // 7. Existe
    public boolean existe(T dato) {

        Nodo<T> actual = cabeza;

        while (actual != null) {

            if (actual.dato.compareTo(dato) == 0)
                return true;

            actual = actual.siguiente;
        }

        return false;
    }

    // 8. Eliminar una ocurrencia
    public void eliminarUnaOcurrencia(T dato) {

        if (cabeza == null) {
            System.out.println("La lista está vacía.");
            return;
        }

        Nodo<T> actual = cabeza;

        while (actual != null) {

            if (actual.dato.compareTo(dato) == 0) {

                if (actual.anterior != null)
                    actual.anterior.siguiente = actual.siguiente;
                else
                    cabeza = actual.siguiente;

                if (actual.siguiente != null)
                    actual.siguiente.anterior = actual.anterior;
                else
                    cola = actual.anterior;

                tamanio--;

                System.out.println("Primera ocurrencia eliminada.");

                return;
            }

            actual = actual.siguiente;
        }

        System.out.println("El elemento no existe.");
    }

    // 9. Eliminar todas las ocurrencias
    public void eliminarTodasOcurrencias(T dato) {

        if (cabeza == null) {
            System.out.println("La lista está vacía.");
            return;
        }

        int eliminados = 0;

        Nodo<T> actual = cabeza;

        while (actual != null) {

            Nodo<T> siguiente = actual.siguiente;

            if (actual.dato.compareTo(dato) == 0) {

                if (actual.anterior != null)
                    actual.anterior.siguiente = actual.siguiente;
                else
                    cabeza = actual.siguiente;

                if (actual.siguiente != null)
                    actual.siguiente.anterior = actual.anterior;
                else
                    cola = actual.anterior;

                tamanio--;
                eliminados++;
            }

            actual = siguiente;
        }

        if (eliminados > 0)
            System.out.println(eliminados + " ocurrencia(s) eliminadas.");
        else
            System.out.println("El elemento no existe.");
    }

    // MAIN
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        ListaDoblementeLigada<Integer> lista =
                new ListaDoblementeLigada<>();

        int opcion;

        do {

            System.out.println("\n===== MENÚ =====");

            System.out.println("1. Adicionar");
            System.out.println("2. Mostrar hacia adelante");
            System.out.println("3. Mostrar hacia atrás");
            System.out.println("4. Ordenar descendentemente");
            System.out.println("5. Mostrar moda(s)");
            System.out.println("6. Mostrar gráfico");
            System.out.println("7. Existe");
            System.out.println("8. Eliminar una ocurrencia");
            System.out.println("9. Eliminar todas las ocurrencias");
            System.out.println("0. Salir");

            System.out.print("Seleccione opción: ");

            opcion = sc.nextInt();

            switch (opcion) {

                case 1:

                    System.out.print("Ingrese número: ");

                    int dato = sc.nextInt();

                    lista.adicionar(dato);

                    System.out.println("Elemento agregado.");

                    break;

                case 2:

                    lista.mostrarAdelante();

                    break;

                case 3:

                    lista.mostrarAtras();

                    break;

                case 4:

                    lista.ordenarDescendente();

                    break;

                case 5:

                    lista.mostrarModas();

                    break;

                case 6:

                    lista.mostrarGrafico();

                    break;

                case 7:

                    System.out.print("Número a buscar: ");

                    int buscar = sc.nextInt();

                    System.out.println(
                            lista.existe(buscar)
                                    ? "Sí existe."
                                    : "No existe."
                    );

                    break;

                case 8:

                    System.out.print("Número a eliminar: ");

                    int elim1 = sc.nextInt();

                    lista.eliminarUnaOcurrencia(elim1);

                    break;

                case 9:

                    System.out.print("Número a eliminar todas: ");

                    int elimAll = sc.nextInt();

                    lista.eliminarTodasOcurrencias(elimAll);

                    break;

                case 0:

                    System.out.println("Saliendo...");

                    break;

                default:

                    System.out.println("Opción inválida.");
            }

        } while (opcion != 0);

        sc.close();
    }
}