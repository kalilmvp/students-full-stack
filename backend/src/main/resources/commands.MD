#### mock data generator

[Mockaroo](https://www.mockaroo.com/)

<br/>

#### to run the command without profile

```
./mvnw compile jib:dockerBuild -Djib.to.image=fullstack:v1 or ./mvnw compile jib:dockerBuild -Dimage=fullstack:v1
./mvnw clean install jib:dockerBuild -Djib.to.image=fullstack:v1 or ./mvnw clean install jib:dockerBuild -Dimage=fullstack:v1
```

<br/>

#### with profiles configured
```
./mvnw clean install -P build-frontend -P jib-push-to-dockerhub -Dapp.image.tag=2
./mvnw clean install -P build-frontend -P jib-push-to-local -Dapp.image.tag=latest
```

<br/>

#### postgres container
```docker
docker network create db
docker network rm db
docker run --name db -p 5432:5432 --network=db -v "$PWD:/var/lib/postgresql/data" -e POSTGRES_PASSWORD=password -d postgres:alpine
```


<br/>


#### psql
```docker
docker run -it --rm --network=db postgres:alpine psql -h db -U postgres
```
