# ReconocimientoFacialApp

La aplicación móvil ReconocimientoFacialApp fue desarrollada utilizando Android Studio como entorno de desarrollo,
y se utilizó el lenguaje de programación Java y XML para las interfaces gráficas.

A continuación, se detallan los pasos que se siguieron para la reconstrucción del proyecto:

## 1. Construir el proyecto aplicando una estructura de organización, uno de los aspectos basados en Clean Architecture

> **¡Atención!**
>
> Asi quedara la estructura final ya con el codigo de todas las clases.
> 
> Se debe seguir un orden para la creacion de las clases, dicho orden se mostrará más adelante.

#### Carpeta data

Esta carpeta contiene las clases relacionadas con el manejo de datos. Siguiendo los principios de Clean Architecture,
esta capa se encarga de la abstracción y gestión de los datos, como solicitudes y respuestas de la red, acceso a la base de datos, etc.

- data
  - EnrollRequest.java Clase que representa la solicitud de enrolamiento facial.
  - EnrollResponse.java Clase que representa la respuesta de enrolamiento facial.

#### Carpeta domain

En esta carpeta se encuentran las clases relacionadas con la lógica de dominio de la aplicación.
Aquí se definen las entidades principales del negocio y las reglas de negocio. Por ejemplo, 
la clase "FaceRecognition" encapsula la lógica del reconocimiento facial, que es un aspecto central de la funcionalidad de la aplicación.

- domain
  - FaceRecognition.java: Clase principal que encapsula la lógica del reconocimiento facial.
  - RecognizeRequest.java: Clase que representa la solicitud de reconocimiento facial.
  - RecognizeResponse.java: Clase que representa la respuesta del proceso de reconocimiento facial.
  - ServerAddress.java: Clase que almacena la dirección del servidor utilizado para el reconocimiento facial.

#### Carpeta ui

 Esta carpeta contiene las clases relacionadas con la interfaz de usuario de la aplicación.
 Contiene subcarpetas para poder separarlas por funcionalidades.

- ui

##### subcarpeta activities:

Contiene las actividades principales de la aplicación.

  - activities
    - CameraActivity.java: Actividad que controla la cámara y captura las imágenes faciales.
    - CommonActivity.java: Actividad común que proporciona funcionalidades compartidas por otras actividades.
    - MainActivity.java: Actividad principal de la aplicación que muestra la pantalla de inicio.

##### supcarpeta adapters

Contiene adaptadores utilizados en la interfaz de usuario.

  - adapters
    - CameraManager.java: Clase que gestiona la cámara y la captura de imágenes faciales.
    - Overlay View.java: Clase que muestra una superposición en la interfaz para resaltar las áreas faciales.

##### subcarpeta fragments

Contiene los fragmentos utilizados en la interfaz de usuario.

  - fragments
    - CommonFragment.java: Fragmento común que proporciona funcionalidades compartidas por otros fragmentos.
    - EnrollFragment.java: Fragmento utilizado para el proceso de enrolamiento facial.
    - MainFragment.java: Fragmento principal que muestra las opciones principales de la aplicación.
    - RecognizeFragment.java: Fragmento utilizado para el proceso de reconocimiento facial.
    - SettingsFragment.java: Fragmento que muestra y permite modificar la configuración de la aplicación.

#### carpeta utils

Esta carpeta contiene clases relacionadas con utilidades generales.

 - util
    - Enums.java: Clase que almacena enumeraciones utilizadas en la aplicación.
    - OnBackPressed.java: Interfaz utilizada para manejar el evento de retroceso en la aplicación.
    - Utils.java: Clase que proporciona funciones de utilidad para diversas tareas.

<!-- ## 2. Agregar los archivos y recursos necesarios para la app en la carpeta res y sus subcarpetas respectivamente.

 -->
