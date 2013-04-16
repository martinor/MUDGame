# File:     Makefile
# Author:   Jan-Peter v. Hunnius
#           s_jhunni@gmx.de
# Created:  12th of October, 2000.
# Last mod: 16th of October, 2000.
#
# This program is free software; you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation; either version 2 of the License, or
# (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with this program; if not, write to the Free Software
# Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
#
# This target deletes all java compiled class files and recompiles all
# java files
jam:	
	rm -f *.class
	javac -O Jam.java


# This target deletes all editor backup files and all java compiled
# class files
clean:	
	rm -f *.class *.bak *~

# This target is the standard. It performs target jam (see above)
all:	jam
