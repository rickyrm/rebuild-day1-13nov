| Paso | Anotación añadida | Efecto observado          |
| ---- | ----------------- | ------------------------- |
| 1    | `@RestController` | Registra bean web         |
| 2    | `@RequestMapping` | Normaliza path minúscula  |
| 3    | `@GetMapping`     | Vincula método a URL      |
| 4    | `@PathVariable`   | Inyecta valor → 200 OK    |
| 5    | `ResponseEntity`  | Control total status/body |

## Quitar spring-boot-starter-web del pom.xml
Al comentar la dependencia en el pom.xml y ejecutar mvn clean compile da una serie de errores:

Muestra que el package org.springframework.boot.* no existe.
