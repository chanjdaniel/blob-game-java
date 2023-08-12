***This README was written during development of this game for a second-year computer science course**

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
- Tue Nov 23 19:30:39 PST 2021  
Game Started
- Tue Nov 23 19:30:47 PST 2021  
Started New Game
- Tue Nov 23 19:31:02 PST 2021  
bob the blob gained Consume stimulants
- Tue Nov 23 19:31:03 PST 2021  
bob the blob used Consume stimulants - speed: 3
- Tue Nov 23 19:31:15 PST 2021  
bob the blob gained Consume stimulants
- Tue Nov 23 19:31:16 PST 2021  
bob the blob used Consume stimulants - speed: 4
- Tue Nov 23 19:31:18 PST 2021  
bob the blob ate Jocelin - size: 20
- Tue Nov 23 19:31:24 PST 2021  
Saved game at ./data/saves/bob.json
- Tue Nov 23 19:31:24 PST 2021  
Quit Game

**Load Game**  
- Tue Nov 23 19:32:23 PST 2021  
Game Started
- Tue Nov 23 19:32:28 PST 2021  
Loaded game from ./data/saves/bob.json
- Tue Nov 23 19:32:33 PST 2021  
bob the blob ate Pam - size: 29
- Tue Nov 23 19:32:39 PST 2021  
bob the blob was eaten by Adaline - Game over!
- Tue Nov 23 19:32:42 PST 2021  
Quit Game

## Phase 4: Task 3

If I had more time, I would have liked to find a way to reduce the number of classes
used to represent each "screen" of the game. The UML class diagram is so messy mainly 
because I had decided to use one class for each of the game screens. I made this decision 
because I knew that each screen was going to look very different and have different elements. 
I honestly do not know how I could do this better, as having all the data for all the screens in one
class seems unreasonable. I would have also liked to implement the singleton design pattern for classes 
where there should only be one instance; for example, GameFrame and BlobEatBlob. 

Otherwise, I was happy with the design decisions I made and 
putting everything together was quite smooth.

___

*Example images*

<p>&nbsp;</p>

Character creation

<img width="800" alt="Screenshot 2023-08-12 at 8 53 22 PM" src="https://github.com/chanjdaniel/blob-game-java/assets/97641190/4a3307fc-9f36-41f1-9a16-0d2241f01302">

<p>&nbsp;</p>

Pause menu

<img width="800" alt="Screenshot 2023-08-12 at 8 54 19 PM" src="https://github.com/chanjdaniel/blob-game-java/assets/97641190/26661df9-9beb-4189-969e-c6de140ec5b5">

<p>&nbsp;</p>

Main gameplay

<img width="800" alt="Screenshot 2023-08-12 at 8 57 33 PM" src="https://github.com/chanjdaniel/blob-game-java/assets/97641190/8602d050-cf3a-4f2d-ac81-992202ef73c7">

<p>&nbsp;</p>

History of eaten opponents

<img width="800" alt="Screenshot 2023-08-12 at 8 56 04 PM" src="https://github.com/chanjdaniel/blob-game-java/assets/97641190/0f7c9000-a5d7-4a38-be0d-96f4a4e335a9">

