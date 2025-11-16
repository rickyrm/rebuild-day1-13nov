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


## Código de HelloServlet.java

1. extends HttpServlet
- HttpServlet está en jakarta.servlet.http (antes ajax).
- Es la clase base que el contenedor web (Tomcat) llama cuando llega una peticion HTTP.
2. doGet(HttpServletRequest req, HttpServletResponse resp)
- El contenedor crea los objetos req y resp por tí.
- Solo sobrescribes el verbo que te interese (GET en este caso).
3. resp.setContentType("application/json").
- Cabecera HTTP Content-Type:application/json.
- Si no la pones, Tomcat mandará text/plain por defecto.
4. resp.setStatus(HttpServletResponse.SC_OK).
- SC_OK == constante 200.
- Equivalente a resp.setStatus(200). pero más legible.
5. resp.getWriter().println(...).
- getWriter() devuelve un PrintWriter ya ligado al socket de salida.
- Cada println escribe directamente en el cuerpo HTTP (sin buffer hasta flush implícito).

## ¿Quién llama a doGet?
- Tomcat tiene un mapper de URL → ServletRegistrationBean.
- Cuando llega GET /manual, el contenedor:
  1. Crea HttpServletRequest y HttpServletResponse. 
  2. Instancia tu servlet (una sola vez, singleton). 
  3. Invoca service(req, resp) → delega a doGet(...).


## Diferencia entre servlet puro y Spring MVC
| Tecnología                         | Lugar donde se fija la URL                                      |
| ---------------------------------- | --------------------------------------------------------------- |
| **Spring MVC** (`@RestController`) | Anotación `@RequestMapping`, `@GetMapping`, etc.                |
| **Servlet puro** (`HttpServlet`)   | **Segundo parámetro** del constructor `ServletRegistrationBean` |

Servlet puro → path en ServletRegistrationBean;   
Spring MVC → path en anotaciones de clase/método.

## Resumen del día 2
- 31 JARs embebidos en BOOT-INF/lib
- JarLauncher bootstraps nested classpath
- Manual servlet 200 OK at /manual
- Spring MVC 200 OK at /Person/{id}
- Custom 400 JSON without @Valid

## Nueva rama day3-latency-slim-jar
Comparativa entre Spring MVC y Servlet puro.

| Endpoint               | Total 1 000 req | Media por req |
| ---------------------- | --------------- | ------------- |
| Servlet puro `/manual` | 7 477 ms        | ≈ 7,5 ms      |
| Spring MVC `/Person/1` | 7 453 ms        | ≈ 7,4 ms      |

- Conclusión hoy: diferencia insignificante (+-0.3%) --> el overhead de Spring MVC es mínimo para este caso.

## Reducir JAR eliminando starter que no uso.
````bash
mvn dependency:list | grep -i starter
````
### Resultado:
| Starter                       | Para qué lo usas           |
| ----------------------------- | -------------------------- |
| `spring-boot-starter-web`     | Tus endpoints REST         |
| `spring-boot-starter-tomcat`  | Servidor embebido          |
| `spring-boot-starter-json`    | Conversión JSON automática |
| `spring-boot-starter-logging` | Logs que ves en consola    |
| `spring-boot-starter`         | Core de Spring Boot        |
| `spring-boot-starter-test`    | Solo test (scope `test`)   |

Conclusión: No hay grasa que quitar. 

### Slim-JAR alternativo: quitar test del package

Para ello se empaqueta sin test, solo reducirá un poco de KBs.
````bash
mvn clean package -DskipTests
````
Vemos el tamaño nuevo con el comando:
````bash
ls -lh target/*.jar
````
El numero interno de JARs internos:
````bash
jar tf target/rebuild-day1-0.0.1-SNAPSHOT.jar | grep -c BOOT-INF/lib
````
