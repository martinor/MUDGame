/* 
 * File:     Actor.java
 * Author:   Jan-Peter v. Hunnius
 *           s_jhunni@gmx.de
 * Created:  13th of October, 2000.
 * Last mod: 21st of October, 2000.
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
import java.lang.Character;
import java.util.Vector;


/*
 * Actor is the parent class for livings (such as players and mobiles),
 * items and actions
 */
public class Actor
{

  /*
   * Protected member variables
   */
  protected final World  world;
  protected final Vector commandList;

  protected       short  busyPulses = 0;

  protected       String name        = "";
  protected       short  level       = 1;

  /*
   * Constructors
   */

  /* This constructor initializes the actor */
  public Actor( World world, String name )
  {

    /* Initialize the world reference */
    this.world = world;

    /* Initialize the name */
    this.name = name;

    /* Initialize the command list */
    commandList = new Vector();

    /* Register this actor within the world's actor list */
    world.addActor( this );
  }


  /*
   * Public member functions
   */

  /* This function returns the actor's name */
  public String getName()
  {

    /* Return the actor's name */
    return( name );
  }


  /* This function returns the actor's level */
  public short getLevel()
  {

    /* Return the actor's level */
    return( level );
  }


  /* This function returns the actor's busy pulses */
  public short getBusyPulses()
  {

    /* Return the actor's busy pulses */
    return( busyPulses );
  }


  /* This function decreases the actor's busy pulses */
  public void decreaseBusyPulses()
  {

    /* Decrease the busy pulses by one */
    busyPulses--;
  }


  /* This function lets the actor act if he wants to */
  public void act()
  {

    /* If there are commands in the list */
    if( ! commandList.isEmpty() )
    {

      /* Get the first command from the list */
      String command = ( String ) commandList.firstElement();

      /* Delete the first command from the list */
      commandList.removeElementAt( 0 );

      /* Perform the command */
      performCommand( command );
    }
  }


  /* This function lets the actor perform a given command */
  protected void performCommand( String command )
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


  /* This function parses and performs a given command */
  protected final void parseCommand( String command,
    String arguments )
  {

    /* Dependend on the first character of the command switch the
       command parsers */
    switch( Character.toLowerCase( command.charAt( 0 ) ) )
    {

      /* If the first character is a */
      case 'a':
      {

        /* Call the parser for all commands beginning with a */
        parseCommandA( command, arguments );

        /* Jump over all the other letters */
        break;
      }

      /* If the first character is b */
      case 'b':
      {

        /* Call the parser for all commands beginning with b */
        parseCommandB( command, arguments );

        /* Jump over all the other letters */
        break;
      }

      /* If the first character is c */
      case 'c':
      {

        /* Call the parser for all commands beginning with c */
        parseCommandC( command, arguments );

        /* Jump over all the other letters */
        break;
      }

      /* If the first character is d */
      case 'd':
      {

        /* Call the parser for all commands beginning with d */
        parseCommandD( command, arguments );

        /* Jump over all the other letters */
        break;
      }

      /* If the first character is e */
      case 'e':
      {

        /* Call the parser for all commands beginning with e */
        parseCommandE( command, arguments );

        /* Jump over all the other letters */
        break;
      }

      /* If the first character is f */
      case 'f':
      {

        /* Call the parser for all commands beginning with f */
        parseCommandF( command, arguments );

        /* Jump over all the other letters */
        break;
      }

      /* If the first character is g */
      case 'g':
      {

        /* Call the parser for all commands beginning with g */
        parseCommandG( command, arguments );

        /* Jump over all the other letters */
        break;
      }

      /* If the first character is h */
      case 'h':
      {

        /* Call the parser for all commands beginning with h */
        parseCommandH( command, arguments );

        /* Jump over all the other letters */
        break;
      }

      /* If the first character is i */
      case 'i':
      {

        /* Call the parser for all commands beginning with i */
        parseCommandI( command, arguments );

        /* Jump over all the other letters */
        break;
      }

      /* If the first character is j */
      case 'j':
      {

        /* Call the parser for all commands beginning with j */
        parseCommandJ( command, arguments );

        /* Jump over all the other letters */
        break;
      }

      /* If the first character is k */
      case 'k':
      {

        /* Call the parser for all commands beginning with k */
        parseCommandK( command, arguments );

        /* Jump over all the other letters */
        break;
      }

      /* If the first character is l */
      case 'l':
      {

        /* Call the parser for all commands beginning with l */
        parseCommandL( command, arguments );

        /* Jump over all the other letters */
        break;
      }

      /* If the first character is m */
      case 'm':
      {

        /* Call the parser for all commands beginning with m */
        parseCommandM( command, arguments );

        /* Jump over all the other letters */
        break;
      }

      /* If the first character is n */
      case 'n':
      {

        /* Call the parser for all commands beginning with n */
        parseCommandN( command, arguments );

        /* Jump over all the other letters */
        break;
      }

      /* If the first character is o */
      case 'o':
      {

        /* Call the parser for all commands beginning with o */
        parseCommandO( command, arguments );

        /* Jump over all the other letters */
        break;
      }

      /* If the first character is p */
      case 'p':
      {

        /* Call the parser for all commands beginning with p */
        parseCommandP( command, arguments );

        /* Jump over all the other letters */
        break;
      }

      /* If the first character is q */
      case 'q':
      {

        /* Call the parser for all commands beginning with q */
        parseCommandQ( command, arguments );

        /* Jump over all the other letters */
        break;
      }

      /* If the first character is r */
      case 'r':
      {

        /* Call the parser for all commands beginning with r */
        parseCommandR( command, arguments );

        /* Jump over all the other letters */
        break;
      }

      /* If the first character is s */
      case 's':
      {

        /* Call the parser for all commands beginning with s */
        parseCommandS( command, arguments );

        /* Jump over all the other letters */
        break;
      }

      /* If the first character is t */
      case 't':
      {

        /* Call the parser for all commands beginning with t */
        parseCommandT( command, arguments );

        /* Jump over all the other letters */
        break;
      }

      /* If the first character is u */
      case 'u':
      {

        /* Call the parser for all commands beginning with u */
        parseCommandU( command, arguments );

        /* Jump over all the other letters */
        break;
      }

      /* If the first character is v */
      case 'v':
      {

        /* Call the parser for all commands beginning with v */
        parseCommandV( command, arguments );

        /* Jump over all the other letters */
        break;
      }

      /* If the first character is w */
      case 'w':
      {

        /* Call the parser for all commands beginning with w */
        parseCommandW( command, arguments );

        /* Jump over all the other letters */
        break;
      }

      /* If the first character is x */
      case 'x':
      {

        /* Call the parser for all commands beginning with x */
        parseCommandX( command, arguments );

        /* Jump over all the other letters */
        break;
      }

      /* If the first character is y */
      case 'y':
      {

        /* Call the parser for all commands beginning with y */
        parseCommandY( command, arguments );

        /* Jump over all the other letters */
        break;
      }

      /* If the first character is z */
      case 'z':
      {

        /* Call the parser for all commands beginning with z */
        parseCommandZ( command, arguments );

        /* Jump over all the other letters */
        break;
      }

      /* In other cases */
      default:
      {

        /* Call the parser for all commands beginning with other
           characters than letters */
        parseCommandOther( command, arguments );
      } 
    }
  }


