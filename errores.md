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