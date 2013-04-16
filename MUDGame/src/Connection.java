/* 
 * File:     Connection.java
 * Author:   Jan-Peter v. Hunnius
 *           s_jhunni@gmx.de
 * Created:  15th of August, 2000.
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
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.net.Socket;


/*
 * Connection handles one internet connection with
 * input and output
 */
public final class Connection
{


  /*
   * Private member variables
   */
  private final ConnectionServer connectionServer;
  private       PlayerCharacter  playerCharacter;
  private final Socket           socket;

  private final BufferedReader   in;
  private final BufferedWriter   out;

  private       String           inputLine = "";

  private final char ECHO_OFF[] = { 255, 251, 1 };
  private final char ECHO_ON[]  = { 255, 252, 1 };


  /*
   * Constructors
   */

  /* This constructor takes the socket to communicate on
     and a reference to the connection server as arguments.
     It initializes the connection, creates I/O buffers and
     gives a welcome message to the socket */
  public Connection( Socket socket,
    ConnectionServer connectionServer,
    World world ) throws IOException
  {

    /* Set the connection server and socket to interact with */
    this.connectionServer = connectionServer;
    this.socket           = socket;

    /* Set socket options to TCPNoDelay and NoLinger */
    socket.setTcpNoDelay( true );
    socket.setSoLinger( false, 0 );

    /* Create an input and an output buffer */
    in = new BufferedReader( new InputStreamReader(
      socket.getInputStream() ) );
    out = new BufferedWriter( new OutputStreamWriter(
      socket.getOutputStream() ) );

    /* Register the connection within the connection server */
    connectionServer.addConnection( this );

    /* Create a new player character connected with this connection */
    playerCharacter = new PlayerCharacter( this, world, "" );
  }


  /*
   * Public member functions
   */

  /* This function reads a line from the socket if available.
     If the socket is closed, the connection will be cleaned
     up. */
  public final String readLine()
  {

    /* A char to read into */
    char character;

    /* Try to read a line if input is available */
    try
    {

        /* While there is any input */
        while( in.ready() )
        {

          /* Read a character */
          character = ( char ) in.read();

          /* If the read character is 255 (a control sequence is
             following) */
          if( character == 255 )
          {

            /* Skip the next three characters (the control
               sequence) */
            in.skip( 2 );
          }

          /* If the read character is acceptable */
          else
          {
            /* Append the character to the inputLine */
            inputLine += character;
          }
        }

        /* If the line is complete */
        if( inputLine.indexOf( '\n' ) != -1 )
        {

          /* Clone the line to be able to empty it */
          String returnLine = inputLine.substring(
            0, inputLine.indexOf( '\n' ) );

          /* Empty the input line */
          inputLine = inputLine.substring(
            inputLine.indexOf( '\n' ) + 1, inputLine.length() );

          /* Return the (cloned) input line */
          return( returnLine );
        }

        /* Line is incomplete */
        else
        {

          /* Return an empty line */
          return( "" );
        }
    }

    /* If an I/O expception occurs */
    catch( IOException ioe )
    {

      /* Clean up the connection */
      cleanUp();

      /* Return an empty line */
      return( "" );
    }
  }


  /* This function writes text to the socket and flushes the
     output buffer */
  public final void write( String text )
  {

    /* Try to do the output */
    try
    {

      /* Write the text to the output buffer */
      out.write( text, 0, text.length() );

      out.flush();
    }

    /* On I/O exceptions */
    catch( IOException IOE )
    {

      /* Clean up this connection */
      cleanUp();
    }
  }


  /* This function writes a line of text to the socket and
     flushes the output buffer */
  public final void writeLine( String line )
  {

    /* Write the line to the output buffer and append \n\r */
    write( line + "\n\r" );
  }


  /* This function turns the echo of the connection off */
  public final void echoOff()
  {

    /* Try to do the output */
    try
    {

      /* Write the echo_off string to the connection */
      out.write( ECHO_OFF, 0, 3 );

      out.flush();
    }

    /* On I/O exceptions */
    catch( IOException IOE )
    {

      /* Clean up this connection */
      cleanUp();
    }
  }


  /* This function turns the echo of the connection on */
  public final void echoOn()
  {

    /* Try to do the output */
    try
    {

      /* Write the echo_on string to the connection */
      out.write( ECHO_ON, 0, 3 );

      out.flush();
    }

    /* On I/O exceptions */
    catch( IOException IOE )
    {

      /* Clean up this connection */
      cleanUp();
    }
  }


  /* This function sets the player character reference */
  public final void setPlayerCharacter(
    PlayerCharacter playerCharacter )
  {
    this.playerCharacter = playerCharacter;
  }


  /* This function cleans up the connection. It closes the I/O
     buffers and the socket and finally removes itself from
     the connections server's list of all connections */
  public final void cleanUp()
  {

    /* Try to close the I/O buffers and the socket */
    try
    {

      /* Close buffers */
      in.close();
      out.close();

      /* Close the socket */
      socket.close();
    }

    /* Do nothing if an exception occurs */
    catch( IOException ioe )
    {
    }

    /* Remove this connection from the connection server's
       connection list */
    connectionServer.remove( this );
  }


  /* This function returns a description string for the
     connection */
  public final String getDescription()
  {

    /* Return the socket host and port */
    return( socket.getInetAddress().getHostName() + " ( "
      + socket.getInetAddress().getHostAddress() + " ) :"
      + socket.getPort() + "\n" );
  }
}
