# Code Sherpas Beer Tap Dispenser

### Notes

- I have modified the API that creates a dispenser to receive an additional parameter pricePerLiter since it was not
  specified in the code challenge, so I made this assumption

### Requirements

- Docker (Docker Compose v3.8)
- Ensure the Database Mongodb port **27017** is free
- Ensure the API port **8080** is free
- JDK 17

### Set-up

- Create MongoDB container: ```docker compose up -d```
- Run project with Spring local profile: ```./gradlew bootRun --args='--spring.profiles.active=local'```
- **Optional** If you would like to use a MongoDB GUI, this is the connection
  string: ```mongodb://root:password@localhost:27017```

### APIS

Since there aren't API definitions in the challenge description as per 02 August 2023, I created the followings APIs:

Please also check the Swagger Docs: http://localhost:8080/swagger-ui/index.html

- Create Dispenser

```
curl --request POST \
  --url http://localhost:8080/api/dispenser \
  --header 'Content-Type: application/json' \
  --data '{
	"flowVolume": "1",
	"pricePerLiter": "0.55"
}'
```

- Get Dispenser Information

```
curl --request GET \
--url http://localhost:8080/api/dispenser/64ca8235d2ad5f1d23166ec2
```

- Get Reports

```
curl --request GET \
  --url http://localhost:8080/api/dispenser/report
```

- Get Report for a single dispenser

```
  curl --request GET \
  --url http://localhost:8080/api/dispenser/report/64cada2639d7750f97fc8798
  ```

- Switch ON/OFF the dispenser tap

```
curl --request PATCH \
  --url http://localhost:8080/api/dispenser/switch \
  --header 'Content-Type: application/json' \
  --data '{
	"dispenserId": "64cada2639d7750f97fc8798"
}'
```