var TIME_FORMAT = "%0d.%0m.%y";
var PRICE_COLOR = '#2A5BA1';
var COLOR_RED = '#cc0000';
var COLOR_GREEN = '#00cc00';

$(function() {	
	var now = new Date().getTime();
	$('.item').each(function() {
		var item = $(this);
		var histogram = item.find('.histogram');
		var currency = item.data('currency');
		var historyDataSource = item.find('ul.history');
		var historyData = getHistoryData(historyDataSource, now);
		var dataToPlot = new Array();
		
		if (item.data('mode') == "PRICE_LIMIT") {
			var limit = item.data('limit');
			var firstDate = historyData[0][0];
			var lastPrice = historyData[historyData.length - 1][1];
			
			var colorLimit = COLOR_RED;
			
			if (lastPrice < limit) {
				colorLimit = COLOR_GREEN;
			}
			
			dataToPlot.push({
				data: [[firstDate, limit], [now, limit]],
				color: colorLimit,
				hoverable: false,
				shadowSize: 0
			});
		}
		
		dataToPlot.push({ 
			data: historyData, 
			color: PRICE_COLOR,
			shadowSize: 0,
			hoverable: true,
			points: {
				show: false,
            	fill: true,
            	fillColor: PRICE_COLOR
			}
		});
		
		$.plot(histogram, dataToPlot, {
			xaxis: {
				mode: "time",
		        timeformat: TIME_FORMAT,
		        alignTicksWithAxis: 1
			}, 
			yaxis: {
				tickDecimals: 2,
				tickFormatter: currencyFormatter
			},
			series: {
                lines: {
                	show: true
                }
            },
            grid: {
            	hoverable: true
            }
		});
		
		function currencyFormatter(v, axis) {
		    return v.toFixed(axis.tickDecimals) + currency;
		}
		
		histogram.bind("plothover", function(event, pos, item) {
		   if (item) {
		      if (previousPoint != item.dataIndex) {
		          previousPoint = item.dataIndex;
		          
		          // alert("hover");
		          $("#tooltip").remove();
		          // TODO: why +1d
		          var x = $.plot.formatDate(new Date(item.datapoint[0]), TIME_FORMAT, []);
		          var y = item.datapoint[1].toFixed(2);
		                    
		          showTooltip(item.pageX, item.pageY, x + "<br />" + y + currency);
		      }
		   } else {
		      $("#tooltip").remove();
		      previousPoint = null;            
		   }
		});
		
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
		plotData.push([hData.date.getTime(), hData.min]);
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
       var min = Number.MAX_VALUE;

       for (var i = 0; i < aggregateHash[key].length; i++) {
    	  var value = aggregateHash[key][i].value;
          sum += value;
          max = Math.max(max, value);
          min = Math.min(min, value);
       }

       newSource.push({
          total: sum,
          max: max,
          min: min,
          count: aggregateHash[key].length,
          items: aggregateHash[key],
          dateString: key,
          date: dates[key]
       });
    }

    return newSource;
}


// edit form functions

$(function() {
	modeChange();
	$('#mode').change(modeChange);
});

function modeChange() {
	var limit = $('#limit').parent();
	if ($('#mode').val() == "PRICE_LIMIT") {
		limit.show();
	} else {
		limit.hide();
	}
}