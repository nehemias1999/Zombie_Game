
# Zombie Game

## Descripcion del juego

### Objetivo del Juego

El objetivo principal del juego es evitar quedarse con el comodín, una carta especial sin número que no puede formar pareja. Los jugadores deben formar parejas de cartas con el mismo número para deshacerse de ellas y evitar el comodín.

Este juego permite a 2-4 jugadores formar parejas de cartas con el mismo número. El objetivo principal del juego es evitar quedarse con el comodín, que no tiene valor numérico y no puede formar pareja.

### Inicio del Juego

Se barajan todas las cartas a los jugadores. (incluido el comodin)

Cada jugador recibe sus cartas, verifica que las parejas existentes y las descarta en el mazo de parejas.

### Turnos de Juego

El jugador que comienza selecciona una carta del jugador de su derecha, sin verla la recibe y comprueba si formo una pareja con la nueva carta.

Si formo una pareja con la nueva carta, deja ambas cartas en el mazo de parejas.

Si no formo pareja, continua el siguiente jugador de la izquierda que todavia tenga cartas.

El juego sigue hasta que todos los jugadores hayan formado sus parejas y uno de ellos se quede con el comodín.

### Final del Juego

El juego termina cuando un jugador se queda unicamente con el comodín y los demas jugadores se quedaron sin cartas.

## Descripcion tecnica

### Características principales:

- **Arquitectura MVC**: El juego sigue una arquitectura MVC (Modelo-Vista_Controlador).
- **Patron de diseño Observer**: La capa de Modelo y la de controlador se comunican a traves de este patron.
- **Patron de diseño Builder**: La clase Mensaje utiliza este patron para instanciarse.
- **Mecanismo de comunicacion RMI**: El proyecto utiliza este mecanismo para simular Clientes (jugadores) y servidor (Modelo).
- **Modo multijugador**: El juego está diseñado para ser jugado por 2 a 4 jugadores.

## Instrucciones para Descargar y Ejecutar el Proyecto

### Descargar desde GitHub

1. Clona el repositorio en tu máquina local utilizando el siguiente comando:

```bash
git clone https://github.com/tu_usuario/zombie_game.git
```

### Ejecutar el Proyecto

1. Abre el proyecto con un Editor de codigo:

2. Compila la clase ServerApp (que por defecto se ejcuta en el puerto 10000)

3. Compila la clase ClientApp (Jugador) definiendo un puerto donde escuchara. Se tiene que crear una instancia por cada jugador.
