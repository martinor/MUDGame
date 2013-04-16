/* 
 * File:     PlayerCharacter.java
 * Author:   Jan-Peter v. Hunnius
 *           s_jhunni@gmx.de
 * Created:  13th of October, 2000.
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
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.StringTokenizer;


/*
 * PlayerCharacter represents a hero managed by a player
 */
public class PlayerCharacter extends Living
{

  /*
   * Private member variables
   */
  private       Connection connection;
  private final String     PLAYER_DIRECTORY = "../world/players/";

  private       long       idlePulses = 0;

  private       Action     action;

  private       String     password    = "";
  private       boolean    color       = false;

  /* State variable and constants */
  private       int        state;
  private final int        STATE_PARSE_NAME         =   1;
  private final int        STATE_CONFIRM_NAME       =   2;
  private final int        STATE_PARSE_NEW_PASSWORD =   3;
  private final int        STATE_CONFIRM_PASSWORD   =   4;
  private final int        STATE_CHECK_PASSWORD     =   5;
  private final int        STATE_PARSE_COLOR        =   6;
  private final int        STATE_PARSE_RECONNECT    =   7;
  private final int        STATE_PLAYING            = 100;

  /* Color numbers */
  private final int NORMAL        =  0;
  private final int BLACK         =  1;
  private final int RED           =  2;
  private final int GREEN         =  3;
  private final int BROWN         =  4;
  private final int BLUE          =  5;
  private final int MAGENTA       =  6;
  private final int CYAN          =  7;
  private final int LIGHT_GREY    =  8;
  private final int GREY          =  9;
  private final int LIGHT_RED     = 10;
  private final int LIGHT_GREEN   = 11;
  private final int YELLOW        = 12;
  private final int LIGHT_BLUE    = 13;
  private final int LIGHT_MAGENTA = 14;
  private final int LIGHT_CYAN    = 15;
  private final int WHILE         = 16;
  
  /* Color code sequences */
  private final String COLOR_CODES[] =
  {
    "{nor}",
    "{bla}", "{red}", "{gre}", "{bro}",
    "{blu}", "{mag}", "{cya}", "{GRY}",
    "{gry}", "{RED}", "{GRE}", "{yel}",
    "{BLU}", "{MAG}", "{CYA}", "{whi}"
  };
  
  /* ANSI color codes */
  private final String ANSI_CODES[] =
  {
    "\033[0m",
    "\033[0;30m", "\033[0;31m", "\033[0;32m", "\033[0;33m",
    "\033[0;34m", "\033[0;35m", "\033[0;36m", "\033[0;37m",
    "\033[1;30m", "\033[1;31m", "\033[1;32m", "\033[1;33m",
    "\033[1;34m", "\033[1;35m", "\033[1;36m", "\033[1;37m",
  };
  

  /*
   * Constructors
   */

  /* This constructor initializes the player character */
  public PlayerCharacter( Connection connection, World world,
    String name )
  {

    /* Call Actor's constructor to initialize the world and register
       in the actorList */
    super( world, name );

    /* Initialize the connection reference */
    this.connection = connection;

    /* Register this player within the world's player character
       list */
    world.addPlayerCharacter( this );

    /* Get the world's title screen and display write it to the
       player */
    writeLine( world.getTitleScreen() );

    /* Ask for the player character's name */
    write( "How shall we call you ? " );

    /* Set the state to STATE_PARSE_NAME */
    state = STATE_PARSE_NAME;
  }


  /*
   * Public member functions
   */

  /* This function returns the player character's password */
  public final String getPassword()
  {

    /* Return the password */
    return( password );
  }

  /* This function writes text to the player's connection */
  public final void write( String text )
  {

    /* If the player character has an active connection */
    if( connection != null )
    {

      /* Write the text to the connection */
      connection.write( parseColors( text ) );
    }
  }


