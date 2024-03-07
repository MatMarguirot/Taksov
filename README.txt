Taksov - Aplicación para la planificación de rutinas de entrenamiento
Taksov es una aplicación backend desarrollada con Spring Boot que te permite crear y gestionar rutinas de entrenamiento. Puedes implementarla utilizando Docker. La aplicación utiliza autenticación basada en roles para controlar el acceso a las funcionalidades.

## Características Principales

- **Autenticación por roles**: Los usuarios pueden tener diferentes roles, permitiendo una gestión de accesos y permisos personalizada.
- **Gestión de sesiones de entrenamiento**: Los usuarios pueden crear y gestionar sesiones de entrenamiento, asociando ejercicios y categorizándolos por grupos musculares.
- **Asociación de ejercicios a grupos musculares**: Cada ejercicio está asociado a un grupo muscular, facilitando la organización de las rutinas de entrenamiento.
- **Despliegue en Docker**: Taksov puede ser desplegado fácilmente en entornos Docker, asegurando un entorno de ejecución consistente y aislado.

## Requisitos

- Java 11+
- Maven
- Docker (Opcional, para despliegue)

## Instalación

1. Clona el repositorio: `git clone https://github.com/MatMarguirot/taksov.git`
2. Navega al directorio del proyecto: `cd taksov`
3. Ejecuta el proyecto con Maven: `mvn spring-boot:run`

## Instalación con Docker

Para instalar con Docker puedes ejecutar uno de los siguientes scripts, ambos en la carpeta raíz del proyecto:
`start-dev.sh`: Inicia la aplicación en modo development. Contiene secreto JWT hardcoded.
`start-prod`: Inicia la aplicación en modo producción. Debe especificarse el secreto JWT en el argumento previo a `docker-compose`.
- Ejemplo: `ENV_FILE=./.env.dev JWT_SECRET=FIDJQ92P3J90P1283RH90P8HF2P938HF09182H3F098H1P39FI docker-compose up -d --build`
Si deseas añadir tus propias variables de entorno, debes modificar los archivos .env.dev (development) y .env.prod(producción). Alternativamente puedes añadir tu propio archivo de configuración en la raíz del proyecto.
