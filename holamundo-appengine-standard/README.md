# Servlet sencillo en Google App Engine Standard

Si no lo tienes ya, crea un nuevo proyecto de GCP, una aplicación de Google App Engine y habilita la facturación en ese proyecto.

Descarga el [SDK de Google Cloud](https://cloud.google.com/sdk/docs/), descomprímelo y actualiza el $PATH. Ejecuta:

    gcloud init

Clona después este repositorio. 

Para ejecutar la aplicación localmente: 

    mvn appengine:devserver

Para desplegar en la nube:

    mvn clean appengine:update -Dappengine.appId=<your-project-id> \
        -Dappengine.version=<version>

Más [información](https://cloud.google.com/java/getting-started-appengine-standard/tutorial-app).