  /* This function writes a line of text to the player's connection */
  public final void writeLine( String line )
  {

    /* If the player character has an active connection */
    if( connection != null )
    {

      /* Write the line to the connection */
      connection.writeLine( parseColors( line ) );
    }
  }


  /* This function writes a prompt to the player's connection */
  public final void writePrompt()
  {

    /* Write the text to the connection */
    write( "\n\r{red}<> {nor}" );
  }


  /* This function cleans up the player */
  public final void cleanUp()
  {

    /* If the connection is not null */
    if( connection != null )
    {

      /* Clean up the connection */
      connection.cleanUp();
    }

    /* Remove character from the player character list */
    world.removePlayerCharacter( this );

    /* Remove character from the living list */
    world.removeLiving( this );

    /* Remove character from the actor list */
    world.removeActor( this );
}


  /* This function deletes the player file */
  public final boolean delete()
  {

    /* Initialize the player character file */
    File playerFile = new File( PLAYER_DIRECTORY + name + ".xml" );

    /* Delete the file */
    return( playerFile.delete() );
  }


  /* This function saves the player file */
  public final boolean save()
  {

    /* Try to open the player file */
    try
    {

      /* A string to hold the raw player data */
      String playerData = "";
      
      /* Open the player file */
      FileWriter playerFile = new FileWriter(
        PLAYER_DIRECTORY + name + ".xml" );

      /* Start the player character tag */
      playerFile.write( "<PLAYER_CHARACTER>\n" );

      /* Write the name */
      playerFile.write( "\t<NAME>" + name + "</NAME>\n" );

      /* Write the password */
      playerFile.write( "\t<PASSWORD>" + password + "</PASSWORD>\n" );

      /* Write the level */
      playerFile.write( "\t<LEVEL>" + level + "</LEVEL>\n" );

      /* Write the color field */
      playerFile.write( "\t<COLOR>" + color + "</COLOR>\n" );

      /* Close the player character tag */
      playerFile.write( "</PLAYER_CHARACTER>\n" );

      /* Close the player file */
      playerFile.close();
    }

    /* If an I/O exception occurs */
    catch( IOException IOE )
    {

      /* Give a message to the console */
      System.out.println( "Error writing player file: "
        + PLAYER_DIRECTORY + name );

      /* return false to indicate failure */
      return( false );
    }

    /* On success return true */
    return( true );
  }


