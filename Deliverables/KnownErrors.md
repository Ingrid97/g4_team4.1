##Known Errors

- Robots can exit the map boundary on right side of the board without dying and walk back onto the board.

- If a robot stands on a conveyor belt, it can push the robot so it's on the same tile as another robot.

- If some robots choose powerdown while others doesn't you get a IndexOutOfBoundsException when the second robot makes a move.

- When you win the game it exits with a error.