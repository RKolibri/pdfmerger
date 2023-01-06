# pdfmerger
## Basic Spring webapp that merges any count of pdf files into one! 
[![Maven Package](https://github.com/RKolibri/pdfmerger/actions/workflows/maven.yml/badge.svg)](https://github.com/RKolibri/pdfmerger/actions/workflows/maven.yml)
##  Still in ALPHA MODE NOT USABLE!

## How to use
1. Choose any number of pdf files to merge
2. Press the 'Merge' button
3. Wait for the files to be merged (a loading bar will be displayed)
4. Press the 'Download' button to download the merged file

## How to build
1. Clone the repository
2. Run `mvn package` in the root directory

## How to package
1. Run `mvn package` in the root directory
2. The packaged jar file will be located in the `target` directory

## How to run
1. Navigate to the directory where the jar file is located
2. Run `java -jar pdfmerger.jar`

## Dockerization
To Dockerize the app, you can use the provided `Dockerfile` and `docker-compose.yml` files.

1. Build the Docker image by running `docker build -t pdfmerger .` in the root directory
2. Run the app in a Docker container by running `docker-compose up`
3. The app will be available at `http://localhost:8080`

### Docker Compose
To use the `docker-compose.yml` file, you will need to have [Docker Compose](https://docs.docker.com/compose/) installed.

The `docker-compose.yml` file includes a volume for the `/merged` directory on the container side. This directory is used to store the merged PDF files.
