# COMP2911

Instructions on how to use the separated LevelMap class.
A lot of these steps are kludgey and temporary, until we decide to fully move to the separated class.


1. Add "extends Observable" to the Game class
2. Remove arguments from Game constructor
3. Comment out from Game constructor line "this.display = new Display(title,width,height, this.listener);"
4. Comment out from Game constructor line "render();"
5. Make update() function in Game.java public
6. Comment out from update() function line "render();"
7. Add setChanged(); after the render line commented out in step 6
8. Add notifyObservers(); after the line added in step 7
9. Add a getter for Matric in Game.java
10. Compile from WarehouseBoss.java instead of GameLauncher.java
