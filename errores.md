## Concurrencia básica
El código perteneciente a este cápitulo esta en /manual/concurrency/CounterDemo.java

### Tabla comparativa

| Versión         | Tiempo | Observación                                     |
| --------------- | ------ | ----------------------------------------------- |
| `synchronized`  | 38 ms  | Simple, pero bloquea todo el método             |
| `ReentrantLock` | 119 ms | Más código + **primitivo CAS** → más lento aquí |
| `AtomicLong`    | 23 ms  | **CAS sin bloqueo** → ganador claro             |

Conclusión: Para contadores simples, `AtomicLong` es la mejor opción.

## GC visual - jvisualvm

- ArrayList grows 1.5x, HashMap resizes @ 12 entries
- 2M increments: sync 38 ms, lock 119 ms, atomic 23 ms
- Heap peak 80 MB, 3 minor GCs, 0 Person instances → no leak