  /* This function loads the player file */
  public final boolean load()
  {

    /* A string to hold the raw player data */
    String playerData = "";
      
    /* Try to open the player file */
    try
    {

      /* Open the player file */
      FileReader playerFile = new FileReader(
        PLAYER_DIRECTORY + name + ".xml" );

      /* While there are characters to read */
      while( playerFile.ready() )
      {

        /* Add a read character to the player data */
        playerData += ( char ) playerFile.read();
      }

      /* Close the player file */
      playerFile.close();
    }

    /* If the file doesn't exist */
    catch( FileNotFoundException FNFE )
    {

      /* Return false to indicate a new player */
      return( false );
    }

    /* If an I/O exception occurs */
    catch( IOException IOE )
    {

      /* Give a message to the console */
      System.out.println( "Error reading player file: "
        + PLAYER_DIRECTORY + name );

      /* return false to indicate failure, the player gets deleted
         anyway */
      return( false );
    }

    /* Create a tokenizer to parse the data */
    StringTokenizer tokenizer = new StringTokenizer(
      playerData, " <>\n\r\t", true );

    /* A string to save a token */
    String token;

    /* Two strings to parse a variable name and a value */
    String variable = "", value = "";

    /* Constants for the parse state */
    final short PARSE_ROOT     = 1;
    final short PARSE_VARIABLE = 2;
    final short PARSE_VALUE    = 3;
    final short PARSE_ENDTAG   = 4;
    final short PARSE_NOTHING  = 5;

    /* A string to save a token */
    short parsingState = PARSE_ROOT;

    /* Run through the tokens */
    while( tokenizer.hasMoreTokens() )
    {

      /* Read the next token */
      token = tokenizer.nextToken();

      /* If the next token is a blank, a tab or a new line char */
      if( " ".equals( token ) || "\t".equals( token )
        || "\n".equals( token ) || "\r".equals( token ) )
      {

        /* continue */
        continue;
      }

      /* Dependent on the parsing state */
      switch( parsingState )
      {

        /* If the root tag has to be parsed */
        case PARSE_ROOT:
        {

          /* If there are not enough tokens */
          if( tokenizer.countTokens() < 2 )
          {

            /* Give a message to the console */
            System.out.println( "Not enough token in player file "
              + PLAYER_DIRECTORY + name + " while parsing the root "
              + "tag." );

            /* return false to indicate failure, the player gets
               deleted anyway */
            return( false );
          }

          /* If the root tag is not opened */
          if( ! "<".equals( token ) )
          {

            /* Give a message to the console */
            System.out.println( "Root tag in player file "
              + PLAYER_DIRECTORY + name + " not opened." );

            /* return false to indicate failure, the player gets
               deleted anyway */
            return( false );
          }

          /* Read the root token */
          token = tokenizer.nextToken();

          /* If we have the wrong root tag */
          if( ! "PLAYER_CHARACTER".equals( token ) )
          {

            /* Give a message to the console */
            System.out.println( "Wrong root tag in player file "
              + PLAYER_DIRECTORY + name + ":" + token + "." );

            /* return false to indicate failure, the player gets
               deleted anyway */
            return( false );
          }

          /* Read the root token closer */
          token = tokenizer.nextToken();

          /* If the root tag is not closed */
          if( ! ">".equals( token ) )
          {

            /* Give a message to the console */
            System.out.println( "Root tag in player file "
              + PLAYER_DIRECTORY + name + " not closed." );

            /* return false to indicate failure, the player gets
               deleted anyway */
            return( false );
          }

          /* Set the parsing state to PARSE_VARIABLE */
          parsingState = PARSE_VARIABLE;

          /* Jump over the other cases */
          break;
        }

        /* If a variable name has to be parsed */
        case PARSE_VARIABLE:
        {

          /* If there are not enough tokens */
          if( tokenizer.countTokens() < 2 )
          {

            /* Give a message to the console */
            System.out.println( "Not enough tokens in player file "
              + PLAYER_DIRECTORY + name + " while parsing a "
              + "variable." );

            /* return false to indicate failure, the player gets
               deleted anyway */
            return( false );
          }

          /* If the variable tag is not opened */
          if( ! "<".equals( token ) )
          {

            /* Give a message to the console */
            System.out.println( "Found no <, but had to parse a "
              + "variable in " + PLAYER_DIRECTORY + name + "." );

            System.out.println( token);

            /* return false to indicate failure, the player gets
               deleted anyway */
            return( false );
          }

          /* Read the variable tag */
          variable = tokenizer.nextToken();

          /* Read the root token closer */
          token = tokenizer.nextToken();

          /* If the variable tag is not closed */
          if( ! ">".equals( token ) )
          {

            /* Give a message to the console */
            System.out.println( "Tag " + variable + " in player file "
              + PLAYER_DIRECTORY + name + " not closed." );

            /* return false to indicate failure, the player gets
               deleted anyway */
            return( false );
          }

          /* If we parsed the /PLAYER_CHARACTER tag */
          if( "/PLAYER_CHARACTER".equals( variable ) )
          {

            /* We're finished, set parsing state to PARSE_NOTHING */
            parsingState = PARSE_NOTHING;
          }

          /* Empty the value string */
          value = "";

          /* Set the parsing state to PARSE_VALUE */
          parsingState = PARSE_VALUE;

          /* Jump over the other cases */
          break;
        }

        /* If a value has to be parsed */
        case PARSE_VALUE:
        {

          /* If the token was "<" */
          if( "<".equals( token ) )
          {

            /* If we have to set the name */
            if( "NAME".equals( variable ) )
            {

              /* Set the name to the read value */
              name = value;
            }

            /* If we have to set the password */
            else if( "PASSWORD".equals( variable ) )
            {

              /* Set the password to the read value */
              password = value;
            }

            /* If we have to set the level */
            else if( "LEVEL".equals( variable ) )
            {

              /* Try to set the level */
              try
              {

                /* Set the level to the read value */
                level = new Short( value ).shortValue();
              }

              /* On NumberFormatException */
              catch( NumberFormatException NFE )
              {

                /* Give a message to the console */
                System.out.println( "Not a number given for level in "
                  + PLAYER_DIRECTORY + name + "." );

                /* return false to indicate failure, the player gets
                   deleted anyway */
                return( false );
              }
            }

            /* If we have to set the color */
            else if( "COLOR".equals( variable ) )
            {

              /* Try to set the color */
              try
              {

                /* Set the level to the read value */
                color = new Boolean( value ).booleanValue();
              }

              /* On NumberFormatException */
              catch( NumberFormatException NFE )
              {

                /* Give a message to the console */
                System.out.println( "Not a boolean given for color in "
                  + PLAYER_DIRECTORY + name + "." );

                /* return false to indicate failure, the player gets
                   deleted anyway */
                return( false );
              }
            }

            /* Set the parsing state to PARSE_ENDTAG */
            parsingState = PARSE_ENDTAG; }


          /* If the token was not "<" */
          else
          {

            /* Add the read token to the value string */            
            value += token;
          }

          /* Jump over the other cases */
          break;
        }

        /* If an end tag has to be parsed */
        case PARSE_ENDTAG:
        {

          /* If there are not enough tokens */
          if( tokenizer.countTokens() < 1 )
          {

            /* Give a message to the console */
            System.out.println( "Not enough tokens in player file "
              + PLAYER_DIRECTORY + name + " while parsing an "
              + "end tag." );

            /* return false to indicate failure, the player gets
               deleted anyway */
            return( false );
          }

          /* If the end tag does not correspond to the variable */
          if( ! ( "/" + variable ).equals( token ) ) {

            /* Give a message to the console */
            System.out.println( "Tag " + variable + " in player file "
              + PLAYER_DIRECTORY + name + " not ended correctly." );

            /* return false to indicate failure, the player gets
               deleted anyway */
            return( false );
          }

          /* Read the next token, which should be ">" */
          token = tokenizer.nextToken();

          /* If the end tag is not closed */
          if( ! ">".equals( token ) )
          {

            /* Give a message to the console */
            System.out.println( "Endtag " + variable
              + " in player file " + PLAYER_DIRECTORY
              + name + " not closed correctly." );

            /* return false to indicate failure, the player gets
               deleted anyway */
            return( false );
          }

          /* Set the parsing state to PARSE_VARIABLE */
          parsingState = PARSE_VARIABLE;

          /* Jump over the other cases */
          break;
        }

        case PARSE_NOTHING:
        {

          /* Give a message to the console */
          System.out.println( "Parsed" + token + " in player file "
            + PLAYER_DIRECTORY + name + ", but I'm finished." );

          /* return false to indicate failure, the player gets
             deleted anyway */
          return( false );
        }
      }
    }

    /* On success return true */
    return( true );
  }


