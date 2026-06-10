
* Ensayo: 10/06
* Presentación oficial: 23/06

15-20 min

Diapos con Google Slides o Canvas.

* logo y nombre
* equipo
* contexto objetivo proyecto
* DEMO
  * anónimo
  * user
  * admin
* github y proyecto colaborativo + Tecnologías (Java 25 + Spring 4)
* Desarrollos futuros
* Conclusiones


## Prompt Gamma

### largo:

Crea una presentación profesional y visual en ESPAÑOL para presentar un proyecto académico
de desarrollo de software ante un tribunal/clase. Objetivo: 12-14 tarjetas para una exposición
de 15-20 minutos con una DEMO en vivo de la aplicación.

PROYECTO: "Restaurantes" — aplicación web donde los usuarios pueden descubrir restaurantes,
ver sus cartas de platos, leer y escribir reseñas, marcar favoritos y hacer pedidos paso a paso.
Es una app web renderizada en servidor (HTML con Thymeleaf), NO una SPA ni una API JSON.
Tiene tres tipos de usuario: visitante anónimo, usuario registrado y administrador.

TONO: profesional pero cercano y claro, pensado para exponer en directo.
ESTILO VISUAL: moderno y gastronómico — tonos cálidos (naranja/rojo/verde), tipografía limpia,
iconos y fotografía de comida y restaurantes de stock. Estética tipo dashboard, poco texto por
tarjeta, mucho apoyo visual. En las tarjetas de DEMO usa MUY poco texto (son guion de la demo en vivo).

Genera EXACTAMENTE estas tarjetas en este orden:

1. PORTADA — Título grande "Restaurantes" con un logo/wordmark sencillo (icono de cubierto o plato).
   Subtítulo: "Descubre, reseña y pide de tus restaurantes favoritos". Pie: nombre del equipo y curso.

2. EL EQUIPO — Tarjeta con espacio para 3-4 integrantes (foto/avatar, nombre y rol: backend,
   frontend/vistas, base de datos, testing). [Dejar como plantilla para rellenar nombres].

3. CONTEXTO Y OBJETIVO — El problema: encontrar restaurantes, decidir dónde comer y pedir es disperso.
   Objetivo: una única app web donde explorar el catálogo, leer reseñas reales, guardar favoritos
   y hacer pedidos, con un panel de administración para gestionarlo todo.

4. VISIÓN GENERAL — Qué ofrece la app en 4-5 bloques con iconos: Catálogo y filtros · Reseñas y
   puntuaciones (1-5) · Favoritos · Pedidos paso a paso · Panel de administración. Todo con
   autenticación real y control de acceso por roles.

5. DEMO · VISITANTE ANÓNIMO — (guion breve, viñetas cortas) Explorar el catálogo de restaurantes ·
   Filtros combinables (tipo de cocina, precio medio, nombre) · Ficha de restaurante con su carta y
   reseñas · Ficha de plato · Solo se muestran restaurantes activos.

6. DEMO · USUARIO REGISTRADO — (guion breve) Registro e inicio de sesión · Escribir una reseña con
   puntuación · Marcar favoritos · Crear un pedido paso a paso (añadir platos, recálculo del total,
   finalizar con datos de pago y propina) · Perfil con estadísticas (reseñas, pedidos, gasto).

7. DEMO · ADMINISTRADOR — (guion breve) Panel de administración · CRUD de restaurantes y platos con
   imagen · Moderar reseñas · Gestionar usuarios y roles · Borrado lógico (no se pierde el histórico).

8. ARQUITECTURA Y TECNOLOGÍAS — Destaca Java 25 y Spring Boot 4. Lista con logos/iconos:
   Spring MVC + Thymeleaf (server-side) · Spring Data JPA + H2 en memoria · Spring Security
   (login, roles, BCrypt) · Spring Validation ·
   @ControllerAdvice + @ModelAttribute · Páginas de error personalizadas (403/404/500) ·
   Subida de imágenes · Datos de demo cargados al arrancar.

11. TRABAJO COLABORATIVO Y GITHUB — Repositorio en GitHub · Trabajo por ramas y pull requests ·
    Integración continua con GitHub Actions que compila en cada push · Cómo nos repartimos el trabajo.

