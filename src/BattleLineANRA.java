import java.util.Random;
import java.util.Scanner;

public class BattleLineANRA {
    int playerPosition = 10;
    int computerPosition = -10;
    int playerSoldiers = 25;
    int computerSoldiers = 25;
    int playerFirePower = 2500;
    int computerFirePower = 2500;
    boolean gameOn = true;
    boolean playerBombPlaced, computerBombPlaced;
    Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        new BattleLineANRA().runProgram();
    }

    public void runProgram() {
        startGame();
        while (gameOn) {
            scout();
            displayPlayerStats();
            playerDecision();
            detonateBomb();
            checkIfGameOver();
            computerDecision();
            detonateBomb();
            checkIfGameOver();
        }
        gameEnd();
    }

    



    public int rollDice() {
        Random rand = new Random();
        return rand.nextInt(6) + 1;
    }

    public int getDistance() {
        int max = Math.max(playerPosition, computerPosition);
        int min = Math.min(playerPosition, computerPosition);
        return max - min;
    }

    public void printEmptyLine() {
        System.out.println(" ");
    }

    public void startGame() {
        playerPosition -= rollDice();
        computerPosition += rollDice();
    }

    public void displayPlayerStats() {
        System.out.printf("Your positions is %d, your firepower is %d and you have %d men left \n"
                , playerPosition, playerFirePower, playerSoldiers);
        printEmptyLine();
    }

    public void playerMoveForward() {
        int dice = rollDice();
        if (dice < 4) {
            playerPosition = playerPosition - 1;
        } else {
            playerPosition = playerPosition - 2;
        }
        if (playerPosition < -10) {
            playerPosition = -10;
        }
        if (playerPosition == -10) {
            printEmptyLine();
            playerBombPlaced = true;
            System.out.println("You placed a bomb in the enemy's base! \n " +
                    "get back on your own side to detonate it");
        }
        printEmptyLine();
    }

    public void computerMoveForward() {
        int dice = rollDice();
        if (dice < 4) {
            computerPosition = computerPosition + 2;
        } else {
            computerPosition = computerPosition + 3;
        }
        if (computerPosition > 10) {
            computerPosition = 10;
        }
        if (computerPosition == 10) {
            computerBombPlaced = true;
            printEmptyLine();
            System.out.println("The enemy placed a bomb in your base! \n " +
                    "eliminate enemy before he gets back on his own side. " +
                    "Otherwise the bomb will explode");
        }
    }

    public void playerMoveBackward() {
        int dice = rollDice();
        switch (dice) {
            case 1, 2 -> playerPosition += 1;
            case 3, 4 -> playerPosition += 2;
            case 5, 6 -> playerPosition += 3;
        }
        if (playerPosition > 10) {
            playerPosition = 10;
        }
        playerFirePower += 250;
        printEmptyLine();
    }

    public void computerMoveBackward() {
        int dice = rollDice();
        switch (dice) {
            case 1, 2 -> computerPosition -= 2;
            case 3, 4 -> computerPosition -= 3;
            case 5, 6 -> computerPosition -= 4;
        }
        if (computerPosition < -10) {
            computerPosition = -10;
        }
        computerFirePower += 250;
    }

    public void playerAttack() {
        printEmptyLine();
        int fire = rollDice() * 100;
        if (fire < playerFirePower) {
            switch (getDistance()) {
                case 0 -> {
                    computerSoldiers -= 6;
                    System.out.println("ATTACK! you killed 6 of computers soldiers");
                }
                case 1 -> {
                    computerSoldiers -= 5;
                    System.out.println("ATTACK! you killed 5 of computers soldiers");
                }
                case 2 -> {
                    computerSoldiers -= 4;
                    System.out.println("ATTACK! you killed 4 of computers soldiers");
                }
                case 3 -> {
                    computerSoldiers -= 3;
                    System.out.println("ATTACK! you killed 3 of computers soldiers");
                }
                case 4 -> {
                    computerSoldiers -= 2;
                    System.out.println("ATTACK! you killed 2 of computers soldiers");
                }
                case 5 -> {
                    computerSoldiers -= 1;
                    System.out.println("ATTACK! you killed 1 of computers soldiers");
                }
                default -> System.out.println("Computer is too far away");
            }
            playerFirePower -= fire;
        } else {
            System.out.println("You don't have enough firepower!");
        }
        printEmptyLine();
    }

    public void computerAttack() {
        int fire = rollDice() * 100;
        if (fire < computerFirePower) {

            switch (getDistance()) {
                case 0 -> playerSoldiers -= 6;
                case 1 -> playerSoldiers -= 5;
                case 2 -> playerSoldiers -= 4;
                case 3 -> playerSoldiers -= 3;
                case 4 -> playerSoldiers -= 2;
                case 5 -> playerSoldiers -= 1;
            }
            computerFirePower -= fire;
            if (getDistance() <= 5) {
                System.out.println("You are under attack!");
            } else {
                System.out.println("Computer attacks but is too far away to hit");
            }
        } else {
            System.out.println("Computer attacks, but doesn't have enough firepower");
        }
        printEmptyLine();
    }

    public void scout() {
        if (getDistance() < 1) {
            System.out.println("Scout: you are at the same position!");
            printEmptyLine();
        } else if (getDistance() < 2) {
            System.out.println("Scout: enemy is one position away!");
            printEmptyLine();
        } else if (getDistance() < 3) {
            System.out.println("Scout: enemy is two positions away");
            printEmptyLine();
        } else if (computerPosition - playerPosition == 3) {
            System.out.println("Scout: enemy is three positions behind you!");
            printEmptyLine();
        }
    }

    public void computerDecision() {

        if (computerBombPlaced) {
            computerMoveBackward();
        } else if (getDistance() < 3) {
            computerAttack();
        } else {
            computerMoveForward();
        }
        int dice = rollDice();
        switch (dice) {
            case 1, 2 -> computerMoveForward();
            case 3, 4 -> computerMoveBackward();
            case 5, 6 -> computerAttack();
        }


    }

    public void checkIfGameOver() {
        if ((playerSoldiers <= 0) || (computerSoldiers <= 0)) {
            gameOn = false;
        }
    }

    public void gameEnd() {
        if (computerSoldiers <= 0) {
            System.out.println("Enemy is defeated! You won the game!");
        }
        if (playerSoldiers <= 0) {
            System.out.println("You lost the game!");
        }
    }

    public void playerDecision() {
        System.out.print("Do you want to move forward, move backwards or attack? \n " +
                "type \"f\" for forward, \"b\" for backwards and \"a\" for attack: ");
        String choice = in.next().toLowerCase();
        switch (choice) {
            case "f" -> playerMoveForward();
            case "b" -> playerMoveBackward();
            case "a" -> playerAttack();
            default -> {
                System.out.println("You misspelled! You retreat");
                playerMoveBackward();
            }
        }
    }

    public void detonateBomb() {
        if (playerBombPlaced && (playerPosition > 0)) {
            computerSoldiers = 0;
            System.out.println("BANG! you detonated the bomb");
        }
        if (computerBombPlaced && (computerPosition < 0)) {
            playerSoldiers = 0;
            System.out.println("BANG! a bomb detonated at your base!");
        }
    }


}