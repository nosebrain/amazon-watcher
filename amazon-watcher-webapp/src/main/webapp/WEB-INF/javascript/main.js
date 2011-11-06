var TIME_FORMAT = "%0d.%0m.%y";
$(function() {
	var now = new Date().getTime();
	$('#items>li').each(function() {
		var item = $(this);
		var histogram = item.find('.histogram');
		
		var historyDataSource = item.find('ul.history');
		var historyData = getHistoryData(historyDataSource, now);
		
		$.plot(histogram, [ { 
			data: historyData, 
			color: '#2220F9',
			shadowSize: 0,
			hoverable: true
			} ], {
			xaxis: {
				mode: "time",
		        timeformat: TIME_FORMAT
			}, 
			yaxis: {
				tickDecimals: 2,
				tickFormatter: currencyFormatter
			},
			series: {
                lines: {
                	show: true
                },
                points: {
                	show: true,
                	fill: true,
                	fillColor: '#2220F9'
                }
            },
            grid: {
            	hoverable: true
            }
		});
		
		histogram.bind("plothover", function(event, pos, item) {
		   if (item) {
		      if (previousPoint != item.dataIndex) {
		          previousPoint = item.dataIndex;
		          
		          // alert("hover");
		          $("#tooltip").remove();
		          // TODO: why +1d
		          var x = $.plot.formatDate(new Date(item.datapoint[0]), TIME_FORMAT, []);
		          var y = item.datapoint[1].toFixed(2);
		                    
		          showTooltip(item.pageX, item.pageY, x + "<br />" + y + '€'); // TODO: 
		      }
		   } else {
		      $("#tooltip").remove();
		      previousPoint = null;            
		   }
		});
		
		histogram.hide();
		
		item.find('a.showHistory').click(function() {
			histogram.toggle();
			return false;
		});
	});
	
});

function getHistoryData(historyDataUl, now) {
	var data = [];
	$(historyDataUl).find('li').each(function() {
		var price = $(this).data('price');
		var date = new Date($(this).data('date') + 24 * 60 * 60 * 1000);
		data.push({value: price, date: date});
	});
	
	var historyData = aggregateByDate(data);
	
	var plotData = [];
	for (var i = 0; i < historyData.length; i++) {
		var hData = historyData[i];
		plotData.push([hData.date.getTime(), hData.max]);
	}
	
	var last = data[data.length - 1];
	plotData.push([now, last.value]);
	
	return plotData;
}

function showTooltip(x, y, contents) {
    $('<div id="tooltip">' + contents + '</div>').css( {
        position: 'absolute',
        display: 'none',
        'font-size' : '80%',
        'text-align': 'center',
        'font-weight' : 'bold',
        top: y + 10,
        left: x + 10,
        padding: '5px',
        'background-color': '#000',
        'color' : '#fff',
        opacity: 0.75
    }).appendTo("body").fadeIn(200);
}

function currencyFormatter(v, axis) {
    return v.toFixed(axis.tickDecimals) + "€"; // TODO: use config
}

// source http://stackoverflow.com/questions/3228837/histogram-in-flot-javascript
function aggregateByDate(source) {
    var aggregateHash = {};
    var dates = {};

    for	(var i = 0; i < source.length; i++) {
    	var item = source[i];
    	var year = item.date.getFullYear();
    	var month = item.date.getMonth();
    	var dateM = item.date.getDate();
        var compareString = year + '-' + (month + 1) + '-' + dateM;
        var date = new Date(year, month, dateM);
        dates[compareString] = date;
        
        if (!(compareString in aggregateHash)) {
           aggregateHash[compareString] = [];
        }

        aggregateHash[compareString].push(item);
    }

    var newSource = [];
    for (var key in aggregateHash) {
       var sum = 0;
       var max = Number.MIN_VALUE;

       for (var i = 0; i < aggregateHash[key].length; i++) {
    	  var value = aggregateHash[key][i].value;
          sum += value;
          max = Math.max(max, value);
       }

       newSource.push({
          total: sum,
          max: max,
          count: aggregateHash[key].length,
          items: aggregateHash[key],
          dateString: key,
          date: dates[key]
       });
    }

    return newSource;
}