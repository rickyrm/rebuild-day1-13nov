package manual.concurrency;                     // Declara el paquete donde reside esta clase.

import java.util.concurrent.atomic.AtomicLong;   // Importa AtomicLong para operaciones atómicas sin bloqueo.
import java.util.concurrent.locks.ReentrantLock; // Importa ReentrantLock para gestionar exclusión mutua explícita.

public class CounterDemo {                       // Clase principal que encapsula las tres estrategias de sincronización.

    private long count = 0;                      // Contador compartido usado por tests con synchronized y ReentrantLock.
    private final ReentrantLock lock = new ReentrantLock(); // Lock reentrante que controla acceso exclusivo a la sección crítica.
    private final AtomicLong atomicCount = new AtomicLong(0); // Contador atómico basado en CAS (Compare-And-Swap).

    public static void main(String[] args) throws InterruptedException { // Punto de entrada. join() obliga a declarar throws.
        CounterDemo demo = new CounterDemo();                         // Instancia de la clase para acceder a los métodos de prueba.

        System.out.println("synchronized: " + demo.syncTest() + "ms"); // Ejecuta test con synchronized y reporta latencia.
        System.out.println("ReentrantLock: " + demo.lockTest() + "ms"); // Ejecuta test con ReentrantLock.
        System.out.println("AtomicLong: " + demo.atomicTest() + "ms");  // Ejecuta test usando AtomicLong.
    }

    private long syncTest() throws InterruptedException { // Benchmark que incrementa usando un método synchronized.
        count = 0;                                        // Resetea el contador para garantizar medición limpia.
        long start = System.nanoTime();                   // Marca temporal inicial en nanosegundos.

        Thread t1 = new Thread(() -> {                    // Primer hilo concurrente.
            for (int i = 0; i < 1_000_000; i++)           // Bucle intensivo de incrementos.
                incrementSync();                          // Incremento protegido con palabra clave synchronized.
        });

        Thread t2 = new Thread(() -> {                    // Segundo hilo con la misma carga.
            for (int i = 0; i < 1_000_000; i++)
                incrementSync();
        });

        t1.start(); t2.start();                           // Arranca ambos hilos en paralelo.
        t1.join(); t2.join();                             // Espera a que finalicen (sin esto no se puede medir con precisión).

        long end = System.nanoTime();                     // Marca temporal de fin.
        System.out.println("sync result = " + count);     // Imprime el valor final del contador (debe ser 2.000.000 si no hay fallos).
        return (end - start) / 1_000_000;                 // Devuelve duración en milisegundos.
    }

    private synchronized void incrementSync() {           // Método sincronizado; solo un hilo puede ejecutar este bloque a la vez.
        count++;                                          // Incremento seguro de la variable compartida.
    }

    private long lockTest() throws InterruptedException { // Benchmark usando ReentrantLock.
        count = 0;                                        // Resetea contador.
        long start = System.nanoTime();                   // Registro temporal inicial.

        Thread t1 = new Thread(() -> {                    // Primer hilo concurrente.
            for (int i = 0; i < 1_000_000; i++)
                incrementLock();                          // Incremento protegido por lock explícito.
        });

        Thread t2 = new Thread(() -> {                    // Segundo hilo concurrente.
            for (int i = 0; i < 1_000_000; i++)
                incrementLock();
        });

        t1.start(); t2.start();                           // Inicia ambos hilos.
        t1.join(); t2.join();                             // Espera finalización para medir correctamente.

        long end = System.nanoTime();                     // Registro temporal final.
        System.out.println("lock result = " + count);     // Muestra contador resultante.
        return (end - start) / 1_000_000;                 // Tiempo total en milisegundos.
    }

    private void incrementLock() {                        // Incremento gestionado con ReentrantLock.
        lock.lock();                                      // Entrada en sección crítica (adquisición del lock).
        try {
            count++;                                      // Acción protegida.
        } finally {
            lock.unlock();                                // Liberación del lock garantizada (aunque ocurra una excepción).
        }
    }

    private long atomicTest() throws InterruptedException { // Benchmark sobre AtomicLong.
        atomicCount.set(0);                                 // Resetea el contador atómico.
        long start = System.nanoTime();                     // Marca temporal inicial.

        Thread t1 = new Thread(() -> {                      // Primer hilo concurrente.
            for (int i = 0; i < 1_000_000; i++)
                atomicCount.incrementAndGet();              // Incremento atómico sin bloqueo.
        });

        Thread t2 = new Thread(() -> {                      // Segundo hilo concurrente.
            for (int i = 0; i < 1_000_000; i++)
                atomicCount.incrementAndGet();
        });

        t1.start(); t2.start();                             // Arranque de hilos.
        t1.join(); t2.join();                               // Espera de finalización.

        long end = System.nanoTime();                       // Marca temporal final.
        System.out.println("atomic result = " + atomicCount.get()); // Imprime valor final.
        return (end - start) / 1_000_000;                   // Duración en milisegundos.
    }


}

