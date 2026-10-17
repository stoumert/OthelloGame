package fr.univ_amu.m1info.board_game_library.model.service.AI;

public enum AI {
    EASY("Easy"),
    MEDIUM("Medium"),
    HARD("Hard");
    private final String name;
    AI(String name) {
        this.name = name;
    }
    public String getName(){
        return name;
    }
    public AIStrategy getStrategy(){
        return switch (this) {
            case EASY -> new EasyAIStrategy();
            case MEDIUM -> new MediumAIStrategy();
            case HARD -> new HardAIStrategy();
        };
    }

}
