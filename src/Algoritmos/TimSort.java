package Algoritmos;

//Obtenido de: https://www.geeksforgeeks.org/timsort/
public class TimSort {
    static int MIN_MERGE = 32;

    public static int minRunLength(int n)
    {
        assert n >= 0;

        // Se convierte en 1 si se desplazan 1 bits
        int r = 0;
        while (n >= MIN_MERGE) {
            r |= (n & 1);
            n >>= 1;
        }
        return n + r;
    }

    // Esta función ordena la matriz desde el índice izquierdo hasta
    // al índice derecho que tiene un tamaño máximo RUN
    public static void insertionSort(int[] arr, int left,
                                     int right)
    {
        for (int i = left + 1; i <= right; i++) {
            int temp = arr[i];
            int j = i - 1;
            while (j >= left && arr[j] > temp) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = temp;
        }
    }

    // La función de combinación fusiona las ejecuciones ordenadas
    public static void merge(int[] arr, int l, int m, int r)
    {
        // La matriz original está dividida en dos partes
        // matriz izquierda y derecha
        int len1 = m - l + 1, len2 = r - m;
        int[] left = new int[len1];
        int[] right = new int[len2];
        for (int x = 0; x < len1; x++) {
            left[x] = arr[l + x];
        }
        for (int x = 0; x < len2; x++) {
            right[x] = arr[m + 1 + x];
        }

        int i = 0;
        int j = 0;
        int k = l;

        // Después de comparar, fusionamos esas dos matrices
        // en una submatriz más grande
        while (i < len1 && j < len2) {
            if (left[i] <= right[j]) {
                arr[k] = left[i];
                i++;
            }
            else {
                arr[k] = right[j];
                j++;
            }
            k++;
        }

        // Copiar los elementos restantes
        // de izquierda, si la hay
        while (i < len1) {
            arr[k] = left[i];
            k++;
            i++;
        }

        // Copiar el elemento restante
        // de derecho, si lo hubiere
        while (j < len2) {
            arr[k] = right[j];
            k++;
            j++;
        }
    }

    // Función Timsort iterativa para ordenar el
    // matriz[0...n-1] (similar a ordenar por combinación)
    public static void timSort(int[] arr, int n)
    {
        int minRun = minRunLength(MIN_MERGE);

        // Ordenar subarreglos individuales de tamaño RUN
        for (int i = 0; i < n; i += minRun) {
            insertionSort(
                    arr, i,
                    Math.min((i + MIN_MERGE - 1), (n - 1)));
        }

        //Comenzar a fusionar desde el tamaño
        // EJECUTAR (o 32). Va a
        // fusionar para formar el tamaño 64,
        // luego 128, 256 y así sucesivamente
        // ....
        for (int size = minRun; size < n; size = 2 * size) {

            //Elige el punto de partida
            // de la submatriz izquierda. Nosotros
            // nos vamos a fusionar
            // arr[izquierda..izquierda+tamaño-1]
            // y arr[izquierda+tamaño, izquierda+2*tamaño-1]
            // Después de cada fusión, nosotros
            // aumenta la izquierda en 2*tamaño
            for (int left = 0; left < n; left += 2 * size) {

                // Encuentra el punto final de la submatriz izquierda
                // mid+1 es el punto inicial del sub derecho
                // matriz
                int mid = left + size - 1;
                int right = Math.min((left + 2 * size - 1),
                        (n - 1));

                // Fusionar submatriz arr[izquierda.....medio] &
                // arr[medio+1....derecha]
                if (mid < right)
                    merge(arr, left, mid, right);
            }
        }
    }

    //Imprimir arreglo
    public static void printArray(int[] arr, int n)
    {
        for (int i = 0; i < n; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.print("\n");
    }

    // Driver code
    public static void main(String[] args)
    {
        //Leer el arreglo de 10 mil elementos
        //final int[] arr = CrearArreglo.leerArregloDesdeArchivo("Arreglo10k.dat");

        //Leer el arreglo de 20 mil elementos
        //final int[] arr = CrearArreglo.leerArregloDesdeArchivo("Arreglo20k.dat");

        //Leer el arreglo de 50 mil elementos
        //final int[] arr = CrearArreglo.leerArregloDesdeArchivo("Arreglo50k.dat");

        //Leer el arreglo de 100 mil elementos
        final int[] arr = CrearArreglo.leerArregloDesdeArchivo("Arreglo100k.dat");

        // Registra el tiempo actual antes de ejecutar el algoritmo
        long tiempoInicio = System.currentTimeMillis();

        //Ejecución del algoritmo
        int n = arr.length;
        timSort(arr, n);

        // Registra el tiempo actual después de que el algoritmo haya terminado
        long tiempoFin = System.currentTimeMillis();

        // Calcula la diferencia de tiempo en milisegundos
        long tiempoTotal = tiempoFin - tiempoInicio;

        System.out.println("Tiempo de Ejecución para el algoritmo Tim Sort (Milesegundos): "+ tiempoTotal);


        //System.out.println("Given Array is");
        //printArray(arr, n);
        //System.out.println("After Sorting Array is");
        //printArray(arr, n);
    }
}
