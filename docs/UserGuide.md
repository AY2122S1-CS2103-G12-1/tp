---
layout: page
title: User Guide
---

Sellah is a desktop application optimized for online individual sellers who prefer CLI over GUI. It is an address book
that contains the contact information and order details related to clients and partners.

* Table of Contents
    * [Quick Start](#quick-start)
    * [Features](#features)
        * [Help](#help-help)
        * [Exit](#exit-exit)
        * [Add](#adding-add)
        * [Delete](#deleting-delete)
        * [Edit](#editing-edit)
        * [Find](#finding-find)
        * [List](#listing-list)
        * [View](#viewing-view)
        * [Command History](#command-history)
        * [Statistics (Coming Soon)](#statistics-coming-soon)
        * [Load & Save Data (Coming Soon)](#loading-and-saving-the-data-coming-soon)
    * [FAQ](#faq)
    * [Command Summary](#command-summary)

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `11` or above installed in your Computer.

1. Download the latest `sellah.jar` from [here](https://github.com/AY2122S1-CS2103T-T12-1/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your Sellah.

1. Double-click the file to start the app. The GUI similar to the below should appear in a few seconds. Note how the app
   contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add NAME`, `NAME` is a parameter which can be used as `add Ben`.

* Items in square brackets are optional.<br>
  e.g `NAME [-e EMAIL]` can be used as `Ben -e ben@gmail.com` or as `Ben`.

* Items with `...` after them can be used multiple times including zero times. e.g. `[-o ORDER]...` can be used as ` ` (
  i.e. 0 times), `-o 0 1 10/21`, `-o 0 1 2021/10/21 -o 1 5 10/20`, etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `-pn PHONE_NUMBER -e EMAIL`, `-e EMAIL -pn PHONE_NUMBER` is also acceptable.

* If a parameter is expected only once in the command but you specified it multiple times, only the last occurrence of
  the parameter will be taken.<br>
  e.g. if you specify `-pn 12341234 -pn 56785678`, only `-pn 56785678` will be taken.

* Extraneous parameters for commands that do not take in parameters (such as `exit`) will be ignored.<br>
  e.g. if the command specifies `exit 123`, it will be interpreted as `exit`.

</div>

### Help: `help`

Displays help message to the user.

Format: `help`

Expected Output: ![Ui](images/Ui.png)

### Exit: `exit`

Exits the program.

Format: `exit`

### Adding: `add`

Adds a new client or product to the application with an automatically generated ID.

Format:

* Add a client: `add -c NAME -pn PHONE_NUMBER [-e EMAIL] [-a ADDRESS] [-o ORDER]...`
* Add a product: `add -p NAME -$ UNIT_PRICE [-q QUANTITY]`

Notes:

* Adds a client with name, phone number and optional email, address and orders.
* Adds a product with name, unit price and optional quantity.
* The user will be informed if the format of a field is incorrect, e.g. input `ten dollars` for the field `price`.
* The user will be informed if the client/product to be added already exits.

Examples:

* `add -c Ben -pn 98765432` adds a new `client` `Ben`, whose `phone number` is `98765432`.
* `add -p pen -$ 10.0 -q 150` adds a new `product` `pen` with a `unit price` of `$10.0` and there are `150`
  pens in stock.

### Deleting: `delete`

Deletes the specified client/product from the tracker.

Format:

* Delete a client: `delete -c INDEX`
* Delete a product: `delete -p INDEX`

Notes:

* Deletes the client/product based on the client/product’s INDEX.
* The INDEX refers to the index shown in the displayed client/product list.
* If the product/client doesn't exist, then we inform the user that such a product/client doesn't exist.

Examples:

* `delete -c 1` deletes the client with index 1 in the tracker.
* `delete -p 2` deletes the product with index 2 in the tracker.

### Editing: `edit`

Edits an existing client or product in the application.

Format:

* Edit a client: `edit -c INDEX [-n NAME] [-pn PHONE_NUMBER] [-e EMAIL] [-a ADDRESS]`
* Edit a product: `edit -p INDEX [-n NAME] [-$ UNIT_PRICE] [-q QUANTITY]`

Notes:

* Edits the client/product at the specified `INDEX`.
    * The index refers to the index number shown in the displayed client/product list by
      [`list`](#listing-list) or [`view`](#viewing-view) commands.
    * The index **must be a positive integer** 1, 2, 3, ...
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* User will be informed if the client/product of the give `INDEX` does not exist.

Examples:

* `edit -c 1 -n Ben` Edits the name of the client with `INDEX` of `1` to `Ben`.
* `edit -p 3 -n Ben10 -q 20` Edits the name of the product with `INDEX` of `3` to `Ben10` and the quantity to `20`.

### Finding: `find`

Finds a client/product that is in the application

Format:

* Find clients: `find -c NAME`
* Find products: `find -p NAME`

Notes:

* Finds the client/product based on the `NAME` provided.
* `NAME` provided must fully match the `NAME` in the application and can be case-insensitive.
* User will be informed if there are no matching `NAME` in the application.

Examples:

* `find -c john` Shows a list of all clients with the `NAME` `john` in the application.
* `find -c phone` Shows a list of all products with the `NAME` `phone` in the application.

### Listing: `list`

Shows a list of all clients/products in the application

Format:

* List clients: `list -c`
* List products: `list -p`

Notes:

* User will be informed if there are no clients/products in the application.

Examples:

* `list -c` Shows a list of all clients in the application.
* `list -p` Shows a list of all products in the application.

### Viewing: `view`

Views a current client/product from the application.

Format:

* View a client: `view -c INDEX`
* View a product: `view -p INDEX`

Notes:

* If the product/client doesn't exist, then we inform the user that such a product/client doesn't exist.

Examples:

* `view -c 20` Views all the details of the client with `INDEX` of `20` including id, name, phone number, email and
  address.
* `view -p 5` Views all the details of the product with `INDEX` of `5` including id, name, unit price and quantity.

### Command History:

Allows the user to navigate to previous commands using `↑` and `↓` keys.

Format:

* Previous command: `↑`
* Next command: `↓`

Notes:

* Pressing `↑` when the first command is currently displayed will do nothing.
* Pressing `↓` when the last command is currently displayed will clear the command input field.
    * Subsequent `↓` will do nothing.

### Statistics [coming soon]

_Details coming soon ..._

### Loading and Saving the data [coming soon]

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: ...<br>
**A**: ...<br>

**Q**: ...<br>
**A**: ...<br>

--------------------------------------------------------------------------------------------------------------------

## Command Summary

<table>
    <tr>
        <th>Action</th>
        <th>Format, Examples</th>
    </tr>
    <tr>
        <td>
            <a href="#adding-add">Add</a>
        </td>
        <td>
            Add a client: <code>add -c NAME -pn PHONE_NUMBER [-e EMAIL] [-a ADDRESS] [-o ORDER]...</code><br>
            e.g., <code>add -c James Tan -pn 98765432 -e james.email@example.com -a 123, Clementi Rd</code><br>
            <br>
            Add a product: <code>add -p NAME -$ UNIT_PRICE [-q QUANTITY]</code><br>
            e.g., <code>add -p pen -$ 10.0 -q 120</code>
        </td>
    </tr>
    <tr>
        <td>
            <a href="#editing-edit">Edit</a>
        </td>
        <td>
            Edit a client: <code>edit -c INDEX [-n NAME] [-pn PHONE_NUMBER] [-e EMAIL] [-a ADDRESS]</code><br>
            e.g., <code>edit -c 1 -n Ben</code> Edits the name of the client with <code>INDEX</code> of <code>1</code> to
            <code>Ben</code>.<br>
            <br>
            Edit a product: <code>edit -p INDEX [-n NAME] [-$ UNIT_PRICE] [-q QUANTITY]</code><br>
            e.g., <code>edit -p 3 -n Ben10 -q 20</code> Edits the name of the product with <code>INDEX</code> of
            <code>3</code> to <code>Ben10</code> and the quantity to <code>20</code>.
        </td>
    </tr>
    <tr>
        <td>
            <a href="#viewing-view">View</a>
        </td>
        <td>
            View a client: <code>view -c INDEX</code><br>
            e.g., <code>view -c 20</code> Views all the details of the client with <code>INDEX</code> of <code>20</code>
            including id, name, phone number, email and address.<br>
            <br>
            View a product: <code>view -p INDEX</code><br>
            e.g., <code>view -p 5</code> Views all the details of the product with <code>INDEX</code> of
            <code>5</code> including id, name, unit price and quantity.
        </td>
    </tr>
    <tr>
        <td>
            <a href="#deleting-delete">Delete</a>
        </td>
        <td>
            Delete a client: <code>delete -c INDEX</code><br>
            e.g., <code>delete -c 20</code> Deletes all the details of the client with <code>INDEX</code> of
            <code>20</code> including id, name, phone number, email and address.<br>
            <br>
            Delete a product: <code>delete -p INDEX</code><br>
            e.g., <code>delete -p 5</code> Deletes all the details of the product with <code>INDEX</code> of
            <code>5</code> including id, name, unit price and quantity.
            <br>
        </td>
    </tr>
    <tr>
        <td>
            <a href="#finding-find">Find</a>
        </td>
        <td>
            Find clients: <code>find -c NAME</code><br>
            e.g., <code>find -c john</code> Shows a list of all clients with the <code>NAME</code> <code>john</code> in the application.<br>
            <br>
            Find products: <code>find -p NAME</code> <br>
            e.g. <code>find -c phone</code> Shows a list of all products with the <code>NAME</code> <code>phone</code> in the application.
        </td>
    </tr>
    <tr>
        <td>
            <a href="#listing-list">List</a>
        </td>
        <td>
            List all client: <code>list -c</code><br>
            e.g., <code>list -c</code> Lists all the clients in the application.<br>
            <br>
            List all product: <code>list -p</code><br>
            e.g., <code>list -p</code> Lists all the products in the application.
        </td>
    </tr>
    <tr>
        <td>
            <a href="#command-history">Command History</a>
        </td>
        <td>
            Allows the user to navigate to previous commands using <code>↑</code> and <code>↓</code> keys.<br>
            Previous command: <code>↑</code><br>
            Next command: <code>↓</code>
        </td>
    </tr>
    <tr>
        <td>
            <a href="#exiting-exit">Exit</a>
        </td>
        <td>
            Exit the program: <code>exit</code>
        </td>
    </tr>
</table>
