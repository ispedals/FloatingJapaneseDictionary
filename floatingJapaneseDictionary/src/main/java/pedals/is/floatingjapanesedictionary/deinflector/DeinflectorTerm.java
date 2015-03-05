/*
 *  Copyright 2013 Balloonguy
 *
 *  Based on Rikaichan 2.0.7
 *
	---

	Rikaichan
	Copyright (C) 2005-2012 Jonathan Zarate
	http://www.polarcloud.com/

	---

	Originally based on RikaiXUL 0.4 by Todd Rudick
	http://www.rikai.com/
	http://rikaixul.mozdev.org/

	---
 *  This file is part of FloatingJapaneseDictionary.

    FloatingJapaneseDictionary is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 2 of the License, or
    (at your option) any later version.

    FloatingJapaneseDictionary is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with FloatingJapaneseDictionary.  If not, see <http://www.gnu.org/licenses/>.
 */
package pedals.is.floatingjapanesedictionary.deinflector;

public class DeinflectorTerm {

	public String word;
	public int type;
	public String reason;

	public DeinflectorTerm() {

	}

	public DeinflectorTerm(String w, int t, String r) {

		word = w;
		type = t;
		reason = r;
	}

}
