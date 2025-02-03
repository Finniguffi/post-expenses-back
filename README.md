# post-expenses-back

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: <https://quarkus.io/>.

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at <http://localhost:8080/q/dev/>.

## Packaging and running the application

The application can be packaged using:

```shell script
./mvnw package
```

It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:

```shell script
./mvnw package -Dquarkus.package.jar.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using:

```shell script
./mvnw package -Dnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```shell script
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/post-expenses-back-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult <https://quarkus.io/guides/maven-tooling>.

## Related Guides

- REST ([guide](https://quarkus.io/guides/rest)): A Jakarta REST implementation utilizing build time processing and Vert.x. This extension is not compatible with the quarkus-resteasy extension, or any of the extensions that depend on it.

## Provided Code

### REST

Easily start your REST Web Services

[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)

## Running Tests

You can run the tests using:

```shell script
./mvnw test
```

The test reports are generated in the `target/surefire-reports` directory.

## Docker Support

You can build a Docker image using the provided Dockerfile:

```shell script
docker build -f src/main/docker/Dockerfile.jvm -t post-expenses-back .
```

To run the application in a Docker container:

```shell script
docker run -i --rm -p 8080:8080 post-expenses-back
```

## Environment Variables

The application supports the following environment variables:

- `JAVA_DEBUG_PORT`: Port used for remote debugging. Defaults to 5005.
- `CONTAINER_CORE_LIMIT`: A calculated core limit.
- `CONTAINER_MAX_MEMORY`: Memory limit given to the container.
- `GC_MIN_HEAP_FREE_RATIO`: Minimum percentage of heap free after GC to avoid expansion.
- `GC_MAX_HEAP_FREE_RATIO`: Maximum percentage of heap free after GC to avoid shrinking.
- `GC_TIME_RATIO`: Specifies the ratio of the time spent outside the garbage collection.
- `GC_ADAPTIVE_SIZE_POLICY_WEIGHT`: The weighting given to the current GC time versus previous GC times.
- `GC_METASPACE_SIZE`: The initial metaspace size.
- `GC_MAX_METASPACE_SIZE`: The maximum metaspace size.
- `GC_CONTAINER_OPTIONS`: Specify Java GC to use.
- `HTTPS_PROXY`: The location of the https proxy.
- `HTTP_PROXY`: The location of the http proxy.
- `NO_PROXY`: A comma-separated list of hosts, IP addresses, or domains that can be accessed directly.

## License

This project is licensed under the Apache License 2.0. See the [LICENSE](LICENSE) file for details.