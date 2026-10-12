# ing-software
Proyecto de Ingeniería de Software

Se cargó la base del proyecto con la cual vamos a empezar a trabajar, toda la estructura es copiada del proyecto de crischan (no es cierto (bueno solo un poquito)). 

Para la parte del backend se encuentra la imagen 3.9-eclipse-temurin-21 con maven lista para utilizar Springboot.
Para el forntend se encuentra node alpine 22 para react con vite.


Al realizar pull al proyecto:

    1. crear un archivo llamado .env
    2. copiar las variables de entorno de el archivo .env.template al archivo recientemente creado.
    3. al construir por primera vez el proyecto ejecutar el comando
    
            docker compose up --build (SOLO LA PRIMERA VEZ QUE SE VAYA A INICIAR)
    
    Y listo.

Para comenzar a trabajar dentro del proyecto en la terminal se coloca:

            docker compose up -> Iniciar los servicios

Y para cerrarlo se utiliza

            CTRL + c -> Para detener servicios y devolver el control a la termial
            docker compose down -> Detener los contenedores

La mayor parte de la configuración de los puertos y conecciones entre las bases de datos y el backend me la hizo chatgepete, si necesitan cambiar algo PORFA avisen, paro de la vida.

PUERTOS:

Backend:
        8080
Frontend:
        5173
Database:
        5432


NOTA:
    Esta madre jala en mi máquina. Necesitamos checar cualquier cosa que falle en las suyas (con Claudio claro que si).

victorfrontend