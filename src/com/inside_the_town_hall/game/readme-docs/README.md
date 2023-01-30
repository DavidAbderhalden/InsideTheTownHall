# InsideTheTownHall

---

Inside The Town Hall is a game with inspiration based on the popular mobile game "clash of clans".

The main game is played inside the town hall, providing a level system, mining and attacks.

## Features of Version 0.1
A basic pixel graphic game board is created with hallway and rooms. These rooms are connected by doors
a movable entity can walk through.

There are only movable wizard entities which can be manually moved (default wasd) or path find by left-click
followed by right-click on the path.

Textures are not yet provided.

## Installation

Use following [maven](https://maven.apache.org/) commands to install all the dependencies:
```bash
mvn clean install
```
Use following [maven](https://maven.apache.org/) commands to build a binary file:
```bash
mvn clean compile assembly:single
```
## Usage

Use following command to run in development mode:
```bash
java InsideTheTownHall -m dev
```
Use following command to run in production mode:
```bash
java InsideTheTownHall -m prod
```

## Testing

Use following command to run all the Unit tests:
```bash
java TestRunner
```

## Project Structure

A conceptual as well as a designer UML class diagram are provided as PNGs.

JavaDocs can be build with following command:
```bash
mvn javadoc:javadoc
```

## Updates & Development

It is currently unclear if the development will be continued.

That is because my school is giving me so much work I will probably die of a heart attack.

## License

[Apache](../../../../../LICENSE)