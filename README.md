# Simple pasteBin

Simple PasteBin is a project dedicated to storing nonsensitive information on the server-side for clients, providing them with an easy way to share it. Each paste (similar to a text file) comes with an expiration date and metadata, primarily the owner’s OpenID.

We have implemented basic Google OAuth 2.0 for user authentication, allowing users to make changes to their pastes. The data is stored in a PostgreSQL database and Azure Blob Storage. This setup ensures a secure and efficient data management system for our users. Please note that this service should not be used for storing sensitive information. Enjoy sharing!

## Getting Started

We assume that you are familiar with Google Cloud Platform and Azure Blob Storage, so you can generate the required data by yourself.

## Prerequisites

Before you begin, ensure you have the following installed on your local machine:

- **Azure Blob Storage**: This is used for storing data in the cloud.
- **Google Oauth 2.0**: This is used for authorization.
- **PostgreSQL**: This is a powerful, open-source object-relational database system. (You may change it later)
- **Java (version 17 or later)**: This is used for backend.
- **Node.js**: This is used for React.js.
- **React.js**: This is a JavaScript library for building user interfaces.

Please follow the official documentation for installation instructions for each prerequisite.

## Getting Started with the Project
This section provides a series of step-by-step instructions on how to get started with the project.

###Cloning the Repository
First, clone the repository or download it using the following command:
```
git clone https://github.com/TheGreyCore/pasteBin/
```
### Setting Up and Running the Backend
Navigate to the backend directory:
```
cd backend
```
Before running the backend, ensure that you have set the following environment variables:
```
azureConnectionString: Your Azure connection string.
googleOauth2ClientID: Your Google OAuth 2.0 client ID.
googleOauth2ClientSecret: Your Google OAuth 2.0 client secret.
```
You can set these environment variables in your operating system, or you can set them just for the application by adding them to the bootRun task in your build.gradle file:
```
bootRun {
    environment 'azureConnectionString', 'your-azure-connection-string'
    environment 'googleOauth2ClientID', 'your-google-oauth2-client-id'
    environment 'googleOauth2ClientSecret', 'your-google-oauth2-client-secret'
}
```

Then, you can run your Spring Boot application with the Gradle bootRun task:
```
./gradlew bootRun
```
### We hope no errors occur!

### Setting Up and Running the Frontend
Now, let’s run the frontend:

Navigate to the frontend folder:
```
cd ../frontend
```
And start the frontend:
```
npm start
```
### You should now be able to access the page (default: http://localhost:3000/).

Please note that the images below may not refer to the actual project:
![1](https://github.com/TheGreyCore/pasteBin/assets/104592742/cbd19a7d-aae9-423d-b4f3-4144cf57882b)
![3](https://github.com/TheGreyCore/pasteBin/assets/104592742/2c69d13a-6746-4a42-a81c-452e9c878e34)
![2](https://github.com/TheGreyCore/pasteBin/assets/104592742/b7539863-5237-44c0-9033-51641084c13f)


## Deployment

At the moment we test how to deploy the application, but anyway we provide a reminder of what you need to do if you want to deploy by yourself:

- *Update Azure Blob Storage Connection Way:* Make sure you use reccomended connection way on deploying
- *Update Frontend and Backend URLs:* You’ll need to update the URLs for your frontend and backend in your code. These URLs are often found directly in  code. Make sure these point to the correct deployed addresses of your frontend and backend.

## Built With

This project was developed using the following technologies:

- React.js: A JavaScript library for building user interfaces.
- Bootstrap: A free and open-source CSS framework directed at responsive, mobile-first front-end web development.
- Azure Blob Storage: Microsoft’s object storage solution for the cloud, used for storing large amounts of unstructured data.
- PostgreSQL (Postgre): A powerful, open-source object-relational database system.
- Spring Boot: An open-source Java-based framework used to create stand-alone, production-grade Spring-based Applications.

  
## Authors

* **Dmitri Matetski** - *Initial work* - [TheGreyCore](https://github.com/TheGreyCore)


## License

This project is licensed under the GPL 3.0 License - see the [LICENSE.md](LICENSE.md) file for details

