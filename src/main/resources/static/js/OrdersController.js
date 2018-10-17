var firstRow = ["Product", "Quantity", "Price"];

function addTableToPage(order){

    var main = document.getElementById("main");
    var table = document.createElement("table");

    table.setAttribute("class", "table");
    table.setAttribute("id", order.order_id);

    row = document.createElement("tr");

    for(var x in firstRow){
        var rowItem = document.createElement("th");
        rowItem.innerHTML = firstRow[x];
        row.appendChild(rowItem);
    }

    table.appendChild(row);

    for(var key in order.products){
        var row = document.createElement("tr");
        for(var element in order.products[key]){
                var rowItem = document.createElement("td");
                rowItem.innerHTML = order.products[key][element];
                row.appendChild(rowItem);
        }
        table.appendChild(row);
    }
    main.appendChild(table);
}

function removeOrderById(idOrder){
	document.getElementById(idOrder).remove();
}

function loadOrders(){
    var ordenes = [];
    axios.get('/orders')
        .then(function(response){
            orders = response.data;
            for(var i in orders){
                var order = {	"order_id": 0,
                                "table_id": 0,
                                "products": []};
                order.order_id = Number(i);
                for(var j in orders[i]){
                    if(j == "tableNumber"){
                        order.table_id = orders[i][j];                        
                    }else{
                        for(var k in orders[i][j]){
                            var infoTable = {};
                            infoTable["product"] = k;
                            infoTable["quantity"] = orders[i][j][k];
                            infoTable["price"] = "$10000";
                            order["products"].push(infoTable);
                        }                        
                    }
                }
                ordenes.push(order);
            }
            for(var x in ordenes){
                addTableToPage(ordenes[x]);
            }
        })
        .catch(function (error) {
            console.log("There is a problem with our servers. We apologize for the inconvince, please try again later" + error);
        });
}
