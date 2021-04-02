# Aplicación con Amazon Gateway, EC2 y S3

[![danielrincon-m](https://circleci.com/gh/danielrincon-m/AREP_LAB4.svg?style=svg)](https://app.circleci.com/pipelines/github/danielrincon-m/AREP_LAB4)
<!-- [![Heroku](img/heroku_long.png)](https://nanospring.herokuapp.com/nspapp/register) -->

## Descripción ☁️

En este laboratorio será desarrollado un prototipo de aplicación web basada en microservicios, para la cual utilizaremos la nube de Amazon. El proyecto estará compuesto por una página web pública corriendo en un contenedor Amazon S3 con la cual podrá interactuar el usuario, un servicio en Spark montado sobre una máquina virtual Amazon EC2, y un servicio de API Gateway, el cual será el puente entre los otros dos componentes.

La arquitectura de los servicios se muestra a continuación:

![Arquitectura](img/arquitectura.png)


## Implementación 🛡️

La arquitectura de la aplicación se basa en la abstracción de una API por medio del API Gateway de Amazon, y la presentación de una interfaz amigable con el usuario por medio de una página web.

Vamos a realizar un breve recorrido por cada uno de los servicios.

### Temperature Service

Se trata de un servicio web simple que responde a una sola petición GET en la ruta */convert/farenheit/celsius*, el servicio espera un parámetro: la temperatura que se desea convertir, el programa valida que el dato ingresado sea válido, y que se haya ingresado un dato, de lo contrario retorna mensajes de error.

El servicio web está construido sobre [Spark Java](#herramientas-utilizadas-%EF%B8%8F), fué empaquetado y subido a la máquina virtual de AWS para su ejecución.

### API Gateway

Este servicio abstrae la API del [Temperature Service](#Temperature-Service), creando una interfaz para que otras aplicaciones puedan acceder al servicio, quitando el riesgo de que tengan un acceso mas allá del que se espera al servidor del servicio.

### Web Service

Se trata de una aplicación web amigable con el usuario, la cual nos presenta un formulario solicitando el valor de temperatura que deseamos convertir, en la misma ventana será presentada la respuesta una vez enviemos nuestra solicitud.

## Reporte de pruebas ⭕

El proyecto fué probado de dos maneras, las cuales se podrán ver a continuación:

### Pruebas unitarias

Se utilizó el framework [REST Assured](#herramientas-utilizadas-%EF%B8%8F) para realizar pruebas sobre la API REST desarrollada, se comprobó que las peticiones estuviesen siendo procesadas y que las respuestas de las conversiones fueran correctas, a continuación, podremos ver el código fuente de las pruebas:

``` Java
@Test
public void valuesTest() {
    HashMap<Float, Float> values = new HashMap<>();
    values.put(0f, -17.7778f);
    values.put(5f, -15f);
    values.put(8f, -13.3333f);
    values.put(20f, -6.66667f);
    values.put(55f, 12.7778f);
    values.put(78.152f, 25.64f);
    values.put(158f, 70f);
    values.put(2548.8556f, 1398.25311111f);

    values.forEach((key, value) -> {
        JsonConfig jsonConfig = JsonConfig.jsonConfig()
                .numberReturnType(JsonPathConfig.NumberReturnType.BIG_DECIMAL);

        given().config(RestAssured.config().jsonConfig(jsonConfig)).port(5000)
                .param("value", key).when().get("/convert/farenheit/celsius").then()
                .body("farenheitDegrees",
                        closeTo(BigDecimal.valueOf(key), BigDecimal.valueOf(0.01f)))
                .body("celsiusDegrees",
                        closeTo(BigDecimal.valueOf(value), BigDecimal.valueOf(0.01f)));
    });
}
```

### Pruebas de funcionamiento

Las pruebas de funcionamiento se realizaron con todo el sistema montado, probando diferentes valores, tanto válidos como no válidos, se realizaron de manera similar al estilo mostrado en el [Video de Demostración](#video-de-demostración-).

## Video de demostración 📹

Se realizó un video demostrando y explicando el funcionamiento de todo el sistema, este video puede ser encontrado [AQUÍ](demostracion.mp4).

## Descarga del proyecto ⬇️

Clone el proyecto utilizando el siguiente comando:

```
git clone https://github.com/danielrincon-m/AREP_LAB7.git
```

## Correr las pruebas unitarias 🧪

### Prerrequisitos

Un IDE que soporte proyectos Java, o una instalación de Maven en su sistema, puede obtenerlo desde
la [página oficial.][mvnLink]

### Ejecución de pruebas

Las pruebas pueden ser ejecutadas desde la sección de pruebas de su IDE o si tiene maven puede navegar a la carpeta principal de TempConverter y ejecutar el comando

```
mvn test
```

## Documentación del código fuente 🌎

La documentación del proyecto puede ser encontrada en la carpeta [TempConverter/docs](TempConverter/docs).

También puede ser generada con Maven, clonando el proyecto y ejecutando el siguiente comando en la carpeta TempConverter:

```
mvn javadoc:javadoc
```

## Documento de diseño 📄

Este laboratorio no cuenta con documento de diseño.

## Herramientas utilizadas 🛠️

* [Visual Studio Code](https://code.visualstudio.com/) - IDE de desarrollo
* [Maven](https://maven.apache.org/) - Manejo de Dependencias
* [JUnit](https://junit.org/junit4/) - Pruebas unitarias
* [GitHub](https://github.com/) - Repositorio de código
* [Spark](https://sparkjava.com/) - Framework web
* [AWS](https://aws.amazon.com/es/) - Despliegue en la nube
* [REST Assured](https://rest-assured.io/) - Herramienta de pruebas para el API REST

## Autor 🧔

**Daniel Felipe Rincón Muñoz:** *Planeación y desarrollo del proyecto* -
[Perfil de GitHub](https://github.com/danielrincon-m)

## Licencia 🚀

Este proyecto se encuentra licenciado bajo **GNU General Public License** - consulte el archivo [LICENSE.md](LICENSE.md)
para más detalles.

<!-- 
## Acknowledgments 

* Hat tip to anyone whose code was used
* Inspiration
* etc
-->

[gitLink]: https://git-scm.com/downloads
[mvnLink]: https://maven.apache.org/download.cgi
