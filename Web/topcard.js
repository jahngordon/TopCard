/*--------------------------------------------------------------------------
 *  Top Card Game Javascript Library
 *--------------------------------------------------------------------------*/

/**
 * Player Class
 */
Player = Class.create();
Player.prototype =  {
	initialize: function(name) {
		this.name = name;
		this.domElement = $(document.createElement("li"));
		this.domElement.update(name);
		this.cards = new Array();
		this.lastcard = null;
	},
	
	getDomElement: function() {
		return this.domElement;
	},
	
	dealCard: function(card) {
		this.cards.push(card);
		this.updateUi();
	},
	
	playCard: function() {
		var card = this.cards.shift();
		card.setLastOwner(this);
		this.updateUi();
		this.lastcard = card;
		return card;
	},
	
	winCards: function(cards) {
		while (cards.length > 0) {
			this.cards.push(cards.pop());
		}
		this.updateUi();
	},
	
	updateUi: function() {
		this.domElement.update(this.name + " (" + this.cards.length + ")");
	}
};


/**
 * Aircraft Class
 */
Aircraft = Class.create();
Aircraft.prototype =  {
	initialize: function(manufacturer, model, range, wingspan, length, height, mtow, maxspeed, year, imagefile) {
		this.manufacturer = manufacturer;
		this.model = model;
		this.range = range;
		this.wingspan = wingspan;
		this.length = length;
		this.height = height;
		this.maxspeed = maxspeed;
		this.mtow = mtow;
		this.year = year;
		this.imagefile = imagefile;
		this.lastOwner = null;
		this.domElement = this.createTableView();
	},
	
	/**
	 * Get the DOM element for this aircraft card
	 * @returns
	 */
	getCard: function() {
		return this.domElement;
	},
	
	/**
	 * Create the DOM element (and return) for this card
	 */
	createTableView: function() {
		var domEl = $(document.createElement("table"));
		domEl.setAttribute('class', 'topcard');
		var tbody = $(document.createElement("tbody"));
		domEl.appendChild(tbody);

		// Title box first
		var tr = $(document.createElement("tr"));
		tbody.appendChild(tr);
		
		var td = $(document.createElement("th"));
		td.setAttribute('class', 'title');
		td.setAttribute('colspan', '2');
		td.update(this.manufacturer + ' ' + this.model);
		tr.appendChild(td);
		
		// Then the image
		tr = $(document.createElement("tr"));
		tbody.appendChild(tr);
		
		td = $(document.createElement("td"));
		td.setAttribute('class', 'picture');
		td.setAttribute('colspan', '2');
		
		var img = $(document.createElement("img"));
		img.setAttribute('src', 'images/' + this.imagefile);
		td.appendChild(img);
		
		tr.appendChild(td);
		
		// Then the data
		tbody.appendChild(this.createTableRow("Range", this.range + "km"));
		tbody.appendChild(this.createTableRow("Wingspan", this.wingspan + "m"));
		tbody.appendChild(this.createTableRow("Length", this.length + "m"));
		tbody.appendChild(this.createTableRow("Height", this.height + "m"));
		tbody.appendChild(this.createTableRow("Max Speed", this.maxspeed + "kph"));
		tbody.appendChild(this.createTableRow("Max Takeoff Weight", this.mtow + "t"));
		tbody.appendChild(this.createTableRow("First Flight", this.year));
		
		return domEl;
	},
	
	createTableRow: function(heading, value) {
		var tr = $(document.createElement("tr"));
		
		var cell = $(document.createElement("th"));
		cell.update(heading);
		tr.appendChild(cell);
		
		cell = $(document.createElement("td"));
		cell.update(value);
		tr.appendChild(cell);

		return tr;
	},
	
	/**
	 * Store the last owner of this card
	 * @param player
	 */
	setLastOwner: function(player) {
		this.lastOwner = player;
	},
	
	/**
	 * Gets the last owner of this card
	 * @returns The last owner of the card
	 */
	getLastOwner: function() {
		return this.lastOwner;
	}
};


/**
 * Game Code
 */
