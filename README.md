# Code Sherpas Beer Tap Dispenser

### Requirements
- Docker (Docker Compose v3.8)
- Ensure the Database Mongodb port **27017** is free
- Ensure the API port **8080** is free
- JDK 17

### Set-up
- Create MongoDB container: ```docker compose up -d```
- Run project with Spring local profile: ```./gradlew bootRun --args='--spring.profiles.active=local'```
- **Optional** If you would like to use a MongoDB GUI, this is the connection string: ```mongodb://root:password@localhost:27017```

### cURL APIs
Since there aren't API definitions in the challenge description as per 02 August 2023, I created the followings APIs:

- Create Dispenser
```
curl --request POST \
  --url http://localhost:8080/api/dispenser \
  --header 'Content-Type: application/json' \
  --data '{
  "flowVolume": "2.0"
  }'
```

- Get Dispenser
```
curl --request GET \
--url http://localhost:8080/api/dispenser/64ca8235d2ad5f1d23166ec2
```

- Switch ON tap
 ```
curl --request PATCH \
  --url http://localhost:8080/api/dispenser/session/on \
  --header 'Content-Type: application/json' \
  --data '{
  "dispenserId": "64caace74499e355a981120c"
  }'
```

- Switch OFF tap
```
curl --request PATCH \
  --url http://localhost:8080/api/dispenser/session/off \
  --header 'Content-Type: application/json' \
  --data '{
	"dispenserId": "64caace74499e355a981120c"
}'
```