  /* This function lets the player character act if he wants to */
  public final void act()
  {

    /* The next pending command */
    String commandLine;

    /* If the player has an active connection */
    if( connection != null )
    {

      /* Put pending commands into the command list */
      while( ( commandLine = connection.readLine() ) != "" )
      {

        /* Add the command line to the command list */
        commandList.addElement( new String( commandLine ) );      
      }
    }

    /* If there are commands in the list */
    if( ! commandList.isEmpty() )
    {

      /* Get the first command from the list */
      String command =
        ( ( String ) commandList.firstElement() ).trim();
      
      /* Delete the first command from the list */
      commandList.removeElementAt( 0 );

      /* Perform the command */
      performCommand( command );

      /* Reset idle pulses */
      idlePulses = 0;
    }

    /* If there was no command in the list */
    else
    {

      /* Increase idel pulses */
      idlePulses++;

      /* If idle pules are more than 3000 (5 minutes) */
      if( idlePulses > 3000 )
      {

        /* Give a message to the idle player */
        writeLine( "\n\r\n\rDisconnecting due to inactivity..." );

        /* Clean up the player */
        cleanUp();
      }
    }
  }


  /* This function indicates an unknown command by writing "Huh?" */
  public final void unknownCommand()
  {

    /* We don't know this command */
    writeLine( "Huh?" );
  }


