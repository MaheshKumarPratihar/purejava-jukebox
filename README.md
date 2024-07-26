# JUKE BOX -> USING PURE JAVA

NOTE -> 

    File "jukebox-input.txt" has commands.    
    File "songs.csv" has songs

RUN:
   **_use below command on terminal to and it will do the magic_**
```
mvn spring-boot:run -P jukeBox -Dspring-boot.run.arguments="INPUT_FILE=jukebox-input.txt"
```
   **_or you can run the "JukeboxApplication.java" by uncommenting the lines_**

## *_Functional Requirements_*
1. A user can create a playlist from a pool of available songs.
2. A user can delete a playlist.
3. A user can add / delete songs from the playlist.
4. A user can start playing songs by choosing a playlist. On choosing a playlist, the first song in the playlist will start playing.
5. A user can switch songs by using Next, Back command or by choosing another song from the playlist that has been chosen.
   1. On reaching the end, Next will switch to the first song in the current playlist.
   2. On reaching the start, Back will switch to the last song in the playlist.
   3. Only one song can be played at a time.
6. A user can choose to play the song from another playlist if and only if that playlist is selected. 
   Basically two operations have to be done to successfully play the song of their choice.
   1. Select the playlist ( which will play the first song when selected )
   2. Choose the song of your choice.
7. An album is a collection of songs owned by the original artist / artist group .

    ```
    1. Artist Group Example:- One Direction.
    2. One Direction Group is the album owner and is considered as an artist.
    3. Each song can feature multiple artists, but it will be owned by the artist whose album this song belongs to.
    4. Each song can only be a part of one album.
    ```

# **_Below are the commands to be executed by Command Executor_**
## **_Queries: Input and Output Format_**

## **_1. LOAD-DATA_**
Load all the songs from csv into the Song repository.
CSV File Input format:

	SONG_NAME,GENRE,ALBUM_NAME,ALBUM_ARTIST, “#” separated Featured Artists
Sample CSV File:

	South of the Border,Pop,No.6 Collaborations Project,Ed Sheeran,Ed-Sheeran#Cardi.B#Camilla Cabello
	Remember the Name,Pop,No.6 Collaborations Project,Ed Sheeran,Ed-Sheeran#Eminem#50 Cents


    Data File:- Song.csv
    Input format:
    LOAD-DATA { Input csv file }

_**Note:- Order in which the song is added in the playlist must be maintained.**_

    Sample input:
    LOAD-DATA songs.csv
    Output Format Schema:
    Songs Loaded successfully


## **_2. CREATE-USER_**
Create a new user in the system
Input format:

    CREATE-USER { Name }

    Sample input:
    CREATE-USER Kiran
    Output Format Schema:
    { User id } { User Name }
    Sample Output:
    1 Kiran


## **_3. CREATE-PLAYLIST_**
Create a new Playlist from a pool of songs. A song could exist in multiple playlists.

    Input format:
    CREATE-PLAYLIST { USER_ID } { PLAYLIST_NAME } { List of Song IDs }

    Note:- Order in which the song is added in the playlist must be maintained.

```
Sample input:
CREATE-PLAYLIST 1  MY_PLAYLIST_1 1 2 3 4 5
Output Format Schema:
Playlist ID - { Playlist ID  }

Or

Error Message if Any of the Above Song IDs is not present in the pool.
----------------------------------------------
Sample Output:
Playlist ID - 1

Or

Some Requested Songs Not Available. Please try again.
```

## **_4. DELETE-PLAYLIST_**
Delete a Playlist given Playlist ID if it exists in the system.
    
    Input format:
    DELETE-PLAYLIST { USER_ID } { Playlist-ID }
```
Sample input:
DELETE-PLAYLIST 1 1
Output Format Schema:
Delete Successful
Or
Error Message if the Playlist IDs do not exist.
----------------------------------------------
Sample Output:
Delete Successful
Or
Playlist Not Found
```

## **_5. MODIFY-PLAYLIST_**
Modify a Playlist to add/delete Song from the playlist.
```
Input format:
To add Song
MODIFY-PLAYLIST ADD-SONG { USER_ID } {Playlist-ID } { List of Song IDs }
```
**_Note:- Do not add the song again if the Song ID already exists in the playlist._**
```
To delete Song
MODIFY-PLAYLIST DELETE-SONG { USER_ID } {Playlist-ID } { List of Song IDs }
```
```
Sample input:
To add Song
MODIFY-PLAYLIST ADD-SONG 1 1 6 7
```
```
To Delete Song
MODIFY-PLAYLIST DELETE-SONG 1 1 3 4
```

**_Output Format Schema:_**
```
To add Song
Playlist ID - { Playlist ID  }
Playlist Name - { Playlist Name }
Song IDs - { List of final Song IDs}
Or
Error Message if Any of the Requested Song IDs is not present in the pool.
```
```
To delete Song
Playlist ID - { Playlist ID  }
Playlist Name - { Playlist Name }
Song IDs - { List of final Song IDs }
Or
Error Message if Any of the Requested Song IDs to be deleted are not present in the playlist.
```

