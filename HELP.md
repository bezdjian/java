# Test Containers with Spring boot.
## This branch works with the branch testcontainer, as an additional service to run it with GenericContainer.

### Build and run
```
docker build -t projects .
```

```
docker run -p 8081:8080 projects
```

Browse: http://localhost:8081/api/projects/technology/Java