# ZTV

Using this project you can expose m3u files as streams so that they can be used by Plex as Live TV

It can parse m3u files from the internet or you can upload it as a file as well.

Required Java: 11

Once the project is running, it's available on `localhost:8270`. For Plex provide this url: `localhost:8270/api`

### To compile the project
```
./gradlew build
```
### Generated file under
```
ztv-backend/build/libs/
```

### How to run it
```
java -jar ztv-backend-1.0-SNAPSHOT.jar
```

