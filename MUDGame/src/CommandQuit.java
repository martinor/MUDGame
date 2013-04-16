/* 
 * File:     CommandQuit.java
 * Author:   Jan-Peter v. Hunnius
 *           s_jhunni@gmx.de
 * Created:  21st of October, 2000.
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
 * CommandQuit just gives a message to the actor
 */
public class CommandQuit extends Action
{

  /*
   * Constructors
   */

  /* This constructor initializes the command quit */
  public CommandQuit( World world,
    PlayerCharacter playerCharacter )
  {

    /* Call the actor constructor */
    super( world, playerCharacter, "quit" );

    /* Save the player character, if it succeeds */
    if( ( ( PlayerCharacter ) actor ).save() == true )
    {

      /* Give a message */
      ( ( PlayerCharacter ) actor ).writeLine(
        "OK, character saved, quitting. Goodbye." );

      /* Clean up the player character */
      ( ( PlayerCharacter ) actor ).cleanUp();
    }

    /* If it fails */
    else
    {

      /* Give a message */
      ( ( PlayerCharacter ) actor ).writeLine(
        "Saving your character failed, not quitting." );
    }

    /* End the command quit */
    end();
  }
}
