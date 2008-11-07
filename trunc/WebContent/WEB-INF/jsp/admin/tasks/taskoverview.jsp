<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<script type="text/javascript">
	var myDataTable;
	var myLogReader;
	var myDataTableCallback;

	var jobCommandCallback = {
		success: function(o) {YAHOO.log("Job started"); },
	  	failure: function(o) {YAHOO.log("Failure during job start!", "error"); },
	  	cache:false
	} 

	function startJob(){
		YAHOO.util.Connect.asyncRequest('GET', 'jobCommand.htm?jobId=' + this.id, jobCommandCallback, null);
	}
		

	var jobActionFormat = function(elCell, oRecord, oColumn, oData) { 
		if(oRecord.getData().isActive){
			elCell.innerHTML = 'Job ist active';
		}
		else{
			elCell.innerHTML = "<img id=" + oData + " src='/SimpleInquest/img/right.gif' class='XHRRequest'/>";
			YAHOO.util.Event.addListener(oData, "click", startJob); 
		}
	};

	var myDataSource = new YAHOO.util.DataSource("JSONState.htm");
    myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSON;
    myDataSource.connXhrMode = "queueRequests";
    myDataSource.responseSchema = {
        resultsList: "tasks",
        fields: [
        	{key:"name"},
        	{key:"isActive"},
        	{key:"nextExecutionDate"}, 
        	{key:"id"}
        ]
    };

    function init(){
    	initLogger();
    	initJobsTable();
    };
	
	function initJobsTable() {
        var myColumnDefs = [
			{key:"name",label:"Job",sortable:false},
            {key:"isActive",label:"Aktiv",sortable:false},
            {key:"nextExecutionDate",label:"Next Run",sortable:false},                 
            {key:"id", label:"", formatter: jobActionFormat}
        ];

        myDataTable = new YAHOO.widget.DataTable("json", myColumnDefs,
                myDataSource, {initialRequest:""});

        this.myDataTable.subscribe("buttonClickEvent", function(oArgs){
            var oRecord = this.getRecord(oArgs.target);
            alert(YAHOO.lang.dump(oRecord.getData()));
        });

        var myDataTableCallback = {             
        	success : myDataTable.onDataReturnReplaceRows, 
            failure : function(o) {YAHOO.log("Failure during job table update", "error"); }, 
            scope : myDataTable 
        };

        myDataSource.setInterval  ( 1000 , myDataTable.get("initialRequest") , myDataTableCallback , null ) 
    };

    function initLogger() {
    	var logReaderConfig = { 
    		width: "20px", 
    		height: "30em", 
    		newestOnTop: true, 
    		footerEnabled: false 
    	}; 
    	myLogReader = new YAHOO.widget.LogReader("log", logReaderConfig);
    }; 
	 
	YAHOO.util.Event.onDOMReady(init);
 </script>
<h2>Admin Bereich - Jobs</h2>
<div id="json"></div>