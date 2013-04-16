/* 
 * File:     World.java
 * Author:   Jan-Peter v. Hunnius
 *           s_jhunni@gmx.de
 * Created:  17th of August, 2000.
 * Last mod: 20th of October, 2000.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */


/*
 * Imports
 */
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;


/*
 * World is the controller class for the gaming domain
 * fantasy world
 */
public final class World
{

  /*
   * Private member variables
   */
  private final Vector actorList;
  private final Vector livingList;
  private final Vector playerCharacterList;

  private final String WORLD_DIRECTORY   = "../world/";
  private final String TITLE_SCREEN_FILE =
    WORLD_DIRECTORY + "titlescreen.asc";

  private       String titleScreen = "";


  /*
   * Public member variables
   */
  public boolean shutDown;


  /*
   * Constructors
   */

  /* This constructor initializes the world. It sets shutdown to
     false to prevent immediate exit */
  public World()
  {

    /* Set shutdown to false */
    shutDown = false;

    /* Create a vector for all actors */
    actorList = new Vector();

    /* Create a vector for all livings */
    livingList = new Vector();

    /* Create a vector for all player characters */
    playerCharacterList = new Vector();

    /* Try to open the title screen file */
    try
    {

      /* Open the title screen file */
      FileReader titleScreenFile = new FileReader( TITLE_SCREEN_FILE );

      /* While there are characters to read */
      while( titleScreenFile.ready() )
      {

        /* Add a read character to the title screen */
        titleScreen += ( char ) titleScreenFile.read();
      }

      /* Close the title screen file */
      titleScreenFile.close();
    }

    /* If the file doesn't exist */
    catch( FileNotFoundException FNFE )
    {

      /* Give a message to the console */
      System.out.println( "Title screen file not found: "
        + TITLE_SCREEN_FILE );
      
      /* Shut down */
      shutDown();
    }

    /* If an I/O exception occurs */
    catch( IOException IOE )
    {

      /* Give a message to the console */
      System.out.println( "Error reading title screen file."
        + TITLE_SCREEN_FILE );
      
      /* Shut down */
      shutDown();
    }
  }
  

  /*
   * Public member functions
   */

  /* This function shuts the world down */
  public final void shutDown()
  {

    /* Run through all player characters and save them */
    for( int index = 0;
      index != playerCharacterList.size();
      index++ )
    {

      /* Get a player character reference */
      PlayerCharacter playerCharacter =
        ( PlayerCharacter ) playerCharacterList.elementAt( index );

      /* If an actor is there */
      if( playerCharacter != null )
      {

        /* Write a message to the player */
        playerCharacter.writeLine( "JAM is shutting down..." );

        /* Clean up the player character */
        if( playerCharacter.save() == true )
        {

          /* Write a message to the player */
          playerCharacter.writeLine(
            "Saved your character. Goodbye." );
        }

        /* If it fails */
        else
        {

          /* Write a message to the player */
          playerCharacter.writeLine( "Failed to save your "
            + "character. Please contact an immortal. Goodbye." );
        }

        /* Clean up the player character */
        playerCharacter.cleanUp();
      }
    }

    /* Set shutdown to true */
    shutDown = true;
  }


  /* This function handles regular updates. It lets all actors act */
  public final boolean update()
  {
    /* Run through all actors and let them act */
    for( int index = 0;
      index != actorList.size();
      index++ )
    {

      /* Get an actor reference */
      Actor actor = ( Actor ) actorList.elementAt( index );

      /* If an actor is there */
      if( actor != null )
      {

        /* If the actor is not busy */
        if( actor.getBusyPulses() <= 0 )
        {

          /* Let the actor act */
          actor.act();
        }

        /* If the actor is busy */
        else
        {

          /* Decrease the actor's busy pulses */
          actor.decreaseBusyPulses();
        }
      }
    }

    /* Return success */
    return( true );
  }


  /* This function returns a string containing the world's title
     screen */
  public final String getTitleScreen()
  {

    /* Return the title screen */
    return( titleScreen );
  }