  /* This function parses and performs a given command beginning with
     A */
  protected void parseCommandA( String command, String arguments )
  {
  }


  /* This function parses and performs a given command beginning with
     B */
  protected void parseCommandB( String command, String arguments )
  {
  }


  /* This function parses and performs a given command beginning with
     C */
  protected void parseCommandC( String command, String arguments )
  {
  }


  /* This function parses and performs a given command beginning with
     D */
  protected void parseCommandD( String command, String arguments )
  {
  }


  /* This function parses and performs a given command beginning with
     E */
  protected void parseCommandE( String command, String arguments )
  {
  }


  /* This function parses and performs a given command beginning with
     F */
  protected void parseCommandF( String command, String arguments )
  {
  }


  /* This function parses and performs a given command beginning with
     G */
  protected void parseCommandG( String command, String arguments )
  {
  }


  /* This function parses and performs a given command beginning with
     H */
  protected void parseCommandH( String command, String arguments )
  {
  }


  /* This function parses and performs a given command beginning with
     I */
  protected void parseCommandI( String command, String arguments )
  {
  }


  /* This function parses and performs a given command beginning with
     J */
  protected void parseCommandJ( String command, String arguments )
  {
  }


  /* This function parses and performs a given command beginning with
     K */
  protected void parseCommandK( String command, String arguments )
  {
  }


  /* This function parses and performs a given command beginning with
     L */
  protected void parseCommandL( String command, String arguments )
  {
  }


  /* This function parses and performs a given command beginning with
     M */
  protected void parseCommandM( String command, String arguments )
  {
  }


  /* This function parses and performs a given command beginning with
     N */
  protected void parseCommandN( String command, String arguments )
  {
  }


  /* This function parses and performs a given command beginning with
     O */
  protected void parseCommandO( String command, String arguments )
  {
  }


  /* This function parses and performs a given command beginning with
     P */
  protected void parseCommandP( String command, String arguments )
  {
  }


  /* This function parses and performs a given command beginning with
     Q */
  protected void parseCommandQ( String command, String arguments )
  {
  }


  /* This function parses and performs a given command beginning with
     R */
  protected void parseCommandR( String command, String arguments )
  {
  }


  /* This function parses and performs a given command beginning with
     S */
  protected void parseCommandS( String command, String arguments )
  {
  }


  /* This function parses and performs a given command beginning with
     T */
  protected void parseCommandT( String command, String arguments )
  {
  }


  /* This function parses and performs a given command beginning with
     U */
  protected void parseCommandU( String command, String arguments )
  {
  }


  /* This function parses and performs a given command beginning with
     V */
  protected void parseCommandV( String command, String arguments )
  {
  }


  /* This function parses and performs a given command beginning with
     W */
  protected void parseCommandW( String command, String arguments )
  {
  }


  /* This function parses and performs a given command beginning with
     X */
  protected void parseCommandX( String command, String arguments )
  {
  }


  /* This function parses and performs a given command beginning with
     Y */
  protected void parseCommandY( String command, String arguments )
  {
  }


  /* This function parses and performs a given command beginning with
     Z */
  protected void parseCommandZ( String command, String arguments )
  {
  }


  /* This function parses and performs a given command beginning with
     other characters than letters */
  protected void parseCommandOther( String command, String arguments )
  {
  }
}
