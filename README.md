# PP_Term5_SI_Migration

## O projekcie
W tym projekcie za zadanie miałem napisać plik AI_Player.java (Players/AI_Player/src/main/java/put/ai/games/aiplayer/AI_Player.java) tak, aby napisany przezemnie algorytm wygrwał z przeciwnikami. W projekcie użyłem algorytm Alfa-Beta.


## Struktura i zmienne

Zmiany z roku na rok:
* ims-submissions/compile.php - ustawic nazwe gry
* newDeploySource.sh - ustawic nazwe gry
* Players/AlphaBetaPlayer/ - ustawic ewaluator na odpowiedni dla danej gry

Deployment:
* newDeploySource.sh - publikacja źródeł i JARów
* deployGames.sh - kopiowanie plikow w miejsca gdzie spodziewa sie ich supervisord, trzeba odpowiednio ustawic nazwe gry
* Zrestartowac supervisord, bo on trzyma otwarte compile.php
* Zmienic linki symboliczne w /home/ewa/deploy/players/ {Frodo,Meriadoc,Peregrin,Samwise}.jar
* jako master -> rm -rf ~/submissions/* ~/logs/*
* semantic: rm -rf /var/www/games/submissions/
* deadline w ims-submissions/index.php
* kopia ims-submissions/index.php do semantic:/var/www/games/

Baza danych (mysql na semantic, baza games)
* W razie potrzeby uzupełnić games_classes
* delete from games_results;
* delete from games_submissions where path is not null;
* delete from games_users where email like '%@student.put.poznan.pl';

Frontend (semantic:/var/www/games)
* Dla efektywnosci: create table games_special_submissions as select id,team from games_submissions where team in (select team from games_special);
