/* 
 * File:     JamGUI.java
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
import java.awt.Frame;
import java.awt.TextArea;


/*
 * JamGUI is the controller class for GUI systems
 */
public final class JamGUI extends Frame
{

  /*
   * Private member variables
   */
  private        final ConnectionServer  connectionServer;
  private        final World             world;
  private        final TextArea          textArea;
  private              long              nextTickTime;
  private static final long              PULSE_MILLIS     = 100;


  /*
   * Constructors
   */

  /* This constructor initializes a main window and creates a new
     world and a new connection server which listens on the port
     given as argument */
  public JamGUI( int port )
  {

    /* Create a window titled "Java MUD server" */
    super( "Java MUD server" );

    /* Create a new world */
    world = new World();

    /* Create a new connection server */
    connectionServer = new ConnectionServer( port, world );

    /* Initialize the text area */
    textArea = new TextArea( 10, 40 );

    /* Don't allow editing the text area */
    textArea.setEditable( false );
    textArea.setVisible( true );

    /* Add the text area to the frame */
    add( "Center", textArea );

    /* Make the window visible at a size of 640*480 */
    setVisible( true );
    setSize( 640, 480 );
    
    /* Validate the new window content */
    validate();
  }


  /*
   * Public member functions
   */

  /* This function runs until the world reports a shutdown. In
     a loop the connection server does updates */
  public final synchronized void run()
  {

    /* If there is no connection server */
    if( connectionServer == null )
    {

      /* Immediately return */
      return;
    }

    /* Set the nextTickTime to now + PULSE_MILLIS */
    nextTickTime = System.currentTimeMillis() + PULSE_MILLIS;

    /* Else loop until the world reports the shutdown signal */
    while( ! world.shutDown )
    {

      /* Update the text area */
      textArea.setText( connectionServer.getConnections() );

      /* Update the connection server, does the update fail? */
      if( connectionServer.update() == false )
      {

        /* Shut down the world */
        world.shutDown();
      }

      /* Update the world, does the update fail? */
      if( world.update() == false )
      {

        /* Shut down the world */
        world.shutDown();
      }

      /* Wait for next tick */
      long now = System.currentTimeMillis();

      /* If we have still to wait */
      if( now < nextTickTime )
      {

        /* Wait as long as needed, ignore exceptions */
        try
        {

          /* Wait */
          wait( nextTickTime - now );
        }

        /* Ignore exceptions */
        catch( Exception e )
        {
        }
      }

      /* We don't have to wait */
      else
      {

        /* Give a warning, because the system seems to be quite slow */
        System.out.print( "Warning: System too slow: " );
        System.out.print( now - nextTickTime );
        System.out.println( " ms." );
      }

      /* Set next tick time */
      nextTickTime += PULSE_MILLIS;
    }

    /* Clean up the connection server */
    connectionServer.cleanUp();

    /* Release all window ressources */
    dispose();
  }
}