12. DESARROLLOS FUTUROS — Exponer una API REST · Base de datos real (PostgreSQL/MySQL) con migraciones ·
    Búsqueda y paginación avanzadas · Notificaciones de estado del pedido.

13. CONCLUSIONES — Qué hemos construido y qué hemos aprendido (Spring Boot, seguridad por roles,
    JPA, trabajo en equipo con Git). Una app web completa y funcional de principio a fin.

14. CIERRE — "¡Gracias!" + turno de preguntas. Datos de acceso a la demo: usuario admin/admin y
    user/user en http://localhost:8080.

---
Consejos rápidos en Gamma

- En Configuración, pon "Cantidad de texto por tarjeta" en Conciso/Medio (las diapos de demo deben quedar limpias).
- Activa "Conservar mi estructura" (Preserve my structure) para que respete las 14 tarjetas tal cual.
- Tras generar, sustituye los placeholders del equipo y, si tienes, sube capturas reales para las tarjetas 5-7 en lugar de la demo en vivo como respaldo.


### reducido:


Crea una presentación profesional y visual en ESPAÑOL de EXACTAMENTE 4 tarjetas para presentar
un proyecto académico de desarrollo de software.

PROYECTO: "Restaurantes" — aplicación web (renderizada en servidor con Thymeleaf, no es una SPA)
donde se puede descubrir restaurantes, ver sus cartas, leer y escribir reseñas, marcar favoritos
y hacer pedidos. Tiene tres roles: visitante anónimo, usuario registrado y administrador.

TONO: profesional pero cercano. ESTILO VISUAL: moderno y gastronómico, tonos cálidos
(naranja/rojo/verde), tipografía limpia, iconos y fotografía de comida/restaurantes, poco texto
por tarjeta.

Genera EXACTAMENTE estas 4 tarjetas:

1. PORTADA — Título grande "Restaurantes" con un logo/wordmark sencillo (icono de plato o cubierto).
   Subtítulo: "Descubre, reseña y pide de tus restaurantes favoritos". Pie: nombre del equipo y curso.

2. CONTEXTO Y OBJETIVO — El problema: encontrar dónde comer, decidir y pedir está disperso.
   Objetivo: una única app web para explorar el catálogo, leer reseñas reales (1-5), guardar
   favoritos y hacer pedidos, con panel de administración. Todo con autenticación real y roles.