  /* This function adds an actor to the actor list */
  public final void addActor( Actor actor )
  {

    /* If the actor is already in the list */
    if( actorList.contains( actor ) )
    {

      /* Immediately return without adding the actor */
      return;
    }

    /* Run though all actors and find an empty slot */
    for( int index = 0;
      index < actorList.size();
      index++ )
    {

      /* Is the slot empty? */
      if( actorList.elementAt( index ) == null )
      {

        /* Add the actor here and stop*/
        actorList.setElementAt( actor, index );
        break;
      }
    }

    /* Was the actor not inserted yet? */
    if( ! actorList.contains( actor ) )     

      /* Add the actor to the list */
      actorList.addElement( actor );
  }


  /* This function removes an actor from the actor list */
  public final void removeActor( Actor actor )
  {

    /* If the actor is in the list */
    if( actorList.contains( actor ) )
    {

      /* Set the position where the actor is located within the list
         to null (marks an empty slot) */
      actorList.setElementAt( null, actorList.indexOf( actor ) );
    }
  }


  /* This function adds a living to the living list */
  public final void addLiving( Living living )
  {

    /* If the living is already in the list */
    if( livingList.contains( living ) )
    {

      /* Immediately return without adding the living */
      return;
    }

    /* Run though all livings and find an empty slot */
    for( int index = 0;
      index < livingList.size();
      index++ )
    {

      /* Is the slot empty? */
      if( livingList.elementAt( index ) == null )
      {

        /* Add the living here and stop*/
        livingList.setElementAt( living, index );
        break;
      }
    }

    /* Was the living not inserted yet? */
    if( ! livingList.contains( living ) )     

      /* Add the living to the list */
      livingList.addElement( living );
  }


  /* This function removes a living from the living list */
  public final void removeLiving( Living living )
  {

    /* If the living is in the list */
    if( livingList.contains( living ) )
    {

      /* Set the position where the living is located within the list
         to null (marks an empty slot) */
      livingList.setElementAt( null, livingList.indexOf( living ) );
    }
  }


  /* This function adds a player character to the player character
     list */
  public final void addPlayerCharacter( PlayerCharacter playerCharacter )
  {

    /* If the player character is already in the list */
    if( playerCharacterList.contains( playerCharacter ) )
    {

      /* Immediately return without adding the player character */
      return;
    }

    /* Run though all player characters and find an empty slot */
    for( int index = 0;
      index < playerCharacterList.size();
      index++ )
    {

      /* Is the slot empty? */
      if( playerCharacterList.elementAt( index ) == null )
      {

        /* Add the player character here and stop*/
        playerCharacterList.setElementAt( playerCharacter, index );
        break;
      }
    }

    /* Was the player character not inserted yet? */
    if( ! playerCharacterList.contains( playerCharacter ) )     

      /* Add the player character to the list */
      playerCharacterList.addElement( playerCharacter );
  }


  /* This function removes a player character from the player
     character list */
  public final void removePlayerCharacter
    ( PlayerCharacter playerCharacter )
  {

    /* If the player character is in the list */
    if( playerCharacterList.contains( playerCharacter ) )
    {

      /* Set the position where the player character is located within
         the list to null (marks an empty slot) */
      playerCharacterList.setElementAt( null,
        playerCharacterList.indexOf( playerCharacter ) );
    }
  }


  /* This function removes a player character from the player
     character list */
  public final PlayerCharacter getSameNamePlayerCharacter
    ( PlayerCharacter playerCharacter )
  {

    /* A player character to check */
    PlayerCharacter candidate;

    /* Run though all player characters and find an empty slot */
    for( int index = 0;
      index < playerCharacterList.size();
      index++ )
    {

      /* Copy the slot into the candidate */
      candidate = ( PlayerCharacter )
        playerCharacterList.elementAt( index );

      /* Is a player character in the slot ? */
      if( candidate != null )
      {

        /* Does he have the right name, but is different from the
           character given as argument ? */
        if( candidate != playerCharacter
          && candidate.getName().equals(
            playerCharacter.getName() ) )
        {

          /* Return the candidate */
          return( candidate );
        }
      }
    }

    /* Return null if no candidate was right */
    return( null );
  }
}
