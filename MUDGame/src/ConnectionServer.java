/* 
 * File:     ConnectionServer.java
 * Author:   Jan-Peter v. Hunnius
 *           s_jhunni@gmx.de
 * Created:  17th of August, 2000.
 * Last mod: 14th of October, 2000.
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
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.ServerSocket;
import java.util.Vector;


/*
 * ConnectionServer handles incoming internet connections
 * and triggers all connection's I/O
 */
public final class ConnectionServer
{

  /*
   * Private member variables
   */
  private final Vector       connectionList;
  private       ServerSocket serverSocket;
  private final World        world;


  /*
   * Constructors
   */

  /* This constructor takes the port to listen on as argument.
     It initializes a server socket and creates a list of
     all connections */
  public ConnectionServer( int port, World world )
  {

    /* Set the reference to the world */
    this.world = world;

    /* Try to initialize a server socket */
    try
    {

      /* Create the server socket listening on the given port */
      serverSocket = new ServerSocket( port );
    }

    /* Trace the stack on I/O exception */
    catch( IOException ioe )
    {
      ioe.printStackTrace();
    }
    
    /* Create a vector for all connections */
    connectionList = new Vector();
  }


  /*
   * Public member functions
   */

  /* This function accepts a new pending connectiuon request
     if available; the world will be shut down on errors */
  public final boolean update()
  {

    /* Try to accept a pending connection request */
    try
    {

      /* Interrupt accept after 1 ms */
      serverSocket.setSoTimeout( 1 );

      /* Add a new created connection with the accepted socket
         to the connection list */
      new Connection( serverSocket.accept(), this, world );
    }

    /* Do nothing when timed out */
    catch( InterruptedIOException iioe )
    {
    }

    /* Trace the stack and return false (reports failure) on
       other I/O exceptions */
    catch( IOException ioe )
    {

      /* Trace the stack */
      ioe.printStackTrace();

      /* Return false */
      return( false );
    }
    
    /* Return true (reports success) if everything went good */
    return( true );
  }


  /* This function cleans up the connection server. It closes the
     server socket, cleans up all connections and empties the
     connection list. */
  public final void cleanUp()
  {

    /* Try to close the server socket */
    try
    {
      serverSocket.close();
    }

    /* Trace the stack on I/O exceptions */
    catch( IOException ioe )
    {
      ioe.printStackTrace();
    }

    /* Run through all connections and clean them up */
    for( int index = 0;
      index != connectionList.size();
      index++ )
    {

      /* Get a connection */
      Connection connection =
        ( Connection ) connectionList.elementAt( index );

      /* If it is still a connection */
      if( connection != null )
      {

        /* Clean it up */
        connection.cleanUp();
      }
    }
      
    /* Clear the connection list */
    connectionList.removeAllElements();
  }


  /* This function adds a connection to the connection list */
  public final void addConnection( Connection connection )
  {

    /* If the connection is already in the list */
    if( connectionList.contains( connection ) )
    {

      /* Immediately return without adding the connection */
      return;
    }

    /* Run though all connections and find an empty slot */
    for( int index = 0;
      index < connectionList.size();
      index++ )
    {

      /* Is the slot empty? */
      if( connectionList.elementAt( index ) == null )
      {

        /* Add the connection here and stop*/
        connectionList.setElementAt( connection, index );
        break;
      }
    }

    /* Was the connection not inserted yet? */
    if( ! connectionList.contains( connection ) )     

      /* Add the connection to the list */
      connectionList.addElement( connection );
  }


  /* This function removes a connection from the connection
     list */
  public final void remove( Connection connection )
  {

    /* If the connection is in the list */
    if( connectionList.contains( connection ) )
    {

      /* Set the position where the connection is located within
         the list to null (marks an empty slot) */
      connectionList.setElementAt( null,
        connectionList.indexOf( connection ) );
    }
  }


  /* This function returns a string showing all active internet
     connections of the connection server */
  public final String getConnections()
  {

    /* The string containing all connection descriptions at the
       end */
    String connectionDescriptions = "";

    /* Run though all connections and find non empty slots */
    for( int index = 0;
      index < connectionList.size();
      index++ )
    {

      /* Is the slot not empty? */
      if( connectionList.elementAt( index ) != null )
      {

        /* Get the connection at index */
        Connection connection = 
          ( Connection ) connectionList.elementAt( index );
          
        /* Add the connection description to the connection
           descriptions */
        connectionDescriptions += connection.getDescription();
      }
    }

  /* Return the description string */
  return( connectionDescriptions );
  }
}
