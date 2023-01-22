# PdfMerger
## Basic Spring MVC that merges any count of pdf files into one! 

[![CodeQL](https://github.com/RKolibri/pdfmerger/actions/workflows/codeql.yml/badge.svg)](https://github.com/RKolibri/pdfmerger/actions/workflows/codeql.yml)
![Docker Pulls](https://img.shields.io/docker/pulls/klevakoz96/pdfmerger?color=blueviolet&logo=docker&logoColor=blueviolet%20&style=for-the-badge)
[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)


## How to use   
1. Choose any number of pdf files to merge
2. Press the 'Merge' button
3. Wait for the files to be merged (a loading bar will be displayed)
4. Press the 'Download' button to download the merged file


## How to run
1. Download the latest release from the [releases](https://github.com/RKolibri/pdfmerger/releases) page
2. Run the jar file with the command `java -jar pdfmerger-main.jar`
3. Wait for the server to start
4. Access the server at `localhost:8080`
5. enjoy!

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
