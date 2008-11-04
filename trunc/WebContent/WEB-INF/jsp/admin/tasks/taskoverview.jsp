<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<script type="text/javascript">
	var myDataTable;
	var myDataSource;
	var callback1;

	var jobActionFormat = function(elCell, oRecord, oColumn, oData) { 
		elCell.innerHTML = '<a href="./editAppointment.htm?id=' + oData + '">Edit</a>'; 
	};
	
	function updateJobsTable() {
        var myColumnDefs = [
			{key:"name",label:"Job",sortable:true},
            {key:"isActive",label:"Aktiv",sortable:true},
            {key:"nextExecutionDate",label:"Next Run",sortable:true},                 
            {key:"action", formatter: jobActionFormat}
        ];

        myDataSource = new YAHOO.util.DataSource("JSONState.htm");
        myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
        myDataSource.connXhrMode = "queueRequests";
        myDataSource.responseSchema = {
            resultsList: "tasks",
            fields: ["name","isActive","nextExecutionDate"]
        };

        myDataTable = new YAHOO.widget.DataTable("json", myColumnDefs,
                myDataSource, {initialRequest:""});                

		callback1 = {
            	success : this.myDataTable.onDataReturnAppendRows,
            	failure : this.myDataTable.onDataReturnAppendRows,
            	scope : this.myDataTable
        };
    };
	 
	YAHOO.util.Event.onDOMReady(updateJobsTable);
 </script>

<div id="json"></div>