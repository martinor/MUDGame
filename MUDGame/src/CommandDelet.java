/* 
 * File:     CommandDelet.java
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
 * CommandDelet just gives a message to the actor
 */
public class CommandDelet extends Action
{

  /*
   * Constructors
   */

  /* This constructor initializes the command delet */
  public CommandDelet( World world,
    PlayerCharacter playerCharacter )
  {

    /* Call the actor constructor */
    super( world, playerCharacter, "delet" );

    /* Give a message */
    ( ( PlayerCharacter )actor ).writeLine(
      "You have to write the whole word delete followed by your\n"
      + "password to delete your character." );

    /* End the command delet */
    end();
  }
}
