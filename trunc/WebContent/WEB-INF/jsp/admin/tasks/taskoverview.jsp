<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<script type="text/javascript">
	var myDataTable;
	var myDataTableCallback;

	var jobActionFormat = function(elCell, oRecord, oColumn, oData) { 
		elCell.innerHTML = '<a href="./editAppointment.htm?id=' + oData + '">Edit</a>'; 
	};

	var myDataSource = new YAHOO.util.DataSource("JSONState.htm");
    myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
    myDataSource.connXhrMode = "queueRequests";
    myDataSource.responseSchema = {
        resultsList: "tasks",
        fields: ["name","isActive","nextExecutionDate"]
    };
	
	function initJobsTable() {
        var myColumnDefs = [
			{key:"name",label:"Job",sortable:true},
            {key:"isActive",label:"Aktiv",sortable:true},
            {key:"nextExecutionDate",label:"Next Run",sortable:true},                 
            {key:"action", formatter: jobActionFormat}
        ];

        myDataTable = new YAHOO.widget.DataTable("json", myColumnDefs,
                myDataSource, {initialRequest:""});

        myDataTableCallback = {
            	success : this.myDataTable.onDataReturnReplaceRows,
            	failure : this.myDataTable.onDataReturnReplaceRows,
            	scope : this.myDataTable
        };

        myDataSource.setInterval  ( 1000 , myDataTable.get("initialRequest") , myDataTableCallback , null ) 
    };
	 
	YAHOO.util.Event.onDOMReady(initJobsTable);
 </script>
<h2>Admin Bereich - Jobs</h2>
<div id="json"></div>