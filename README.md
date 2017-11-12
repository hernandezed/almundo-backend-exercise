# almundo-backend-exercise

## Consigna
Existe un call center donde hay 3 tipos de empleados: operador, supervisor y director. El proceso de la atención de una llamada telefónica en primera instancia debe ser atendida por un operador, si no hay ninguno libre debe ser atendida por un supervisor, y de no haber tampoco supervisores libres debe ser atendida por un director.

## Requerimientos

* Diseñar el modelado de clases y diagramas UML necesarios para documentar y comunicar el diseño.
* Debe existir una clase Dispatcher encargada de manejar las llamadas, y debe contener el método dispatchCall para que las asigne a los empleados disponibles.
* La clase Dispatcher debe tener la capacidad de poder procesar 10 llamadas al mismo tiempo (de modo concurrente).
* Cada llamada puede durar un tiempo aleatorio entre 5 y 10 segundos.
* Debe tener un test unitario donde lleguen 10 llamadas.

Diagrama de clases

![](https://raw.githubusercontent.com/hernandezed/almundo-backend-exercise/activemq-impl/docs/callcenter.png)

Uso:
* La aplicacion al correrla, queda escuchando al puerto 8080.
* Cargar empleados: Realizar un POST a http://localhost:8080/api/employees
```
[{"name":"Tomas", "priority":0},
{"name":"Lucas", "priority":0},
{"name":"Maria", "priority":0},
{"name":"Rocio", "priority":0},
{"name":"Juan", "priority":0},
{"name":"Martin", "priority":0},
{"name":"Patricia", "priority":1},
{"name":"Monica", "priority":1},
{"name":"Raul", "priority":1},
{"name":"Ana", "priority":2}]
```
* Cargar llamada: Realizar un POST a http://localhost:8080/api/calls
```
{"id": idLlamada}
```
* Ver llamadas terminadas: Realizar un GET a http://localhost:8080/api/calls/finished