  /*
   * Protected member functions
   */

  /* This functions parses the color commands within a string and
     either converts them to ANSI or purges them depending on
     the color variable */
  private String parseColors( String text )
  {

    /* An int to run through all colors */
    int i;

    /* An int to keep an index within a string */
    int index;

    /* If the player has color turned on */
    if( color == true )
    {

      /* Run through all colors */
      for( i = 0; i < 17; i++ )
      {

        /* Store the index of the color code sequence i in index */
        index = text.indexOf( COLOR_CODES[ i ] );

        /* While the color code sequence i is contained in the
           string */
        while( index != -1 )
        {

          /* Convert the substring */
          text = text.substring( 0, index ) + ANSI_CODES[ i ]
            + text.substring( index + 5 );

          /* Store the index of the color code sequence i in index */
          index = text.indexOf( COLOR_CODES[ i ] );
        }
      }
    }

    /* If the player has color turned off */
    else
    {

      /* Run through all colors */
      for( i = 0; i < 17; i++ )
      {

        /* Store the index of the color code sequence i in index */
        index = text.indexOf( COLOR_CODES[ i ] );

        /* While the color code sequence i is contained in the
           string */
        while( index != -1 )
        {

          /* Convert the substring */
          text = text.substring( 0, index )
            + text.substring( index + 5 );

          /* Store the index of the color code sequence i in index */
          index = text.indexOf( COLOR_CODES[ i ] );
        }
      }
    }

    /* Return the converted string */
    return( text );
  }

