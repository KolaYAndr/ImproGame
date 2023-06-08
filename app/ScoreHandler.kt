class ScoreHandler(private var scoreTeam1: Int = 0, private var scoreTeam2: Int = 0) {
    private var roundScoreTeam1 = 0
        get() = field
        set(value) {
            if value >= 0 field = value
        }
    private var roundScoreTeam2 = 0
        get() = field
        set(value) {
            if value >= 0 field = value
        }

    fun reset(){
       scoreTeam1 += roundScoreTeam1
       scoreTeam2 += roundScoreTeam2

       roundScoreTeam1 = 0
       roundScoreTeam2 = 0
    }
}