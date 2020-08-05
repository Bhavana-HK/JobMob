function addEduField( table ){

	var tableRef = document.getElementById(table);
	var newRow   = tableRef.insertRow(-1);
	var newCell  = newRow.insertCell(0);
	var newElem = document.createElement( 'input' );
		newElem.setAttribute("name", "name");
		newElem.setAttribute("type", "text");
		newElem.setAttribute("placeholder", "Name of Institute");
		newCell.appendChild(newElem);

		newCell = newRow.insertCell(1);
		newElem = document.createElement( 'input' );
		newElem.setAttribute("name", "keywords");
		newElem.setAttribute("type", "text");
		newElem.setAttribute("placeholder", "Course");
		newCell.appendChild(newElem);

		newCell = newRow.insertCell(2);
		newElem = document.createElement( 'input' );
		newElem.setAttribute("name", "start");
		newElem.setAttribute("type", "date");
		newElem.setAttribute("placeholder", "Enter Start Date");
		newCell.appendChild(newElem);
		
		newCell = newRow.insertCell(3);
		newElem = document.createElement( 'input' );
		newElem.setAttribute("name", "end");
		newElem.setAttribute("type", "date");
		newElem.setAttribute("placeholder", "Enter End Date");
		newCell.appendChild(newElem);

		newCell = newRow.insertCell(4);
		newElem = document.createElement( 'input' );
		newElem.setAttribute("name", "percent");
		newElem.setAttribute("type", "text");
		newElem.setAttribute("placeholder", "percentage acquired");
		newCell.appendChild(newElem);
		
		newCell = newRow.insertCell(5);
		newElem = document.createElement( 'input' );
		newElem.setAttribute("type", "button");
		newElem.setAttribute("value", "Delete Row");
		newElem.setAttribute("onclick", 'removeEduLine(this)')
		newCell.appendChild(newElem);
	}

	function removeEduLine(lineItem) {
  		var row = lineItem.parentNode.parentNode;
  		row.parentNode.removeChild(row);
	}
	
	function addWorkField( table ){

		var tableRef = document.getElementById(table);
		var newRow   = tableRef.insertRow(-1);

		var newCell  = newRow.insertCell(0);
		var newElem = document.createElement( 'input' );
			newElem.setAttribute("name", "title");
			newElem.setAttribute("type", "text");
			newElem.setAttribute("placeholder", "Job Title");
			newCell.appendChild(newElem);

			newCell = newRow.insertCell(1);
			newElem = document.createElement( 'input' );
			newElem.setAttribute("name", "name");
			newElem.setAttribute("type", "text");
			newElem.setAttribute("placeholder", "Company Name");
			newCell.appendChild(newElem);

			newCell = newRow.insertCell(2);
			newElem = document.createElement( 'input' );
			newElem.setAttribute("name", "start");
			newElem.setAttribute("type", "date");
			newElem.setAttribute("placeholder", "Enter Start Date");
			newCell.appendChild(newElem);
			
			newCell = newRow.insertCell(3);
			newElem = document.createElement( 'input' );
			newElem.setAttribute("name", "end");
			newElem.setAttribute("type", "date");
			newElem.setAttribute("placeholder", "Enter End Date");
			newCell.appendChild(newElem);

			newCell = newRow.insertCell(4);
			newElem = document.createElement( 'input' );
			newElem.setAttribute("type", "button");
			newElem.setAttribute("value", "Delete Row");
			newElem.setAttribute("onclick", 'removeWorkLine(this)')
			newCell.appendChild(newElem);
		}

		function removeWorkLine(lineItem) {
	  		var row = lineItem.parentNode.parentNode;
	  		row.parentNode.removeChild(row);
		}
	
	function openTab(evt, tabName) {
	    // Declare all variables
	    var i, tabcontent, tablinks;

	    // Get all elements with class="tabcontent" and hide them
	    tabcontent = document.getElementsByClassName("tabcontent");
	    for (i = 0; i < tabcontent.length; i++) {
	        tabcontent[i].style.display = "none";
	    }

	    // Get all elements with class="tablinks" and remove the class "active"
	    tablinks = document.getElementsByClassName("tablinks");
	    for (i = 0; i < tablinks.length; i++) {
	        tablinks[i].className = tablinks[i].className.replace(" active", "");
	    }

	    // Show the current tab, and add an "active" class to the button that opened the tab
	    document.getElementById(tabName).style.display = "block";
	    evt.currentTarget.className += " active";
	}
	