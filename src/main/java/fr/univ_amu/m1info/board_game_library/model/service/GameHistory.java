package fr.univ_amu.m1info.board_game_library.model.service;

import java.util.Stack;

/**
 * cette classe gere l'historique des etats
 * implemente les fonctionalitées undo et redo
 *
 */
public class GameHistory {

    private final Stack<HistoryState> undoStack;
    private final Stack<HistoryState> redoStack;

    public GameHistory() {
        this.undoStack = new Stack<>();
        this.redoStack = new Stack<>();
    }

    /**
     * Ajoute un etat dans la pile d'annulation
     * en meme temps vide la pile de retablissement
     * on ne peu pas refaire les actions annulés précedemment
     * @param historyState le metrice de pawns avec le joueurs courant , l'état a sauvegarder dans les piles
     */
    public void push(HistoryState historyState) {
        redoStack.clear();
        undoStack.push(historyState);
    }


    /**
     * vide les deux piles undo et redo (annulation et retablissement )
     */
    public void clearAll() {
        undoStack.clear();
        redoStack.clear();
    }

    /**
     * verifie  si c'est possible de faire un retour en arriere
     * cela en verifiant si la pile undo est vide ou pas (si il existe des etats precedents)
     * @return False si la pile undo (annulation) est vide, True sinon
     */
    public boolean canUndo() {
        return !undoStack.isEmpty();
    }

    /**
     * verifie si c'est possible de faire un redo (avancer)
     *
     * @return False si la pile redo (rétablissement) est vide, True sinon
     */
    public boolean canRedo() {
        return !redoStack.isEmpty();
    }

    /**
     * fais le retablissement (redo)
     * recupere le prochain etat depuis la pile de retablissement redo
     * place l'etat actuel dans la pile d'annulation undo
     *
     * @param historyState l'état actuel avant redo
     * @return l'état restauré depuis la pile redo, ou null si aucun etat n' y est
     */
    public HistoryState redo(HistoryState historyState) {
        if (canRedo()) {
            HistoryState state = redoStack.pop();
            undoStack.push(historyState);
            return state;
        } else {
            return null;
        }
    }

    /**
     * s'occupe de faire un retour en arriere
     * recupere l'etat actuel et l'empile  dans la pile de redo (retablissement)
     * recupere l'etat precedent  de la pile undo (annulation).
     *
     * @param historyState l'état actuel avant l'opération d'annulation
     * @return l'état restauré depuis la pile undo, ou null si aucune annulation n'est possible
     */
    public HistoryState undo(HistoryState historyState) {
        if (canUndo()) {
            HistoryState state = undoStack.pop();
            redoStack.push(historyState);
            return state;
        } else {
            return null;
        }
    }
}
