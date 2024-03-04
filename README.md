### Technology stack

* JDK 17
* Spring Boot
* MySQL
* Maven
* Kafka
* Docker

Change the port number in `application-dev.yml` file if needed. Default port number is `8080`

### Running the app

Build the `message_reader.jar` file<br>
`mvn package`<br><br>
Run <br>
`java -jar target/message_reader.jar`<br><br>

## Run Application Using Docker

### Prerequisites

Before you can run the application, please ensure you have installed Docker Desktop on your system.

Here are the steps to run the application using Docker:

1. Open a terminal or command prompt.
2. Navigate to the project's root directory where the `docker-compose.yml` file is located.
3. Run the command `docker-compose up`.

Docker will begin to pull the following images (if they are not already installed in your local Docker cache):

- JDK 17
- MySQL
- Kafka (confluentinc/cp-kafka image)
- ZooKeeper (confluentinc/cp-zookeeper image)
- Kafka UI (provectuslabs/kafka-ui image)

Please note that the first run may take some time as Docker needs to download the necessary images mentioned above. Once
these images are downloaded and stored in your Docker cache, subsequent runs will be much faster.

After Docker Compose has finished starting the services, you can access the running application. For example, you can
access Kafka UI via your web browser at `http://localhost:8090/`.

Always ensure to stop the containers once you are done by running `docker-compose down`. This ensures that any changes
you made to the docker-compose file are picked up next time you run `docker-compose up`.