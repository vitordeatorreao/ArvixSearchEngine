# ArvixSearchEngine
This is the source code repository for the first task in the Intelligent
Information Retrieval course for the MSc Computer Science at Federal University
of Pernambuco.

This project is written entirely in Java and uses
[Maven](https://maven.apache.org/) for managing dependencies.

## Building the project on Eclipse

If you are not using a very old version, Eclipse should support Maven projects
out-of-the-box.

1. Clone this repository to your Eclipse workspace;
2. You can import the project by going to `File -> Import`. A window will pop-up,
and you can scroll down to the Maven folder. Inside it, you can find a
"Existing Maven Projects" option. Select it and click next;
3. On the new screen, press the top "Browse..." button and select the folder
where you cloned this repository;
4. Click finish. You should be good to go. Eclipse will download the dependencies
and build the project.

## Dependencies

This project depends on the following packages:

* `junit`: this project's unit tests were written in JUnit 4;
* `lucene-core`: the search engine is built around the
[Apache Lucene](https://lucene.apache.org/core/) library;
* `lucene-queryparser`: this Lucene module contains the `QueryParser` class,
which was used in this project to remove stop words and apply stemming to the
queries;
* `lucene-analyzers-common`: this Lucene module contains the `EnglishAnalyzer`
class which includes a Stemmer for the english language;
* `json-simple`: this is library used to parse the JSON files where the
documents were stored.

## Contributions

Because this is a school project, contributions are not allowed at this point.
Any pull requests will be rejected.
