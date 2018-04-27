# game

A simple command line tool for playing the game


## How to use it
```
Game game = new Game();
```
Then start the game when ready:
```
game.start();
```
At every step the active player must decide the index of one of the 6 available pits to use (0-5). The pit must contain stones to be used.:
```
game.move(selectedIndex);
```
You can get the current representation of each side of the board using the players' objects:
```
Player player1 = game.getPlayer1();
Player player2 = game.getPlayer2();
```
Check the game status after every move, they can be:
* INITIATED: instantiated but not started yet
* PLAYER_1: player 1 turn
* PLAYER_2: player 2 turn
* FINISHED: game was finished

When finished you can retrieve the winner of the game:
```
Player winner = game.getWinner();
```
## Improvements
* Current implementation is very procedural with a lot of moving parts and a lot of mutation is taking place. A better way would be more functional and with minimum mutation.
* More tests coverage
