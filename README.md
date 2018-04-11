# TournamentBracket

Design and then implement a Java FX GUI program that allows user to upload a list of challengers, display a tournament bracket graphical user interface (GUI), and then allows the user to interact with the GUI to update the bracket until the winner of the tournament is determined.

The challengers are provided in a text only file in order of their ranking from first to last place after some type of pre-tournament ranking.
The program must then display a GUI interface with a seeded tournament bracket
Each challenge with two challengers will have an integer input fields for each challenger and a [Final Score] submit button.
Each challenge with challengers that are yet to be determined will have empty lines to show that challengers will be determined.

During the tournament (and while the program is running), when a game is completed:

Final scores are entered for each challenger
[Final Score] submit button is clicked
winners are indicated in the tournament bracket's next round
GUI is updated to reflect the final scores of the completed challenge and challengers for next round
When the final challenge is completed (the CHAMPIONSHIP), the GUI is updated to show:

the CHAMPION
the 2nd Place Finisher
the 3rd Place Finisher (3rd place is the challenger with the highest score of the Semi-Final round finalists that did not get to championship)
the final view of the tournament-bracket (shows all winners and scores for entire tournament)
Program must correctly handle all challenger lists with a size that is a power of 2 up to 16 challengers, i.e. {0,1,2,4,8,16}.  Being able to handle up to 64 challengers is even better.  Document what you program does.
