package ml.kanfa.engine;

/**
 * @author Ibrahim Maïga.
 */
public final class Text {

    public static String[] getGameOverText(){
        final String[] strings = "Game Over !@Appuyer sur la touche ESPACE pour réjouer".split("@");
        return strings;
    }

    public static String[] getWinText(){
        final String[] strings = "Felicitaion ! vous avez gagné@Appuyer sur la touche ENTREE pour continuer".split("@");
        return strings;
    }
}