  /* This function lets the actor perform a given command */
  protected void performCommand( String command )
  {

    /* The action depends primarily on the player's state */
    switch( state )
    {

      /* If the state is STATE_PARSE_NAME */
      case STATE_PARSE_NAME:
      {

        /* Set the player's name */
        name = command;

        /* Load the player file, if that fails:  */
        if( load() == false )
        {

          /* We have a new player, ask him, if the name is OK */
          write( "\n\rSo, we call you " + name + ", right (Y/N) ? " );

          /* Set the state to STATE_CONFIRM_NEW_PLAYER */
          state = STATE_CONFIRM_NAME;
        }

        /* If the load succeeded */
        else
        {

          /* Ask for the password */
          write( "\n\rWelcome, " + name
            + ", what is your secret phase ? " );
          
          /* Turn echo off */
          connection.echoOff();

          /* Set the state to STATE_CONFIRM_NEW_PLAYER */
          state = STATE_CHECK_PASSWORD;
        }

        /* Jump over the other cases */
        break;
      }

      /* If the state is STATE_CONFIRM_NAME */
      case STATE_CONFIRM_NAME:
      {

        /* If the player is sure about his name */
        if( command.toUpperCase().equals( "Y" ) )
        {

          /* Ask the player for a password */
          write( "\n\rWhat will your secret pass phrase be ? " );

          /* Turn echo off */
          connection.echoOff();

          /* Set the state to STATE_PARSE_NEW_PASSWORD */
          state = STATE_PARSE_NEW_PASSWORD;
        }

        /* If the player doesn't want that name */
        else
        {

          /* Reset the player's name */
          name = "";

          /* Ask the player for his real desired name */
          write( "\n\rWhat will your desired name be then ? " );

          /* Set the state to STATE_PARSE_NAME */
          state = STATE_PARSE_NAME;
        }

        /* Jump over the other cases */
        break;
      }

      /* If the state is STATE_PARSE_NEW_PASSWORD */
      case STATE_PARSE_NEW_PASSWORD:
      {

        /* Set the player's password */
        password = command;

        /* Ask the player for his real desired name */
        write( "\n\rPlease confirm your pass phase : " );

        /* Set the state to STATE_CONFIRM_PASSWORD */
        state = STATE_CONFIRM_PASSWORD;

        /* Jump over the other cases */
        break;
      }

      /* If the state is STATE_CONFIRM_PASSWORD */
      case STATE_CONFIRM_PASSWORD:
      {

        /* If the passwords match */
        if( password.equals( command ) )
        {

          /* Turn echo on */
          connection.echoOn();

          /* Ask the player for ansi color */
          write( "\n\r\n\rDo you want ansi colors (Y/N) ? " );

          /* Set the state to STATE_COLOR */
          state = STATE_PARSE_COLOR;
        }

        /* If the passwords don't match */
        else
        {

          /* Let the player again type the password */
          writeLine(
            "\n\r\n\rPass phases didn't match. Try again." );
          write( "What will your secret pass phrase be ? " );

          /* Set the state to STATE_PARSE_NEW_PASSWORD */
          state = STATE_PARSE_NEW_PASSWORD;
        }

        /* Jump over the other cases */
        break;
      }

      /* If the state is STATE_CHECK_PASSWORD */
      case STATE_CHECK_PASSWORD:
      {

        /* If the password is right */
        if( command.equals( password ) )
        {

          /* A reference to an old player character, if available */
          PlayerCharacter oldPlayerCharacter
            = world.getSameNamePlayerCharacter( this );

          /* Turn echo on */
          connection.echoOn();

          /* If the player is already connected */
          if( oldPlayerCharacter != null )
          {

            /* Ask the player to recoonect */
            write( "\n\rThis character is already playing. "
              + "Do you wish to reconnect (Y/N) ? " );

            /* Set the state to STATE_PARSE_RECONNECT */
            state = STATE_PARSE_RECONNECT;
          }

          /* If the player is not yet connected */
          else
          {

            /* Welcome the player to the game */
            writeLine( "\n\r\n\rWelcome back to Jam. "
              + "May your visit be adventurous!" );

            /* Set the state to STATE_PLAYING */
            state = STATE_PLAYING;

            /* Print a prompt */
            writePrompt();
          }
        }

        /* If the password is wrong */
        else
        {

          /* Give a message */
          writeLine( "\n\r\n\rWrong password! Disconnecting..." );

          /* Turn echo on */
          connection.echoOn();

          /* Clean up the player */
          cleanUp();
        }

        /* Jump over the other cases */
        break;
      }

      /* If the state is STATE_PARSE_COLOR */
      case STATE_PARSE_COLOR:
      {

        /* If the player wants color */
        if( command.toUpperCase().equals( "Y" ) )
        {

          /* Tell the player that colors will be on */
          writeLine( "OK, {BLU}c{GRE}o{yel}l{GRE}o{BLU}r{nor} "
           + "will be turned on." );

          /* Set color to true */
          color = true;
        }

        /* If the player doesn't want that name */
        else
        {

          /* Tell the player that colors will be off */
          writeLine( "OK, color will be turned off." );

          /* Set color to false */
          color = false;
        }

        /* Welcome the player to the game */
        writeLine( "\n\rWelcome to Jam. "
          + "May your visit be adventurous!" );

        /* Save the new player character */
        save();

        /* Print a prompt */
        writePrompt();

        /* Set the state to STATE_PLAYING */
        state = STATE_PLAYING;

        /* Jump over the other cases */
        break;
      }

      /* If the state is STATE_PARSE_RECONNECT */
      case STATE_PARSE_RECONNECT:
      {

        /* Turn echo on */
        connection.echoOn();

        /* If the player wants to reconnect */
        if( command.toUpperCase().equals( "Y" ) )
        {

          /* A reference to the old player character */
          PlayerCharacter oldPlayerCharacter
            = world.getSameNamePlayerCharacter( this );

          /* Set the connection's player charatcer to the old one */
          connection.setPlayerCharacter( oldPlayerCharacter );

          /* Clean the old player character's connection up */
          oldPlayerCharacter.connection.cleanUp();

          /* Set the old player character's connection to the new
             player character's */
          oldPlayerCharacter.connection = connection;

          /* Give a message */
          writeLine( "\n\rOK, reconnecting..." );

          /* Give a prompt */
          writePrompt();
        }

        /* If the player doesn't want to reconnect */
        else
        {

          /* Goodbye */
          writeLine( "\n\rOK, goodbye. Disconnecting..." );

          /* Clean up the player */
          cleanUp();
        }

        /* Jump over the other cases */
        break;
      }

      /* If the state is STATE_PLAYING */
      case STATE_PLAYING:
      {

        /* If the command is not empty */
        if( ! command.equals( "" ) )
        {

          /* If the command has no argument */
          if( command.indexOf( ' ' ) == -1 )
          {

            /* Call the command parser without an argument */
            parseCommand( command.toLowerCase(), "" );
          }

          /* If the command has arguments */
          else
          {

            /* Extract the argument */
            String arguments = command.substring(
              command.indexOf( ' ' ) + 1, command.length() );

            /* Cut the argument off */
            command = command.substring( 0, command.indexOf( ' ' ) );

            /* Call the command parser with the argument */
            parseCommand( command, arguments );
          }
        }

        /* Print a prompt */
        writePrompt();
      }
    }
  }


