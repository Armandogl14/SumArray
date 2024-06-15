# Requerimientos para ejecutar

## Para ejecutar el codigo es necesario:

1. Tener Java 21.
2. Tener Gradle.

# **Explicación del código fuente**

![Captura del codigo](https://github.com/Armandogl14/SumArray/blob/main/imgs/Screenshot%202024-06-14%20221430.png)

**Variables estáticas:** 

- ARRAY_SIZE: Tamaño del arreglo.
- THREAD_COUNT: Cantidad de hilos (en caso de esta prácica su valor puede ser 2, 4,16 o 32).
- numbers: Lista dinámica que contendrá los números.

![Captura del codigo](https://github.com/Armandogl14/SumArray/blob/main/imgs/Screenshot%202024-06-14%20221532.png)

**Escribiendo los números aleatorios y luego se colocan en la lista de números:** Con la clase BufferWriter de Java se crea un archivo "numbers.txt", y luego se lee el archivo con BufferReader para colocarlos en la lista numbers.

![Captura del codigo](https://github.com/Armandogl14/SumArray/blob/main/imgs/Screenshot%202024-06-14%20221540.png)

**Suma secuencial:** Una suma secuenial básica donde se va recorriendo el arreglo elemento por elemento y se va acumulando en la variable sum.

![Captura del codigo](https://github.com/Armandogl14/SumArray/blob/main/imgs/Screenshot%202024-06-14%20223202.png)

**Suma paralela:** Este metodo recibe la lista numbers y la cantidad de hilos que serán creados. En un bucle se crean los hilos y mediante un lambda se calcula la suma de la porción del arreglo que le tocó a cada hilo. Y finalmente se combinan las sumar parciales en una sola variable para retornar el valor.

**NOTA:** Los tiempos son calculados mediante el metodo conocido en clase, es decir, una variable startTime antes de llamar el respectivo método (secuencial o paralelo), y una variable endTime justo después del llamado del método, y luego se restan para encontrar el tiempo de ejecución.

