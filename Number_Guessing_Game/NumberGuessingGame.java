import java.util.Random;
import javax.swing.JOptionPane;

public class NumberGuessingGame {
    public static void main(String[] args) {
        int range = 100;
        int attempts = 10;
        int score = 0;
        int round = 1;
        Random rand = new Random();
        int numberToGuess = rand.nextInt(range) + 1;
        while (true) {
            String input = JOptionPane.showInputDialog(null,
                    "Round " + round + " - Guess the number between 1 and " + range + "\nAttempts left: " + attempts);
            if (input == null) {
                // User clicked cancel or closed the dialog
                break;
            }
            int guess = Integer.parseInt(input);
            if (guess == numberToGuess) {
                JOptionPane.showMessageDialog(null, "Congratulations! You guessed the number in " + (10 - attempts + 1) + " attempts.");
                score += attempts;
                round++;
                attempts = 10;
                numberToGuess = rand.nextInt(range) + 1;
            } else if (guess < numberToGuess) {
                JOptionPane.showMessageDialog(null, "Too low. Try again.");
                attempts--;
            } else {
                JOptionPane.showMessageDialog(null, "Too high. Try again.");
                attempts--;
            }
            if (attempts == 0) {
                JOptionPane.showMessageDialog(null, "Game over. The number was " + numberToGuess);
                round++;
                attempts = 10;
                numberToGuess = rand.nextInt(range) + 1;
            }
        }
        JOptionPane.showMessageDialog(null, "Game over. Your final score is " + score);
    }
}
