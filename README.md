# PieDrive
PieDrive aims to provide an integration platform where you can address in an unified manner all your cloud storages. This allwos for easier mangement of data concerns in a central place. This prototype consists of four main parts:
* Authentication Server: Cloudfoundry UAA server provides authentication for the user and issues JWT tokens.
* DevServer: Delivers the front end application.
* Social Provider Service: Abstracts the cloud storages with help of Spring Social.
* Gateway: Provides reverse proxy functionality for all services.

This is an early prototype of PieDrive. The future development will, however, proceed on the original fork.

## How to start
For testing the prototype a series of steps has to be performed:
1. navigate in your shell to `[..]/PieDrive/webclient/piedrive`
3. execute `npm install` to install all the frontend dependencies (npm has to be available on your computer)
2. execute `npm start` to start the development server for the frontend
3. the browser will automatically open, close this window again
4. navigate to `[...]/PieDrive/UUA` and execute `mvn clean package`
5. then execute `java -jar target/UUA-0.0.1-SNAPSHOT.jar` to start the authentication server
6. go to your Dropbox and your GoogleDrive accounts and create Apps for the APIs set the callback URLs to `http://localhost:8080/api/connect/2/dropbox` and `http://localhost:8080/api/connect/2/google`
7. open `[..]/PieDrive/SocialProvider/src/main/resources/application.yml` add your Google and Dropbox appId and addSecret
8. navigate to `[..]/PieDrive/SocialProvider and execute` and execute `mvn clean package`
9. then execute `java -jar target/SocialProvider-0.0.1-SNAPSHOT.jar` to start the provider service
10. finally go to `[..]/PieDrive/PiePlate` and execute `mvn clean package`
11. then execute `java -jar target/PiePlate-0.0.1-SNAPSHOT.jar`  to start the API gateway
12. once all is running you can access the application in your web browser under `http://localhost:8080`
13. sign in with `user/password`
14. navigate to the provider page
15. add Dropbox and GoogleDrive (your UI will not give you any response for the providers)
16. navigate back to files page to see all files from your dropbox and google drive
