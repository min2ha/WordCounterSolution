# Parallel Word Counter

> This is my simplified demo project of ***parralel file reading and processing***.

> Objective is to ***select Top 20 most frequent words*** within all files ASAP.

> Idea:  ***1 text file per 1 Thread***

All ready for processing text files are stored at the same Map:
```
ConcurrentHashMap<String, LongAdder>
```

Processed files moved to separate folder.


This ***multi-module project consists of 3 separate parts***:
> * File Upload Service (REST WS (Back-End))
> * Word Counter Service (REST WS (Back-End))
> * Spring Boot Web App + Boostrap (Front-End)


1. Upload text files (multi-file upload)
2. List of files could be seen if pressed get list and go to the bottom (on the right side demo boxes for more attractive view) 

It’s purpose is to demonstrate my knowledge of technologies listed below:

* Git multi-module project
* Spring Boot, Bootstrap, JQuery (Front End)
* 2 REST services
*	Java 8 Lambda
*	Dockerfile file (to run on Docker)
*	Docker Composer file (to Docker Composer)


## Getting Started

These instructions will get you a copy of the project up and running on your local machine (or VM) for development and testing purposes. See deployment for notes on how to deploy the project on a live system.


### Prerequisites

To test the project, the sw listed bellow need to be installed first:

 1. Java JDK 8 (or higher)
 2. git client
 3. maven build tool
 4. docker
 5. docker-composer

```
Give examples
```

### Installing

A step by step series of examples that tell you how to get a development env running



Clone project repo on test VM (or anywhere):

```
git clone https://github.com/min2ha/WordCounterSolution.git
```

Build project:

```
mvn clean package
```

End with an example of getting some data out of the system or using it for a little demo

## Running the tests

Explain how to run the automated tests for this system

### Break down into end to end tests

Explain what these tests test and why

```
Give an example
```

### And coding style tests

Explain what these tests test and why

```
Give an example
```

## Deployment

Add additional notes about how to deploy this on a live system

```
docker-compose up
```

## Running

After docker-compose was executed sucessfully, you should be able to test:

http://localhost:8833/fileuploadservice/api

http://localhost:8855/wordcounterservice/api

Management should be done over Front End Web App:
http://localhost:8877/wordcounterwebapp


## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

## Contributing

Please read [CONTRIBUTING.md](https://gist.github.com/PurpleBooth/b24679402957c63ec426) for details on our code of conduct, and the process for submitting pull requests to us.

Some basic Git commands are:
```
git status
git add
git commit
```


## Versioning

We use [github](https://github.com) for versioning. For the versions available, see the [tags on this repository](https://github.com/your/project/tags). 

## Authors

* **Mindaugas Vidmantas** - *Initial work* - [min2ha](https://github.com/min2ha)

@min2ha :+1: It's ready to merge! :shipit:

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

* Hat tip to anyone whose code was used
* Inspiration
* etc
