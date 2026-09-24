# Programación Orientada a Objetos - TP2
## Documentación sobre el proyecto
Sobre los detalles de la implementación, el programa fue desarrollado en el IDE IntelliJ IDEA, con el SDK 21 Oracle OpenJDK 21.0.11, Language Level SDK default.

<img width="855" height="347" alt="image" src="https://github.com/user-attachments/assets/e967e158-b923-4177-9698-bd62a54d2eb1" />

El programa se ejecuta en la clase `AppSinScanner`, en la cual está el código que genera el resultado consignado en el punto 4:

- Se crean estudiantes.
- Se crean eventos universitarios cada uno con su sala y sus actividades.
- Se inscribe a los estudiantes en distintas actividades de cada evento.
- Se confirman algunas de las inscripciones de estudiantes en las actividades de cada evento.
- Se genera un ticket de acceso por cada inscripción confirmada.
- Se inicia un proceso concurrente para enviar todos los tickets generados.
- Mientras el hilo de envío de tickets se ejecuta, el hilo principal debe continuar mostrando
por consola los datos del evento, sus actividades y los estudiantes inscriptos.
- Debe evidenciarse en consola que existen dos flujos de ejecución: el hilo principal del
programa y el hilo encargado del envío de tickets

También está la clase `App`, que incorpora un Scanner para crear todos los objetos manualmente. (En realidad la clase `AppSinScanner` también usa un scanner a la hora de ingresar datos específicos de cada actividad `[disertante, requiereNotebook, nivel]`, pero la generación de todos los objetos está hardcodeada, a diferencia de `App`)

## Clonar
Para clonar el repositorio en IntelliJ IDEA, desde el menú inicial, ir al botón `Clone Repository`

<img width="1606" height="66" alt="image" src="https://github.com/user-attachments/assets/2220cf09-ab65-49dc-80ae-f5f136c4aeae" />

En `URL`, pegar la dirección del repositorio

<img width="991" height="310" alt="image" src="https://github.com/user-attachments/assets/72cb4731-969c-489e-9206-045b5c2cf221" />

Finalmente, presionar el botón `Clone` y listo.

## Salida
Al ejecutar la clase `AppSinScanner`, se muestra la siguiente salida, que también se puede ver en el archivo `salida.png`

(Primero se ingresan 3 lineas que corresponden a los datos específicos de cada actividad)

```
Ingrese el nombre del disertante: Saul
Requiere notebook? (S/N): N
Ingrese el nivel del curso: 16
[main]: Mostrando datos...
=================== Datos de evento ===================

[Thread-Tickets]: Enviando tickets...
Id del evento: 0
Título: Evento de Sistemas
Costo estimado: 0.0$
¿Es gratuito?: Sí
-------------------- Sala asignada --------------------
Id: 0
Nombre: Sala Sistemas
--------------------- Actividades ---------------------
[Thread-Tickets] Enviando ticket TK-0 al estudiante Mateo legajo 53000. Corresponde a la actividad: Charla de sistemas. El ticket fue emitido el 2026-09-24
[Thread-Tickets] Enviando ticket TK-1 al estudiante Juan legajo 53001. Corresponde a la actividad: Taller de Programación. El ticket fue emitido el 2026-09-24
[Thread-Tickets] Enviando ticket TK-2 al estudiante Pedro legajo 53003. Corresponde a la actividad: Curso de oratoria. El ticket fue emitido el 2026-09-24
- Charla: Charla de sistemas (id=0) - Cupo máximo: 50
======= Inscripciones =======
[Thread-Tickets]: Tickets enviados correctamente
| Hay un total de 2 inscripciones
| -----
| Fecha: 2026-09-24
| Estado: Confirmado
| Actividad: Charla de sistemas
| Datos del estudiante:
| Legajo: 53000
| Nombre: Mateo
| -----
| Fecha: 2026-09-24
| Estado: Registrado
| Actividad: Charla de sistemas
| Datos del estudiante:
| Legajo: 53001
| Nombre: Juan
| -----
----------------------------------------
- Taller: Taller de Programación (id=1) - Cupo máximo: 200
======= Inscripciones =======
| Hay un total de 4 inscripciones
| -----
| Fecha: 2026-09-24
| Estado: Registrado
| Actividad: Taller de Programación
| Datos del estudiante:
| Legajo: 53000
| Nombre: Mateo
| -----
| Fecha: 2026-09-24
| Estado: Confirmado
| Actividad: Taller de Programación
| Datos del estudiante:
| Legajo: 53001
| Nombre: Juan
| -----
| Fecha: 2026-09-24
| Estado: Registrado
| Actividad: Taller de Programación
| Datos del estudiante:
| Legajo: 53002
| Nombre: Pablo
| -----
| Fecha: 2026-09-24
| Estado: Registrado
| Actividad: Taller de Programación
| Datos del estudiante:
| Legajo: 53003
| Nombre: Pedro
| -----
----------------------------------------
- Curso: Curso de oratoria (id=2) - Cupo máximo: 100
======= Inscripciones =======
| Hay un total de 2 inscripciones
| -----
| Fecha: 2026-09-24
| Estado: Registrado
| Actividad: Curso de oratoria
| Datos del estudiante:
| Legajo: 53002
| Nombre: Pablo
| -----
| Fecha: 2026-09-24
| Estado: Confirmado
| Actividad: Curso de oratoria
| Datos del estudiante:
| Legajo: 53003
| Nombre: Pedro
| -----
----------------------------------------
=======================================================

[main]: Datos mostrados
```