```
Sample Output:
To add Song
Playlist ID - 1
Playlist Name - MY_PLAYLIST_1
Song IDs - 1 2 3 4 5 6 7
Or
Some Requested Songs Not Available. Please try again.
```

```
To delete Song
Playlist ID - 1
Playlist Name - MY_PLAYLIST_1
Song IDs - 1 2 5 6 7
Or
Some Requested Songs for Deletion are not present in the playlist. Please try again.
```

## **_6. PLAY-PLAYLIST_**
Start playing the playlist. The output will be the first song of the playlist.

    Input format:
    PLAY-PLAYLIST  { USER_ID } { Playlist-ID }

```
Sample input:
PLAY-PLAYLIST 1 1
Output Format Schema:
Current Song Playing
Song  -  { Song Name  }
Album - { Album Name  }  
Artists - { List of Artists }
Or
Error Message if Playlist is empty.
```
**_Note:-  The output song will be the first song of the playlist.**_
```
Sample Output:
Current Song Playing
Song - Save Your Tears(Remix)
Album -  After Hours
Artists -  TheWeeknd,Ariana Grande  
Or
Playlist is empty.
```

## **_7. PLAY-SONG_**
Switch songs on the active playlist currently playing any song.

**_Input format:_**
    
    Switch to play a previous song in the active playlist.
    PLAY-SONG { USER_ID }  BACK
    
    Switch to play the next song in the active playlist.
    PLAY-SONG  { USER_ID } NEXT
    
    Switch to the preferred song in the playlist.
    PLAY-SONG { USER_ID }  { Song ID }

**_Output Format Schema:_**

_Switch to play a **previous song** in the active playlist._

    Current Song Playing
    Song  -  { Song Name  }
    Album - { Album Name  }  
    Artists - { List of Artists

_Switch to play the **next song** in the active playlist._

    Current Song Playing
    Song  -  { Song Name  }
    Album - { Album Name  }  
    Artists - { List of Artists

_Switch to the preferred song in the playlist._

    Current Song Playing
    Song  -  { Song Name  }
    Album - { Album Name  }  
    Artists - { List of Artists
    Or
    Error Message if above Song ID is not part of a current active playlist.

**Sample Output:**
_Switch to play a **previous song** in the active playlist._

    Current Song Playing
    Song - Save Your Tears(Remix)
    Album -  After Hours
    Artists -  TheWeeknd,Ariana Grande

_Switch to play the **next song** in the active playlist._

    Current Song Playing
    Song - Save Your Tears(Remix)
    Album -  After Hours
    Artists -  TheWeeknd,Ariana Grande

_Switch to the **preferred song** in the playlist._

    Current Song Playing
    Song - Save Your Tears(Remix)
    Album -  After Hours
    Artists -  TheWeeknd,Ariana Grande
    Or
    Song Not Found in the current active playlist.


## **_Loading Data into the Application_**



**_INPUT COMMANDS:-_**

    LOAD-DATA songs.csv
    CREATE-USER Kiran
    CREATE-PLAYLIST 1 MY_PLAYLIST_1 1 4 5 6
    CREATE-PLAYLIST 1 MY_PLAYLIST_2 1
    DELETE-PLAYLIST 1 2
    PLAY-PLAYLIST 1 1
    MODIFY-PLAYLIST ADD-SONG 1 1 7
    MODIFY-PLAYLIST DELETE-SONG 1 1 7
    PLAY-SONG 1 5
    PLAY-SONG 1 NEXT
    PLAY-SONG 1 NEXT
    PLAY-SONG 1 BACK
    PLAY-SONG 1 BACK
    PLAY-SONG 1 19

**_OUTPUT:-_**

    Songs Loaded successfully
    1 Kiran
    Playlist ID - 1
    Playlist ID - 2
    Delete Successful
    Current Song Playing
    Song - South of the Border
    Album - No.6 Collaborations Project
    Artists - Ed Sheeran,Cardi.B,Camilla Cabello
    Playlist ID - 1
    Playlist Name - MY_PLAYLIST_1
    Song IDs - 1 4 5 6 7
    Playlist ID - 1
    Playlist Name - MY_PLAYLIST_1
    Song IDs - 1 4 5 6
    Current Song Playing
    Song - Cross Me
    Album - No.6 Collaborations Project
    Artists - Ed Sheeran,Chance The Rapper,PnB Rock
    Current Song Playing
    Song - Give Life Back To Music
    Album - Random Access Memories
    Artists - Daft Punk,Nile Rodgers
    Current Song Playing
    Song - South of the Border
    Album - No.6 Collaborations Project
    Artists - Ed Sheeran,Cardi.B,Camilla Cabello
    Current Song Playing
    Song - Give Life Back To Music
    Album - Random Access Memories
    Artists - Daft Punk,Nile Rodgers
    Current Song Playing
    Song - Cross Me
    Album - No.6 Collaborations Project
    Artists - Ed Sheeran,Chance The Rapper,PnB Rock
    Given song id is not a part of the active playlist

	
	



	




	







	




