package ml.kanfa.model;

/**
 * @author Kanfa.
 */
public final class Text {

    public static String[] getGameOverText(){
        String[] strings = "Game Over !@Appuyer sur la touche ESPACE pour réjouer".split("@");
        return strings;
    }

    public static String[] getWinText(){
        String[] strings = "Felicitaion ! vous avez gagné@Appuyer sur la touche ENTREE pour continuer".split("@");
        return strings;
    }
}
