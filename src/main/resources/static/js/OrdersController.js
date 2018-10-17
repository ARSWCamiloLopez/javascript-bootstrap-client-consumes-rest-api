var order1 = {
	"order_id": 1,
	"table_id": 1,
	"products": [{
			"product": "Pizza",
			"quantity": 3,
			"price": "$10000"
		},
		{
			"product": "Hot dog",
			"quantity": 1,
			"price": "$3000"
		},
		{
			"product": "Coke",
			"quantity": 4,
			"price": "$1300"
		}
	]
}

var order2 = {
	"order_id": 2,
	"table_id": 2,
	"products": [{
			"product": "Pizza",
			"quantity": 3,
			"price": "$15000"
		},
		{
			"product": "Hamburguer",
			"quantity": 1,
			"price": "$12300"
		}
	]
}

var order3 = {
	"order_id": 3,
	"table_id": 3,
	"products": [{
			"product": "Pizza",
			"quantity": 5,
			"price": "$25000"
		},
		{
			"product": "Hamburguer",
			"quantity": 2,
			"price": "$24600"
		},
		{
			"product": "Soda",
			"quantity": 3,
			"price": "$4600"
		}
	]
}

function loadOrders(){
	addTableToPage(order1);
	addTableToPage(order2);
	addTableToPage(order3);
}

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

function tryingAxios(){
	axios("https://localhost:8080/orders");
}