  /* This function parses and performs a given command beginning with
     A */
  protected final void parseCommandA( String command,
    String arguments )
  {

    /* We don't know this command */
    unknownCommand();
  }


  /* This function parses and performs a given command beginning with
     B */
  protected final void parseCommandB( String command,
    String arguments )
  {

    /* We don't know this command */
    unknownCommand();
  }


  /* This function parses and performs a given command beginning with
     C */
  protected final void parseCommandC( String command,
    String arguments )
  {

    /* We don't know this command */
    unknownCommand();
  }


  /* This function parses and performs a given command beginning with
     D */
  protected final void parseCommandD( String command,
    String arguments )
  {

    /* If the command was delet */
    if( "delet".startsWith( command ) )
    {

      /* Set the action to a new delet command */
      action = new CommandDelet( world, this );
    }

    /* If the command was delete */
    else if( "delete".startsWith( command ) )
    {

      /* Set the action to a new delete command */
      action = new CommandDelete( world, this, arguments );
    }

    /* If none of the commands was matched */
    else
    {

      /* We don't know this command */
      unknownCommand();
    }
  }


  /* This function parses and performs a given command beginning with
     E */
  protected final void parseCommandE( String command,
    String arguments )
  {

    /* We don't know this command */
    unknownCommand();
  }


  /* This function parses and performs a given command beginning with
     F */
  protected final void parseCommandF( String command,
    String arguments )
  {

    /* We don't know this command */
    unknownCommand();
  }


  /* This function parses and performs a given command beginning with
     G */
  protected final void parseCommandG( String command,
    String arguments )
  {

    /* We don't know this command */
    unknownCommand();
  }


  /* This function parses and performs a given command beginning with
     H */
  protected final void parseCommandH( String command,
    String arguments )
  {

    /* We don't know this command */
    unknownCommand();
  }


  /* This function parses and performs a given command beginning with
     I */
  protected final void parseCommandI( String command,
    String arguments )
  {

    /* We don't know this command */
    unknownCommand();
  }


  /* This function parses and performs a given command beginning with
     J */
  protected final void parseCommandJ( String command,
    String arguments )
  {

    /* We don't know this command */
    unknownCommand();
  }


  /* This function parses and performs a given command beginning with
     K */
  protected final void parseCommandK( String command,
    String arguments )
  {

    /* We don't know this command */
    unknownCommand();
  }