3. DEMO EN VIVO — (guion breve, viñetas cortas, poco torar catálogo
   favoritos y hacer pedidos, con panel de administración. Todo con autenticación real y roles.

3. DEMO EN VIVO — (guion breve, viñetas cortas, poco torar catálogo
   y filtros · Usuario: reseñas, favoritos y pedido paso a paso · Administrador: CRUD de
   restaurantes/platos y gestión de usuarios.

4. TECNOLOGÍAS — Destaca Java 25 y Spring Boot 4. Thymeleaf,
   Spring Data JPA + H2, Spring Security (roles, BCrypt), Bootstrap 5.3, GitHub Actions (CI).



## Prompt usado:




```
Crea una presentación profesional y visual en ESPAÑOL para presentar un proyecto académico de
desarrollo de software ante un tribunal. Genera 7 diapositivas (+1 divisoria opcional).
NO generes diapositivas para la demo: la demo es EN VIVO sobre la app real.

PROYECTO: "FoodDevs Restaurantes" — aplicación web (renderizada en servidor con Thymeleaf, no es una SPA)
donde se puede descubrir restaurantes, ver sus cartas, leer y escribir reseñas, marcar favoritos
y hacer pedidos. Tres roles: visitante anónimo, usuario registrado y administrador.

TONO: profesional, limpio y minimalista. Poco texto por diapositiva.

=== ESTILO Y PALETA DE MARCA (FoodDevs) ===
- Paleta de MARCA: BLANCO (fondo principal), VERDE MENTA de FoodDevs (color de marca: bloques,
  acentos, barras e iconos) y NEGRO (texto y detalles). Color secundario neutro: SLATE / gris
  azulado (textos secundarios, líneas y fondos suaves de tarjeta).
- Diseño LIMPIO: fondos blancos (como mucho, una diapo puntual con fondo verde menta), mucho
  espacio en blanco, tipografía clara y geometría sencilla. NADA de fondos a sangre con foto ni
  capas oscuras.
- Apoyo visual con ICONOS LINEALES coherentes (en negro o verde menta) y bloques de color, no con
  fotografía. Si en alguna diapo quieres una foto, que sea CONTENIDA (dentro de una tarjeta con
  esquinas redondeadas), luminosa y nítida; nunca a pantalla completa.

=== QUÉ NO GENERAR CON IA ===
- Texto dentro de la imagen, logos de marcas ni capturas de interfaces (quedan ilegibles o
  deformados). Para los logos de tecnologías usa los LOGOS OFICIALES de la biblioteca del editor
  (buscándolos por nombre), no imágenes generadas.
- El LOGO DEL EQUIPO (FoodDevs) lo subo yo como archivo: NO lo generes, deja un hueco limpio.
- Rostros del equipo: NO los generes; usa avatares circulares vacíos para fotos reales.

=== DIAPOSITIVAS ===

1. PORTADA (logo y nombre) — Arriba y centrado, un hueco destacado para el LOGO del equipo
   "FoodDevs" (lo subo yo, archivo logo.png). Debajo, título grande "Restaurantes" + subtítulo
   "Descubre, reseña y pide de tus restaurantes favoritos". Pie con el equipo (FoodDevs) y el curso.
   DISEÑO: fondo BLANCO. Título "Restaurantes" en negro con un acento en verde menta; subtítulo en
   slate. Como único adorno, una forma o banda geométrica en verde menta. Sin foto ni capas oscuras.

2. EQUIPO — Subtítulo destacado: "Equipo full-stack: todos hemos trabajado en vistas, controladores,
   servicios, repositorios y base de datos". Espacio para 3-4 integrantes: avatar circular vacío +
   nombre (SIN roles separados, porque todos hemos tocado todo). [Plantilla para rellenar].
   DISEÑO: fondo BLANCO. Avatares como círculos placeholder con borde en verde menta; nombres en
   negro y texto secundario en slate. NADA de caras generadas por IA.

3. EL PROYECTO — Problema: decidir dónde comer, comparar y pedir está disperso. Objetivo: una única
   app web para explorar el catálogo, leer reseñas reales (1-5), guardar favoritos y hacer pedidos,
   con panel de administración. Todo con autenticación real y control de acceso por roles.
   DISEÑO: fondo BLANCO. Representa el flujo con tres iconos lineales en verde menta
   (explorar → reseñar → pedir), texto en negro y matices en slate. Sin foto.

4. FUNCIONALIDADES GENERALES — 5 bloques con icono y una línea cada uno: Catálogo y filtros ·
   Reseñas y puntuaciones (1-5) · Favoritos · Pedidos paso a paso · Panel de administración.
   DISEÑO: fondo BLANCO; 5 tarjetas con icono lineal en verde menta (lupa, estrella, corazón,
   ticket/carrito, engranaje) y texto en negro. Fondos de tarjeta en slate muy claro. Sin foto.

5. (OPCIONAL) DIVISORIA "DEMO EN VIVO" — Solo el texto grande "DEMO EN VIVO".
   DISEÑO: fondo VERDE MENTA (color de marca) con el texto en negro centrado. Limpio, sin foto.

6. REPOSITORIO GITHUB + TECNOLOGÍAS — Izquierda: trabajo colaborativo en GitHub (ramas, pull
   requests, integración continua con GitHub Actions que compila en cada push). Derecha: stack.
   DISEÑO: fondo BLANCO; rejilla con los LOGOS OFICIALES (de la biblioteca del editor, NO generados)
   de Java, Spring Boot, Thymeleaf, H2, Spring Security, Bootstrap y GitHub Actions. Separadores y
   acentos en verde menta, texto en negro.

7. DESARROLLOS FUTUROS — 4 mejoras con icono: Notificaciones, paginación
   DISEÑO: fondo BLANCO; cada mejora con un icono lineal en verde menta y texto en negro, en una
   lista o línea de tiempo limpia. Sin foto.

8. CONCLUSIONES — Qué hemos construido (app web completa y funcional) y qué hemos aprendido
   (Spring Boot, seguridad por roles, JPA, trabajo en equipo con Git). Cierre: "¡Gracias!" + turno
   de preguntas. Datos de demo: admin/admin y user/user en http://localhost:8080.
   DISEÑO: cierre limpio con fondo VERDE MENTA (o blanco), "¡Gracias!" en negro grande y los datos
   de demo en slate. Sin foto.
```