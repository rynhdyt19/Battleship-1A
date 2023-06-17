package artofwar;

import java.util.Random;
import java.util.Scanner;

public class BattleShipGame {

//    menggunakan 7 final variable untuk switch case
    static final char PLAYER_SHIP = '1'; //output = '@'
    static final char PLAYER_MISSED = '-';
    static final char PLAYER_SUNKEN = 'X';
    static final char COMPUTER_SUNKEN = '!';
    static final char EMPTY = '\000'; // output =  ' '
    static final char COMPUTER_SHIP = '2'; // output ' '
    static final char COMPUTER_MISSED = '3'; // output ' '

    static int grid = 10;
    static char[][] oceanMap = new char[grid][grid];
    static Scanner input = new Scanner(System.in);
    static int playerDeployed;
    static int computerDeployed;

    public static void main(String[] args) {

        printOceanMap();
        deployPlayerShips();
        deployComputerShips();
        printOceanMap();

        while (playerDeployed != 0 && computerDeployed != 0) {
            playerAttack();
            printOceanMap();
            if (computerDeployed == 0) break;
            computerAttack();
            printOceanMap();
        }

        System.out.println("* * * * * GAME OVER * * * * *");
        if (playerDeployed == 0) {
            System.out.println("*  Kamu Kalah, Komputer Menang.  *");
        } else {
            System.out.println("* Selamat, Kamu Menang! *");
        }
    }

    static void printOceanMap() {
        printHeader();
        for (int i = 0; i < oceanMap.length; i++) {
            System.out.print(" " + i + " | ");
            for (char location : oceanMap[i]) {
                switch (location) {
                    case PLAYER_SHIP -> System.out.print('@' + " ");
                    case EMPTY, COMPUTER_MISSED -> System.out.print(' ' + " "); // add COMPUTER_SHIP after testing
                    default -> System.out.print(location + " "); // PLAYER_MISSED, PLAYER_SUNKEN, COMPUTER_SUNKEN
                }
            }
            System.out.print("| " + i + " ");
            System.out.println();
        }
        printFooter();
    }

    static void printHeader() {
        System.out.println();
        System.out.println("** BATTLESHIP BATTLE ARENA **");
        if (playerDeployed == 0 && computerDeployed == 0) {
            System.out.println(" Kondisi Sekarang, Arena Sedang Kosong!");
        }
        System.out.println("---- 0 1 2 3 4 5 6 7 8 9 ----");
    }

    static void printFooter() {
        System.out.println("---- 0 1 2 3 4 5 6 7 8 9 ----");
        System.out.println("Kapal Kamu : " + playerDeployed + " | Kapal CPU : " + computerDeployed);
        System.out.println();
    }

    static int assign(char xY) {
        int i = -1;
        while (i == -1) {
            System.out.println("Enter " + xY + " coordinate for your " + (playerDeployed + 1) + " ship:");
            if (!input.hasNextInt()) {
                input.nextLine();
            } else {
                i = input.nextInt();
                if (i < 0 || 9 < i) {
                    i = -1;
                    System.out.println("Please key in any number 0 to 9");
                }
            }
        }
        return i;
    }

    static void deployPlayerShips() {
        while (playerDeployed < 5) {
            int x = assign('X');
            int y = assign('Y');
            if (oceanMap[x][y] != PLAYER_SHIP) {
                oceanMap[x][y] = PLAYER_SHIP;
                playerDeployed++;
            }
        }
        System.out.println("Player deployed " + playerDeployed + " ships");
    }

    static void deployComputerShips() {
        System.out.println("Computer is deploying ships");
        while (computerDeployed < 5) {
            Random random = new Random();
            int i = random.nextInt(0, 10);
            int j = random.nextInt(0, 10);
            if (oceanMap[i][j] != COMPUTER_SHIP && oceanMap[i][j] != PLAYER_SHIP) {
                oceanMap[i][j] = COMPUTER_SHIP;
                computerDeployed++;
                System.out.println(computerDeployed + ". ship DEPLOYED");
            }
        }
        System.out.println("Computer deployed " + computerDeployed + " ships");
    }

    static int attack(char xY) {
        int i = -1;
        while (i == -1) {
            System.out.println("Enter " + xY + " coordinate: ");
            if (!input.hasNextInt()) {
                input.nextLine();
            } else {
                i = input.nextInt();
                if (i < 0 || 9 < i) {
                    i = -1;
                    System.out.println("Please key in any number 0 to 9");
                }
            }
        }
        return i;
    }

    static void playerAttack() {
        int x = attack('X');
        int y = attack('Y');
        switch (oceanMap[x][y]) {
            case PLAYER_SHIP -> {
                oceanMap[x][y] = PLAYER_SUNKEN;
                playerDeployed--;
                System.out.println("Oh no, you sunk your own ship :(");
            }
            case COMPUTER_SHIP -> {
                oceanMap[x][y] = COMPUTER_SUNKEN;
                computerDeployed--;
                System.out.println("Boom! You sunk the ship!");
            }
            case EMPTY -> {
                oceanMap[x][y] = PLAYER_MISSED;
                System.out.println("Sorry, you missed");
            }
            default -> System.out.println("Sorry, you missed"); // PLAYER_SUNKEN, COMPUTER_SUNKEN, PLAYER_MISSED
        }
    }

    static void computerAttack() {
        Random random = new Random();
        int x = random.nextInt(0, 10);
        int y = random.nextInt(0, 10);

        switch (oceanMap[x][y]) {
            case PLAYER_SHIP -> {
                System.out.println("COMPUTER'S TURN");
                oceanMap[x][y] = PLAYER_SUNKEN;
                playerDeployed--;
                System.out.println("The Computer sunk one of your ships!");
            }
            case COMPUTER_SHIP -> {
                System.out.println("COMPUTER'S TURN");
                oceanMap[x][y] = COMPUTER_SUNKEN;
                computerDeployed--;
                System.out.println("The Computer sunk one of its own ships.");
            }
            case COMPUTER_MISSED -> computerAttack();
            case EMPTY -> {
                System.out.println("COMPUTER'S TURN");
                oceanMap[x][y] = COMPUTER_MISSED;
                System.out.println("Computer missed");
            }
            default -> { // PLAYER_SUNKEN, COMPUTER_SUNKEN
                System.out.println("COMPUTER'S TURN");
                System.out.println("Computer missed");
            }
        }
    }
}