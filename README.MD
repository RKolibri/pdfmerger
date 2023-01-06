# pdfmerger

## Basic Spring webapp that merges any count of pdf files into one! 

[![Maven Package](https://github.com/RKolibri/pdfmerger/actions/workflows/maven.yml/badge.svg)](https://github.com/RKolibri/pdfmerger/actions/workflows/maven.yml)

##  Still in ALPHA MODE NOT USABLE!

### How to use

1. Start the Spring webapp
2. Select any number of PDF files from your device
3. Press the "Merge" button
4. Wait for the process to finish (you can track the progress with the loading bar)
5. Press the "Download" button to download the merged PDF file

### How to build

1. Clone the repository
2. Run `mvn package` in the root directory

### How to package

1. Build the project with `mvn package`
2. The jar file will be in the `target` directory

### How to run

1. Run the jar file with `java -jar pdfmerger-0.0.1-SNAPSHOT.jar`
2. Go to `http://localhost:8080` in your web browser

### Dockerization

You can use the provided `Dockerfile` and `docker-compose.yml` files to run the application in a Docker container.

1. Build the image with `docker build -t pdfmerger .`
2. Run the container with `docker-compose up`
3. Go to `http://localhost:8080` in your web browser

Note: The `/merged` folder in the container will be mapped to a volume on the host machine, so the merged PDF files will be stored on the host and persist after the container is stopped.
