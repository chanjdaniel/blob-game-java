# r9r2b - Personal Project

## A blob eat blob world


- **What will the application do?**

*The application will be a game. In this game, the user will play as a blob. 
The goal of the game will be to grow as big as possible while avoiding death, which can occur by 
being hit by traps or eaten by other, larger blobs.
The user will be able to move the blob in cardinal directions around the play area. 
There will be other entities in the play area such as food, power-ups, and other blobs. 
The user will interact with these entities by moving their blob into them.
Moving into different entities will either help the user's blob grow, or make the user's blob shrink.*


- **Who will use it?**

*Myself, and others in the class who might be interested in playing the game. Potentially, anonymous users
online.*

- **Why is this project of interest to you?**

*I have always been interested in trying to code a game and tackling the challenge of coding something
that runs well and is fun to play. This project seems simple to start, but also has a lot of room to add complexity in
the future. As I learn more in the course, I plan to optimize what is already there and
add additional functionality.*

*Some ideas I have now are:*

- *adding additional enemy blob behaviour, like hunting down the player and other blobs*
- *adding different levels*
- *adding different power-ups*
- *adding a way to save progress and game state using a string*

## User Stories
- As a user, I want to be able to add an ability to a list of abilities
- As a user, I want to be able to be eaten by other blobs if they are bigger than me
- As a user, I want to be able to add names of blobs I have eaten to a list of names of blobs eaten
- As a user, I want to be able to eat blobs smaller than me
- As a user, I want to be able to remove unwanted abilities
- As a user, I want to be able to save the state of my blob and the state of the game to file;
 I want to be able to make multiple save files and name them
- As a user, I want to be able to load the state of my blob and the state of the game from file;
 I want to be able to choose to start a new game or choose a file to load from when starting the game
- As a user, I want to be able to name save files and load save files using their names

## Phase 4: Task 2
**New Game**
- Mon Nov 22 12:41:55 PST 2021  
Game Started
- Mon Nov 22 12:42:05 PST 2021  
Started New Game
- Mon Nov 22 12:42:21 PST 2021  
bob the blob gained Consume protein
- Mon Nov 22 12:42:23 PST 2021  
bob the blob used Consume protein
- Mon Nov 22 12:42:25 PST 2021  
bob the blob ate Myrle
- Mon Nov 22 12:42:37 PST 2021  
Saved game at ./data/saves/bob.json
- Mon Nov 22 12:42:44 PST 2021  
Quit Game

**Load Game**
- Mon Nov 22 12:48:07 PST 2021  
Game Started
- Mon Nov 22 12:48:12 PST 2021  
Loaded game from ./data/saves/bob.json
- Mon Nov 22 12:48:16 PST 2021  
bob the blob ate Cherie
- Mon Nov 22 12:48:29 PST 2021  
bob the blob gained Consume stimulants
- Mon Nov 22 12:48:30 PST 2021  
bob the blob used Consume stimulants
- Mon Nov 22 12:48:39 PST 2021  
Quit Game