topcard = {
	players: new Array(),
	rolloverCards: new Array(),
	gameInPlay: false,
	cards: null,
	rolloverCards: null,
	currentCards: null,
	nextPlayer: null,
	
	init: function() {
		// For now, just draw the cards to the screen
		/*for (key in topcard.cards) {
			var thing = topcard.cards[key];
			if (typeof(thing) == 'object') {
				$('playspace').appendChild(thing.getCard());
			}
		}*/
		
	},
	
	createCards: function() {
		topcard.cards = new Array();
		topcard.cards.push(new Aircraft("Airbus", "A380", 15700,79.75,72.72,24.09,560,1020,2005,"a380.jpg"));
		topcard.cards.push(new Aircraft("Boeing", "747-800", 14800,68.5,76.3,19.4,448,917,2010,"b747.jpg"));
		topcard.cards.push(new Aircraft("Aerospatiale-BAC", "Concorde", 7223,25.6,61.66,12.2,187,2179,1969,"concorde.jpg"));
		topcard.cards.push(new Aircraft("Lockheed", "L1011 Tristar", 7410,47.35,54.17,16.87,200,1164,1970,"tristar.jpg"));
		topcard.cards.push(new Aircraft("McDonnell Douglas", "DC10", 6116,47.34,51.97,17.7,195,982,1970,"dc10.jpg"));
		topcard.cards.push(new Aircraft("British Aerospace", "146-200", 2909,26.21,28.6,8.59,42,894,1981,"bae146.jpg"));
		topcard.cards.push(new Aircraft("Boeing", "737-400", 4204,28.9,36.5,11.1,68,943,1988,"b737.jpg"));
		topcard.cards.push(new Aircraft("Airbus", "A320", 6100,35.8,37.57,11.76,78,871,1987,"a320.jpg"));
		topcard.cards.push(new Aircraft("Bombardier", "Dash8 Q400", 2522,28.4,32.81,8.3,29,667,1998,"dash8.jpg"));
		topcard.cards.push(new Aircraft("Antonov", "An-225", 15400,88.4,84,18.1,640,850,1988,"an225.jpg"));
		topcard.cards.push(new Aircraft("Antonov", "An-124", 5200,73.3,68.96,20.78,405,865,1982,"an124.jpg"));
		topcard.cards.push(new Aircraft("Ilushin", "Il-96-300", 7500,60.1,55.3,17.6,216,910,1988,"il96.jpg"));
		topcard.cards.push(new Aircraft("Tupolev", "Tu-144", 2500,28.8,65.5,12.5,207,2120,1968,"tu144.jpg"));
		topcard.cards.push(new Aircraft("Cessna", "172", 1289,11,8.28,2.72,1,302,1955,"cessna.jpg"));
		topcard.cards.push(new Aircraft("Embraer", "ERJ145", 2873,20.04,29.87,6.76,24,851,1995,"erj145.jpg"));
		topcard.cards.push(new Aircraft("ATR", "42-320", 1950,24.6,22.7,7.6,17,500,1984,"atr42.jpg"));
		topcard.cards.push(new Aircraft("Fokker", "100", 3100,28.1,35.5,8.5,46,856,1986,"fokker100.jpg"));
		topcard.cards.push(new Aircraft("Airbus", "A340-500", 16400,63.5,67.9,17.1,372,913,2002,"a340.jpg"));
		topcard.cards.push(new Aircraft("Boeing", "777-300ER", 14600,64.8,73.9,18.6,352,945,1997,"b777.jpg"));
		topcard.cards.push(new Aircraft("Dornier", "328Jet", 1700,20.1,21.3,7,16,750,1998,"dornier.jpg"));
	},
	
	addPlayer: function() {
		var newName = prompt("Please enter your name");
		var player = new Player(newName);
		topcard.players.push(player);
		$('userlist').down('ul').appendChild(player.getDomElement());
		if (topcard.players.length > 1) {
			$('startgame').show();
		}
	},
	
	startGame: function() {
		$('addplayer').hide();
		$('startgame').hide();

		topcard.nextPlayer = topcard.players[0];
		
		topcard.rolloverCards = new Array();
		topcard.createCards();
		
		// Shuffle the cards (don't want the same game every time...)
		topcard.cards = topcard.shuffle(topcard.cards);
		
		while (topcard.cards.length > 0) {
			for (var i = 0; i < topcard.players.length && topcard.cards.length > 0; i++) {
				var currentCard = topcard.cards.shift();
				topcard.players[i].dealCard(currentCard);
			}
		}
		
		topcard.playCards();
	},
	
	playCards: function() {
		$('allcards').hide();
		$('nextround').hide();
		$('cardsplayed').hide();

		// Grab cards from each user...
		topcard.currentCards = new Array();
		
		for (var i = 0; i < topcard.players.length; i++) {
			topcard.currentCards.push(topcard.players[i].playCard());
		}
		
		console.log(topcard.nextPlayer);

		// Display user to play, their card, and the menu to select which property to play
		$('playertoplay').update(topcard.nextPlayer.name);
		$('playspace').update("");
		$('playspace').appendChild(topcard.nextPlayer.lastcard.getCard());
		
		$('playerprompt').show();
	},
	
	playWith: function(choice) {
		var sorter;
		switch(choice) {
		case 'r':
			sorter = topcard.dynamicSort("range");
			break;
		case 'w':
			sorter = topcard.dynamicSort("wingspan");
			break;
		case 'l':
			sorter = topcard.dynamicSort("length");
			break;
		case 'h':
			sorter = topcard.dynamicSort("height");
			break;
		case 'm':
			sorter = topcard.dynamicSort("mtow");
			break;
		case 's':
			sorter = topcard.dynamicSort("maxspeed");
			break;
		case 'f':
			sorter = topcard.dynamicSort("year");
			break;
		}
		
		topcard.currentCards.sort(sorter);
		
		$('allcards').update("");
		for (var i = 0; i < topcard.currentCards.length; i++) {
			$('allcards').appendChild(topcard.currentCards[i].getCard());
		}
		$('allcards').show();
		$('cardsplayed').show();
		
		if (sorter(topcard.currentCards[0], topcard.currentCards[1]) == 0) {
			// Draw
			for (var i = 0; i < topcard.currentCards.length; i++) {
				topcard.rolloverCards.push(topcard.currentCards.length.shift());
			}
		} else {
			// Clear winner
			topcard.nextPlayer = topcard.currentCards[0].getLastOwner();
			
			topcard.nextPlayer.winCards(topcard.currentCards);
			
			if (topcard.rolloverCards.length > 0) {
				topcard.currentCards[0].getLastOwner().winCards(topcard.rolloverCards);
			}
		}
		
		// Remove any players with no cards from game
		for (var i = topcard.players.length - 1; i >= 0; i--) {
			if (topcard.players[i].cards.length == 0) {
				alert(topcard.players[i].name + " is out of the game!");
				topcard.players.splice(i, 1);
			}
		}
		
		if (topcard.players.length > 1) {
			$('nextround').show();
		} else {
			alert(topcard.players[0].name + " wins!!");
		}
	},
	
	dynamicSort: function(property) {
	    var sortOrder = 1;
	    if(property[0] === "-") {
	        sortOrder = -1;
	        property = property.substr(1);
	    }
	    return function (a,b) {
	        var result = (a[property] > b[property]) ? -1 : (a[property] < b[property]) ? 1 : 0;
	        return result * sortOrder;
	    };
	},
	
	shuffle: function(array) {
		var currentIndex = array.length, temporaryValue, randomIndex ;

		// While there remain elements to shuffle...
		while (0 !== currentIndex) {
			// Pick a remaining element...
			randomIndex = Math.floor(Math.random() * currentIndex);
			currentIndex -= 1;
			
			// And swap it with the current element.
			temporaryValue = array[currentIndex];
			array[currentIndex] = array[randomIndex];
			array[randomIndex] = temporaryValue;
		}
		
		return array;
	}
};

document.observe('dom:loaded', function() {
	topcard.init();
});