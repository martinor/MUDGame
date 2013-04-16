/* 
 * File:     Jam.java
 * Author:   Jan-Peter v. Hunnius
 *           s_jhunni@gmx.de
 * Created:  14th of August, 2000.
 * Last mod: 13th of October, 2000.
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
import java.lang.InternalError;


/*
 * Jam is the main application class
 */
public final class Jam
{

  /*
   * Private class variables
   */
  private static final int DEFAULT_PORT = 4711;


  /*
   * Public member functions
   */

  /* The main function parses given command line arguments and
     starts either the JamGUI or the JamConsole */
  public static void main( String[] args )
  {

    /* Set the default port */
    int port = DEFAULT_PORT;

    /* Try to parse the argument, give an error message and exit
       on failure */
    try
    {

      /* Are there any arguments? Try to parse the first then */
      if( args.length != 0 )
        port = Integer.parseInt( args[0] );
    }
    
    /* If the first argument is no Integer */
    catch( NumberFormatException nfe )
    {

      /* Report the usage to standard error */
      System.err.println( "Usage: java Jam [port]" );
      System.err.println( "Where options include:" );
      System.err.println( "\tport the port on which to listen." );

      /* Exit */
      System.exit( 0 );
    }
    
    /* Try to start the GUI, start the console on failure
       (non-GUI systems) */
    try
    {

      /* Start the GUI */
      JamGUI gUI = new JamGUI( port );
      gUI.run();
    }

    /* On internal errors start the console */
    catch( InternalError ie )
    {

      /* Start the console */
      JamConsole console = new JamConsole( port );
      console.run();
    }

    /* Exit the application finally */
    System.exit( 0 );
  }
}
