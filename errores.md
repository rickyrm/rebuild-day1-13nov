### Errores encontrados al modificar el código

1. **Error al cambiar el puerto**: 
   - Con *server.port=8081* en /resource/application.properties no me da error.
2. **Error al eliminar el *@RestController***:
   - Muesta el siguiente mensaje:  
   *Whitelabel Error Page  
   This application has no explicit mapping for /error, so you are seeing this as a fallback. 
   Thu Nov 13 15:35:32 WET 2025 
   There was an unexpected error (type=Not Found, status=404).*
3. **Error al poner en el endpoint *return = null***:
   - Muestra el mensaje:  
     Whitelabel Error Page  
     This application has no explicit mapping for /error, so you are seeing this as a fallback.
     Thu Nov 13 15:57:21 WET 2025
     There was an unexpected error (type=Not Found, status=404).
     
