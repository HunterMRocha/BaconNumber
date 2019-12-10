 # Runtime Analysis

Runtime of Breadth-First-Search is Θ(|V| + |E|).

Since we do *breadth first* instead of *depth first*, and return the first connection found, we know that we are finding a path with the *smallest amount of connections.* 

# Compiling and Running

This project must be compiled with `json-simple`

Here's a way to compile and run the project on a Mac

```bash
javac -cp json-simple-1.1.1.jar*.java
java -cp .:json-simple-1.1.1.jar Driver tmdb_5000_credits.csv
```

Output from test run:
```bash
Actor 1 name: Morgan Freeman
Actor 2 name: Keke Palmer
Path Between Morgan Freeman and Keke Palmer: 
Morgan Freeman --> Robert Wisdom --> Keke Palmer
```"#Six-Degrees-of-Kevin-Bacon" 
"# BaconNumber" 