  /* This function parses and performs a given command beginning with
     L */
  protected final void parseCommandL( String command,
    String arguments )
  {

    /* We don't know this command */
    unknownCommand();
  }


  /* This function parses and performs a given command beginning with
     M */
  protected final void parseCommandM( String command,
    String arguments )
  {

    /* We don't know this command */
    unknownCommand();
  }


  /* This function parses and performs a given command beginning with
     N */
  protected final void parseCommandN( String command,
    String arguments )
  {

    /* We don't know this command */
    unknownCommand();
  }


  /* This function parses and performs a given command beginning with
     O */
  protected final void parseCommandO( String command,
    String arguments )
  {

    /* We don't know this command */
    unknownCommand();
  }


  /* This function parses and performs a given command beginning with
     P */
  protected final void parseCommandP( String command,
    String arguments )
  {

    /* We don't know this command */
    unknownCommand();
  }


  /* This function parses and performs a given command beginning with
     Q */
  protected final void parseCommandQ( String command,
    String arguments )
  {

    /* If the command was qui */
    if( "qui".startsWith( command ) )
    {

      /* Set the action to a new qui command */
      action = new CommandQui( world, this );
    }

    /* If the command was quit */
    else if( "quit".startsWith( command ) )
    {

      /* Set the action to a new quit command */
      action = new CommandQuit( world, this );
    }

    /* If none of the commands was matched */
    else
    {

      /* We don't know this command */
      unknownCommand();
    }
  }


  /* This function parses and performs a given command beginning with
     R */
  protected final void parseCommandR( String command,
    String arguments )
  {

    /* We don't know this command */
    unknownCommand();
  }


  /* This function parses and performs a given command beginning with
     S */
  protected final void parseCommandS( String command,
    String arguments )
  {

    /* If the command was shutdow */
    if( "shutdow".startsWith( command ) )
    {

      /* Set the action to a new shutdow command */
      action = new CommandShutdow( world, this );
    }

    /* If the command was shutdown */
    else if( "shutdown".startsWith( command ) )
    {

      /* Set the action to a new shutdow command */
      action = new CommandShutdown( world, this );
    }

    /* If the command was save */
    else if( "save".startsWith( command ) )
    {

      /* Set the action to a new save command */
      action = new CommandSave( world, this );
    }

    /* If none of the commands was matched */
    else
    {

      /* We don't know this command */
      unknownCommand();
    }
  }


  /* This function parses and performs a given command beginning with
     T */
  protected final void parseCommandT( String command,
    String arguments )
  {

    /* We don't know this command */
    unknownCommand();
  }


  /* This function parses and performs a given command beginning with
     U */
  protected final void parseCommandU( String command,
    String arguments )
  {

    /* We don't know this command */
    unknownCommand();
  }


  /* This function parses and performs a given command beginning with
     V */
  protected final void parseCommandV( String command,
    String arguments )
  {

    /* We don't know this command */
    unknownCommand();
  }


  /* This function parses and performs a given command beginning with
     W */
  protected final void parseCommandW( String command,
    String arguments )
  {

    /* We don't know this command */
    unknownCommand();
  }


  /* This function parses and performs a given command beginning with
     X */
  protected final void parseCommandX( String command,
    String arguments )
  {

    /* We don't know this command */
    unknownCommand();
  }


  /* This function parses and performs a given command beginning with
     Y */
  protected final void parseCommandY( String command,
    String arguments )
  {

    /* We don't know this command */
    unknownCommand();
  }


  /* This function parses and performs a given command beginning with
     Z */
  protected final void parseCommandZ( String command,
    String arguments )
  {

    /* We don't know this command */
    unknownCommand();
  }


  /* This function parses and performs a given command beginning with
     other characters than letters */
  protected final void parseCommandOther( String command,
    String arguments )
  {

    /* We don't know this command */
    unknownCommand();
  }
}
