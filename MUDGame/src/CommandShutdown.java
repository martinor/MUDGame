/* 
 * File:     CommandShutdown.java
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
 * CommandShutdown shuts down JAM, if the level of the actor is high
 * enough
 */
public class CommandShutdown extends Action
{

  /*
   * Constructors
   */

  /* This constructor initializes the command shutdown */
  public CommandShutdown( World world,
    PlayerCharacter playerCharacter )
  {

    /* Call the actor constructor */
    super( world, playerCharacter, "shutdown" );

    /* If the player character's level is >= 20 */
    if( ( ( PlayerCharacter )actor ).getLevel() >= 20 )
    {

      /* Give a message */
      ( ( PlayerCharacter ) actor ).writeLine( "Shutting down JAM." );

      /* Shut down the game */
      world.shutDown();
    }

    /* If the player character's level is < 20 */
    else
    {

      /* This command is not accesible */
      ( ( PlayerCharacter ) actor ).unknownCommand();
    }

    /* End the command shutdown */
    end();
  }
}
