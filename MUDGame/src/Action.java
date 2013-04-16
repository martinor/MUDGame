/* 
 * File:     Action.java
 * Author:   Jan-Peter v. Hunnius
 *           s_jhunni@gmx.de
 * Created:  20th of October, 2000.
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
 * Action is responsible for carrying out an action started by an
 * actor. This can be a spell, an affect or a general command.
 */
public class Action extends Actor
{

  /*
   * Private member variables
   */
  protected Actor actor;


  /*
   * Constructors
   */

  /* This constructor initializes the action */
  public Action( World world, Actor actor, String name )
  {

    /* Call the actor constructor */
    super( world, name );

    /* Initialize the actor */
    this.actor = actor;
  }

  /*
   * Public member functions
   */

  /* This function interrupts the action. It simply ends the action */
  public void interrupt()
  {

    /* End the action */
    end();
  }


  /* This function ends the action. It purges the action's
     registration within the world */
  public void end()
  {

    /* Purge the regsistration within the world */
    world.removeActor( this );
  }
}
