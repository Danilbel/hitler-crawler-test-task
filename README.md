# Hitler Crawler

This is a realization of the solution to the [task Hitler Crawler](TASK.md).

> The solution uses multithreading to find the necessary page.

## How to build and run

The first step is to clone the repository.
```bash
git clone https://github.com/Danilbel/hitler-crawler-test-task.git
```

Then go to the project folder.
```bash
cd hitler-crawler-test-task
```

### Build
To build the project you need to execute the command.
```bash
./gradlew clean jar
```

### Run
To run the project you need to execute the command.
```bash
java -jar build/libs/hitler-crawler-1.0.jar <URL>
```
where `<URL>` is the URL of the Wikipedia page from which the search will start.

## Example result
Example of running the program with the URL `https://en.wikipedia.org/wiki/GitHub`.
```bash
java -jar build/libs/hitler-crawler-1.0.jar https://en.wikipedia.org/wiki/GitHub
```
**Output:**
```
Searching ...
Hitler found: 
https://en.wikipedia.org/wiki/GitHub
 -> https://en.wikipedia.org/wiki/Main_Page
 -> https://en.wikipedia.org/wiki/Wikimedia_Foundation
 -> https://en.wikipedia.org/wiki/List_of_wikis
 -> https://en.wikipedia.org/wiki/Wikiracing
 -> https://en.wikipedia.org/wiki/History_of_Wikipedia
 -> https://en.wikipedia.org/wiki/Adolf_Hitler
```