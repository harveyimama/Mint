
$(document).ready(function() {

	$("#spinner").hide();
	$("#alert").hide();
	$("#result").hide();
	$("#spinner2").hide();
	$("#alert2").hide();
	$("#result2").hide();

	$("#textbox").keyup(function() {
		var bin = $("#textbox"). val();
		if (bin.length > 5)
			{
			$("#spinner").show();
			
			$.get( "http://localhost:8080/card-scheme/verify/".concat(bin), function( data ) {
				 
				  if(data.success == true)
					  {
					  $("#result").show();
					  $("#type span").text(data.payload.type);
					  $("#scheme span").text(data.payload.scheme);
					  $("#bank span").text(data.payload.bank);
					  }	  
				  else
					  {
					  $("#alert").show("slow");
					  $("#result").hide();
					  }
					  
				  
				  $("#spinner").hide();
				});
			
			
			}

	});
	
	
	$("#search").click(function() {
		$("#spinner2").hide();
		$("#alert2").hide();
		$("#result2").hide();
		$("#panseries span").text('');
		$("#count span").text('');
		 
		var start = $("#start"). val();
		var limit = $("#limit"). val();
		if(start != '' && limit != '')
			{
			$("#spinner2").show();
			
			$.get( "http://localhost:8080/card-scheme/stats?start=".concat(start).concat("&limit=").concat(limit), function( data ) {
				 
				  if(data.success == true)
					  {
					  $("#result2").show();
					  var payload =  data.payload
					  console.log(payload)
					  for (var key in payload) {
						    if (payload.hasOwnProperty(key)) {
						        for (var key2 in payload[key]) {
						        	 //console.log(key2 + " -> " + payload[key][key2]);
						        	 $("#panseries span").append(key2);
						        	 $("#panseries span").append('<br/>');
									 $("#count span").append(payload[key][key2]);
									 $("#count span").append('<br/>');
						        }
						    }
						}
					 
					
					  }	  
				  else
					  {
					  $("#alert2").text('No Records found')
					  $("#alert2").show('slow')
					  $("#result2").hide();
					  }
					  
				  
				  $("#spinner2").hide();
				});
			}
		else
			{
			$("#alert2").text('All feilds are required')
			  $("#alert2").show('slow')
			}

	});
});