import java.util.Scanner;
import java.util.ArrayList;
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<String> deck = new ArrayList<String>(); // Deck where the cards are stored
        ArrayList<String> player1Cards = new ArrayList<String>(); // Player 1's Cards
        ArrayList<String> player2Cards = new ArrayList<String>(); // Player 2's Card
        ArrayList<String> drawPile; // Pile for drawing cards during the game
        String startingCard = ""; // The Starting Card
        String currentColour = "";
        String currentNumber = "";
        boolean gameOver = false;
        int currentPlayer = 1;

        //Creating the deck (Lines 12-29)
        String[] colours = {"Red", "Blue", "Green", "Yellow"}; //The 4 colours

        for (String colour : colours) { //adding cards to each colour
            deck.add(colour + " 0"); // adding one zero card
            for (int i = 1; i <= 9; i++) { // adding two of each number 1-9
                deck.add(colour + " " + i);
                deck.add(colour + " " + i);
            }
            deck.add(colour + " Draw Two"); //adding two draw two cards
            deck.add(colour + " Draw Two");
            deck.add(colour + " Skip"); //adding two skip cards
            deck.add(colour + " Skip");
        }

        for (int i = 0; i < 4; i++) { //adding 4 wild cards and 4 wild draw four cards
            deck.add("Wild");
            deck.add("Wild Draw Four");
        }

        //Shuffling the cards (Lines 32-37)
        for (int i = 0; i < deck.size(); i++) {
            int randomIndex = (int) (Math.random() * deck.size()); //Generating a random index
            String temp = deck.get(i);
            deck.set(i, deck.get(randomIndex)); // Swap the cards
            deck.set(randomIndex, temp); //Swap the cards back
        }

        //Deal 7 cards to each player (Lines 40-45)
        for (int i = 0; i < 7; i++) {
            player1Cards.add(deck.get(deck.size() - 1));
            deck.remove(deck.size() - 1);
            player2Cards.add(deck.get(deck.size() - 1));
            deck.remove(deck.size() - 1);
        }

        //The remaining deck becomes the draw pile
        drawPile = deck;

        //Getting a valid starting card
        for (int i = 0; i < drawPile.size(); i++) {
            if (!drawPile.get(i).contains("Wild") && !drawPile.get(i).contains("Draw Two") && !drawPile.get(i).contains("Skip")) {
                startingCard = drawPile.get(i);
                drawPile.remove(i);
                break;
            }
        }

        //Game information
        String gameInfo = "Starting Card: " + startingCard + "\n" + "Player 1's Cards: ";

        //player 1
        for (String card : player1Cards) {
            gameInfo += card + ", ";
        }
        if (gameInfo.length() > 0) {
            gameInfo = gameInfo.substring(0, gameInfo.length() - 2); //Removing the comma
        }

        //player 2
        gameInfo += "\nPlayer 2's cards: ";
        for (String card : player2Cards) {
            gameInfo += card + ", ";
        }
        if (gameInfo.length() > 0) {
            gameInfo = gameInfo.substring(0, gameInfo.length() - 2); //Removing the comma
        }

        System.out.println(gameInfo); //Display the information for players

            //initializing the current colour and current number
            currentColour = startingCard.substring(0, startingCard.indexOf(" "));
            currentNumber = startingCard.substring(startingCard.indexOf(" ") + 1);

        //Gameplay
        while (!gameOver) {
            String currentCard = startingCard;
            if (currentPlayer == 1) {
                System.out.println("\nPlayer 1's turn:");
                System.out.println("Current card: " + currentCard);
                System.out.println("Your cards:");
                for (int i = 0; i < player1Cards.size(); i++) {
                    System.out.println((i + 1) + ". " + player1Cards.get(i));
                }

                //Ask the player to choose a card to play
                String input = "";
                boolean validMove = false;
                while (!validMove) {
                    System.out.print("Select a card to play (enter the number where the card is or type 'draw' to draw a card): ");
                    input = sc.nextLine();
                    if (input.equals("draw")) {
                        String drawnCard = drawPile.get(0);
                        player1Cards.add(drawnCard);
                        drawPile.remove(0);
                        System.out.println("Player 1 draws a card: " + drawnCard);
                        validMove = true;
                    } else {
                        int cardIndex = Integer.parseInt(input) - 1;
                        if (cardIndex >= 0 && cardIndex < player1Cards.size()) {
                            String chosenCard = player1Cards.get(cardIndex);

                            String chosenColour = chosenCard.substring(0, chosenCard.indexOf(" "));
                            String chosenNumber = chosenCard.substring(chosenCard.indexOf(" ") + 1);

                            if (chosenCard.contains(currentColour) || chosenCard.contains(currentNumber) || chosenCard.contains("wild")) {
                                startingCard = chosenCard;
                                player1Cards.remove(cardIndex);
                                System.out.println("Player 1 plays: " + chosenCard);

                                currentColour = chosenColour;
                                currentNumber = chosenNumber;

                                if (chosenCard.contains("Draw Two")) {
                                    System.out.println("Player 2 must draw two cards!");
                                    player2Cards.add(drawPile.get(0));
                                    drawPile.remove(0);
                                    player2Cards.add(drawPile.get(0));
                                    drawPile.remove(0);
                                    currentPlayer = 2;
                                } else if (chosenCard.contains("Skip")) {
                                    System.out.println("Player 2's turn is skipped");
                                    currentPlayer = 2;
                                } else if (chosenCard.equals("Wild")) {
                                    System.out.print("Player 1 chooses the new colour: ");
                                    currentColour = sc.nextLine();
                                    currentNumber = "";
                                } else if (chosenCard.equals("Wild Draw Four")) {
                                    System.out.println("Player 1 chooses the new colour: ");
                                    currentColour = sc.nextLine();
                                    currentNumber = "";
                                    System.out.println("Player 2 must draw four cards!");
                                    player2Cards.add(drawPile.get(0));
                                    drawPile.remove(0);
                                    player2Cards.add(drawPile.get(0));
                                    drawPile.remove(0);
                                    player2Cards.add(drawPile.get(0));
                                    drawPile.remove(0);
                                    player2Cards.add(drawPile.get(0));
                                    drawPile.remove(0);
                                    currentPlayer = 2;
                                }
                                validMove = true;
                                currentCard = startingCard;
                            } else {
                                System.out.println("You can't play this card. It's not a valid move.");
                            }
                        } else {
                            System.out.println("Invalid card selection. Please choose a valid number");
                        }
                    }
                }
            } else {
                System.out.println("\nPlayer 2's turn:");
                System.out.println("Current card: " + currentCard);
                System.out.println("Your cards:");
                for (int i = 0; i < player2Cards.size(); i++) {
                    System.out.println((i + 1) + ". " + player2Cards.get(i));
                }

                //Ask the player to choose a card to play
                String input = "";
                boolean validMove = false;
                while (!validMove) {
                    System.out.print("Select a card to play (enter the number where the card is or type 'draw' to draw a card): ");
                    input = sc.nextLine();
                    if (input.equals("draw")) {
                        String drawnCard = drawPile.get(0);
                        player2Cards.add(drawnCard);
                        drawPile.remove(0);
                        System.out.println("Player 2 draws a card: " + drawnCard);
                        validMove = true;
                    } else {
                        int cardIndex = Integer.parseInt(input) - 1;
                        if (cardIndex >= 0 && cardIndex < player2Cards.size()) {
                            String chosenCard = player2Cards.get(cardIndex);

                            String chosenColour = chosenCard.substring(0, chosenCard.indexOf(" "));
                            String chosenNumber = chosenCard.substring(chosenCard.indexOf(" ") + 1);

                            if (chosenCard.contains(currentColour) || chosenCard.contains(currentNumber) || chosenCard.contains("wild")) {
                                startingCard = chosenCard;
                                player2Cards.remove(cardIndex);
                                System.out.println("Player 2 plays: " + chosenCard);

                                currentColour = chosenColour;
                                currentNumber = chosenNumber;

                                if (chosenCard.contains("Draw Two")) {
                                    System.out.println("Player 1 must draw two cards!");
                                    player1Cards.add(drawPile.get(0));
                                    drawPile.remove(0);
                                    player1Cards.add(drawPile.get(0));
                                    drawPile.remove(0);
                                    currentPlayer = 1;
                                } else if (chosenCard.contains("Skip")) {
                                    System.out.println("Player 1's turn is skipped");
                                    currentPlayer = 1;
                                } else if (chosenCard.equals("Wild")) {
                                    System.out.print("Player 2 chooses the new colour: ");
                                    currentColour = sc.nextLine();
                                    currentNumber = "";
                                } else if (chosenCard.equals("Wild Draw Four")) {
                                    System.out.println("Player 2 chooses the new colour: ");
                                    currentColour = sc.nextLine();
                                    currentNumber = "";
                                    System.out.println("Player 1 must draw four cards!");
                                    player1Cards.add(drawPile.get(0));
                                    drawPile.remove(0);
                                    player1Cards.add(drawPile.get(0));
                                    drawPile.remove(0);
                                    player1Cards.add(drawPile.get(0));
                                    drawPile.remove(0);
                                    player1Cards.add(drawPile.get(0));
                                    drawPile.remove(0);
                                    currentPlayer = 2;
                                }
                                validMove = true;
                                currentCard = startingCard;
                            } else {
                                System.out.println("You can't play this card. It's not a valid move.");
                            }
                        } else {
                            System.out.println("Invalid card selection. Please choose a valid number");
                        }
                    }
                }
            }
        }
    }
}