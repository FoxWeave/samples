$(document).ready(function() {

    var bookTable = $('#bookTable table');
    var customerTable = $('#customerTable table');
    var shipNowBtn = $('#shipNowBtn');

    var books = [
        {title: "Doctor Sleep (Shining Book 2)", author: "Stephen King"},
        {title: "Gino's Italian Escape",         author: "Gino D'Acampo"},
        {title: "AQA Biology AS: Student's Book", author: "Glenn Toole"},
        {title: "Scala Cookbook: Recipes for Object-Oriented and Functional Programming ", author: "Alvin Alexander"}
    ];

    function populateTableBody(table, tableData, colNames, uniqueAttrib) {
        var tbody = $('tbody', table);

        tbody.empty();
        $.each(tableData, function(i, rowData) {
            var row = $('<tr>', {'class': 'rowData'});

            if (uniqueAttrib) {
                row.attr('rowId', rowData[uniqueAttrib]);
            }
            row.data('rowData', rowData);

            tbody.append(row);
            $.each(colNames, function(ii, colName) {
                var cell = $('<td>');
                var cellValue = rowData[colName];

                row.append(cell);
                if (cellValue) {
                    cell.append(cellValue);
                } else {
                    cell.append('&nbsp;');
                }
            });
        });

        $('tr.rowData', tbody).click(function() {
            $('tr.rowData', tbody).removeClass('active');
            $(this).addClass('active');
            toggleShipNowBtn();
        });

        var blankRow = $('<tr>');
        table.append(blankRow);
        $.each(colNames, function() {
            blankRow.append($('<td>&nbsp;</td>'));
        });
    }

    function populateCustomerTable(resetTimer) {
        function setTimer() {
            if (resetTimer) {
                setTimeout(function() {
                    populateCustomerTable(true);
                }, 5000);
            }
        }

        customerTable.activeRowId = $('tr.active', customerTable).attr('rowId');
        $.ajax({
            url: 'api/customer',
            success: function(restResponse) {
                populateTableBody(customerTable, restResponse.data, ['firstName', 'lastName', 'email'], 'id');
                if (customerTable.activeRowId) {
                    $('tr[rowId=' + customerTable.activeRowId + ']', customerTable).addClass('active');
                }
                setTimer();
            },
            error: function() {
                setTimer();
            }
        });
    }

    function getTableSelection(table) {
        var selectedRow = $('tr.rowData.active', table);
        if (selectedRow.size() === 1) {
            return selectedRow.data('rowData');
        } else {
            return undefined;
        }
    }

    function toggleShipNowBtn() {
        shipNowBtn.attr('disabled', 'disabled');
        shipNowBtn.removeClass('btn-primary');

        // Enable the button if there's a book and customer selected...
        if (getTableSelection(bookTable) && getTableSelection(customerTable)) {
            shipNowBtn.removeAttr('disabled');
            shipNowBtn.addClass('btn-primary');
        }
    }
    shipNowBtn.click(function() {
        var selectedBook = getTableSelection(bookTable);
        var selectedCustomer = getTableSelection(customerTable);

        if (selectedBook && selectedCustomer) {
            $('tr.rowData').removeClass('active');
            toggleShipNowBtn();

            var shipmentAPIPayload = {
                productName: selectedBook.title + ' (by ' + selectedBook.author + ')',
                customer: selectedCustomer
            };

            $.ajax({
                type: 'POST',
                url: 'api/shipment',
                dataType: 'json',
                contentType: 'application/json',
                data: JSON.stringify(shipmentAPIPayload),
                success: function(r) {
                    alert('Shipment succeeded.');
                },
                error: function(r) {
                    alert('Shipment failed.');
                }
            });
        }
    });

    toggleShipNowBtn();
    populateTableBody(bookTable, books, ['title', 'author']);
    populateCustomerTable(true);

    $('#customerTableRefresh').click(function() {
        populateCustomerTable();
    });

});