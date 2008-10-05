
	getFirstAndLastDateOfMonth = function(cal){
		var firstAndLastDate = new Array();
		firstAndLastDate.length = 2;
		for (i=0;i<cal.cellDates.length;i++){
			firstAndLastDate[0] = cal.getDateByCellId('cal1_cell' + i);
			if(!cal.isDateOOM(firstAndLastDate[0])){
				break;
			}
		}
		for (i=cal.cellDates.length-1;i>=0;i--){
			firstAndLastDate[1] = cal.getDateByCellId('cal1_cell' + i);
			if(!cal.isDateOOM(firstAndLastDate[1])){
				break;
			}
		}
		return firstAndLastDate;
	}
	
	addLeadingNull = function(value){
		if(value < 10){
			return "0"+value;
		}
		else {
			return value;
		}
	}
	
	formatDate = function(date){
		return addLeadingNull(date.getDate()+1) + '.' + addLeadingNull(date.getMonth()+1) + '.' + date.getFullYear();
	}
